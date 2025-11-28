package net.multiverse.dynamicheight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Lifecycle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import java.lang.reflect.Proxy;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.multiverse.dynamicheight.mixin.ScreenInvoker;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;

/**
 * Adds a configuration panel to the Create World screen for controlling the custom build limits.
 */
public final class WorldHeightScreenHooks {
    private static final Component HEADER = Component.literal("Custom Height Settings");
    private static final Component FLOOR_LABEL = Component.literal("Floor (min Y)");
    private static final Component CEILING_LABEL = Component.literal("Ceiling (max Y)");
    private static LabelWidget minLabel;
    private static LabelWidget maxLabel;
    private static EditBox minBox;
    private static EditBox maxBox;
    private static Button resetButton;
    private static boolean panelVisible;

    private static final Class<?> TAB_MANAGER_CLASS = resolveTabManagerClass();
    private static Field TAB_MANAGER_FIELD;
    private static Method TAB_MANAGER_METHOD;
    private static Method GET_UI_STATE_METHOD;
    private static Field UI_STATE_FIELD;
    private static Method UPDATE_DIMENSIONS_METHOD;
    private static Class<?> DIMENSIONS_UPDATER_CLASS;

    private WorldHeightScreenHooks() {
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof CreateWorldScreen createWorldScreen) {
                onInit(createWorldScreen);
            } else {
                clearWidgets();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.screen instanceof CreateWorldScreen screen) {
                refreshBoxes(screen);
            }
        });
    }

    private static void clearWidgets() {
        minLabel = null;
        maxLabel = null;
        minBox = null;
        maxBox = null;
        resetButton = null;
        panelVisible = false;
    }

    private static void onInit(CreateWorldScreen screen) {
        clearWidgets();
        panelVisible = true;

        int panelWidth = 220;
        int panelX = 20;
        int fieldWidth = panelWidth * 2 / 5;
        int startY = 20;

        resetButton = Button.builder(HEADER, button -> {
                    WorldHeightSettings.resetToDefaults();
                    refreshBoxes(screen);
                    applySelection(screen);
                })
                .bounds(panelX, startY, panelWidth, 20)
                .tooltip(Tooltip.create(Component.literal("Reset to the default overworld height")))
                .build();
        addWidget(screen, resetButton);

        int rowY = startY + 28;
        minLabel = new LabelWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL);
        addWidget(screen, minLabel);
        rowY += 12;

        minBox = new EditBox(Minecraft.getInstance().font, panelX, rowY, fieldWidth, 20, FLOOR_LABEL);
        minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
        minBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        minBox.setTooltip(Tooltip.create(Component.literal("Clamped to multiples of 16 down to vanilla minimum (-64).")));
        minBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMinY(parsed);
                applySelection(screen);
            }
        });
        addWidget(screen, minBox);

        rowY += 28;
        maxLabel = new LabelWidget(panelX, rowY, fieldWidth, 10, CEILING_LABEL);
        addWidget(screen, maxLabel);
        rowY += 12;

        maxBox = new EditBox(Minecraft.getInstance().font, panelX, rowY, fieldWidth, 20, CEILING_LABEL);
        maxBox.setValue(Integer.toString(WorldHeightSettings.getMaxY()));
        maxBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        maxBox.setTooltip(Tooltip.create(Component.literal("Snaps to 16-block steps and never below vanilla ceiling.")));
        maxBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMaxY(parsed);
                applySelection(screen);
            }
        });
        addWidget(screen, maxBox);

        applySelection(screen);
        setWidgetVisibility(true);
        updateFieldValues();
    }

    private static void refreshBoxes(CreateWorldScreen screen) {
        boolean visible = isWorldTabActive(screen);

        if (visible != panelVisible) {
            panelVisible = visible;
            setWidgetVisibility(visible);
        }
        if (visible) {
            updateFieldValues();
        }
    }

    private static void applySelection(CreateWorldScreen screen) {
        Object state = resolveUiState(screen);
        if (state != null) {
            invokeUpdateDimensions(state, WorldHeightSettings.getMinY(), WorldHeightSettings.getMaxY());
        }
    }

    private static WorldDimensions rebuildDimensions(RegistryAccess.Frozen registryAccess, WorldDimensions existing, int minY, int maxY) {
        MappedRegistry<LevelStem> rebuilt = new MappedRegistry<>(Registries.LEVEL_STEM, Lifecycle.stable());
        existing.dimensions().entrySet().forEach(entry -> {
            rebuilt.register(entry.getKey(), rebuildStem(registryAccess, entry.getValue(), minY, maxY), Lifecycle.stable());
        });
        return new WorldDimensions(rebuilt.freeze());
    }

    private static LevelStem rebuildStem(RegistryAccess registryAccess, LevelStem stem, int minY, int maxY) {
        DimensionType updated = DimensionTypeUtil.copyWithHeight(stem.type().value(), minY, maxY);
        Holder<DimensionType> updatedHolder = DimensionTypeUtil.bindUpdatedDimensionType(registryAccess, stem.type(), updated);
        return new LevelStem(updatedHolder, stem.generator());
    }

    private static boolean isWorldTabActive(CreateWorldScreen screen) {
        Object manager = resolveTabManager(screen);
        if (manager == null) {
            return true;
        }
        try {
            Method getCurrentTab = manager.getClass().getMethod("getCurrentTab");
            Object current = getCurrentTab.invoke(manager);
            if (current == null) {
                return true;
            }
            String simpleName = current.getClass().getSimpleName();
            if (simpleName.contains("WorldTab")) {
                return true;
            }
            Package pkg = current.getClass().getPackage();
            return pkg != null && pkg.getName().contains("worldselection");
        } catch (ReflectiveOperationException ignored) {
            return true;
        }
    }

    private static void setWidgetVisibility(boolean visible) {
        if (resetButton != null) {
            resetButton.visible = visible;
            resetButton.active = visible;
        }
        if (minLabel != null) {
            minLabel.visible = visible;
        }
        if (maxLabel != null) {
            maxLabel.visible = visible;
        }
        if (minBox != null) {
            minBox.visible = visible;
            minBox.active = visible;
        }
        if (maxBox != null) {
            maxBox.visible = visible;
            maxBox.active = visible;
        }
    }

    private static void updateFieldValues() {
        if (minBox != null && !minBox.isFocused()) {
            String desired = Integer.toString(WorldHeightSettings.getMinY());
            if (!desired.equals(minBox.getValue())) {
                minBox.setValue(desired);
            }
        }
        if (maxBox != null && !maxBox.isFocused()) {
            String desired = Integer.toString(WorldHeightSettings.getMaxY());
            if (!desired.equals(maxBox.getValue())) {
                maxBox.setValue(desired);
            }
        }
    }

    private static Object resolveTabManager(CreateWorldScreen screen) {
        if (TAB_MANAGER_CLASS == null) {
            return null;
        }
        try {
            if (TAB_MANAGER_FIELD == null) {
                TAB_MANAGER_FIELD = findFieldWithType(screen.getClass(), TAB_MANAGER_CLASS);
                if (TAB_MANAGER_FIELD != null) {
                    TAB_MANAGER_FIELD.setAccessible(true);
                }
            }
            if (TAB_MANAGER_FIELD != null) {
                return TAB_MANAGER_FIELD.get(screen);
            }
            if (TAB_MANAGER_METHOD == null) {
                TAB_MANAGER_METHOD = findZeroArgMethodReturning(screen.getClass(), TAB_MANAGER_CLASS);
                if (TAB_MANAGER_METHOD != null) {
                    TAB_MANAGER_METHOD.setAccessible(true);
                }
            }
            if (TAB_MANAGER_METHOD != null) {
                return TAB_MANAGER_METHOD.invoke(screen);
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    private static Field findFieldWithType(Class<?> owner, Class<?> type) {
        if (type == null) {
            return null;
        }
        Class<?> current = owner;
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.getType() == type) {
                    return field;
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private static Method findZeroArgMethodReturning(Class<?> owner, Class<?> type) {
        if (type == null) {
            return null;
        }
        Class<?> current = owner;
        while (current != null && current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getParameterCount() == 0 && method.getReturnType() == type) {
                    return method;
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private static Field findFieldByName(Class<?> owner, String name) {
        Class<?> current = owner;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private static Method findMethod(Class<?> owner, String name, int parameterCount) {
        Class<?> current = owner;
        while (current != null && current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getName().equals(name) && method.getParameterCount() == parameterCount) {
                    return method;
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private static boolean allowIntegerInput(String value) {
        if (value == null || value.isEmpty() || "-".equals(value)) {
            return true;
        }
        return parseInt(value) != null;
    }

    private static Integer parseInt(String value) {
        if (value == null || value.isEmpty() || "-".equals(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void addWidget(CreateWorldScreen screen, AbstractWidget widget) {
        ((ScreenInvoker) screen).dynamicheight$addRenderableWidget(widget);
    }

    private static Class<?> resolveTabManagerClass() {
        try {
            return Class.forName("net.minecraft.client.gui.components.tabs.TabManager");
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    private static Object resolveUiState(CreateWorldScreen screen) {
        try {
            if (GET_UI_STATE_METHOD == null && UI_STATE_FIELD == null) {
                try {
                    GET_UI_STATE_METHOD = screen.getClass().getMethod("getUiState");
                    GET_UI_STATE_METHOD.setAccessible(true);
                } catch (NoSuchMethodException ignored) {
                }
                if (GET_UI_STATE_METHOD == null) {
                    UI_STATE_FIELD = findFieldByName(screen.getClass(), "uiState");
                    if (UI_STATE_FIELD != null) {
                        UI_STATE_FIELD.setAccessible(true);
                    }
                }
            }
            if (GET_UI_STATE_METHOD != null) {
                return GET_UI_STATE_METHOD.invoke(screen);
            }
            if (UI_STATE_FIELD != null) {
                return UI_STATE_FIELD.get(screen);
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    private static void invokeUpdateDimensions(Object state, int minY, int maxY) {
        try {
            if (UPDATE_DIMENSIONS_METHOD == null) {
                UPDATE_DIMENSIONS_METHOD = findMethod(state.getClass(), "updateDimensions", 1);
                if (UPDATE_DIMENSIONS_METHOD != null) {
                    UPDATE_DIMENSIONS_METHOD.setAccessible(true);
                    DIMENSIONS_UPDATER_CLASS = UPDATE_DIMENSIONS_METHOD.getParameterTypes()[0];
                }
            }
            if (UPDATE_DIMENSIONS_METHOD == null || DIMENSIONS_UPDATER_CLASS == null || !DIMENSIONS_UPDATER_CLASS.isInterface()) {
                return;
            }
            Object updater = Proxy.newProxyInstance(
                    DIMENSIONS_UPDATER_CLASS.getClassLoader(),
                    new Class<?>[]{DIMENSIONS_UPDATER_CLASS},
                    (proxy, method, args) -> {
                        if (args != null && args.length == 2 && args[0] instanceof RegistryAccess.Frozen && args[1] instanceof WorldDimensions) {
                            return rebuildDimensions((RegistryAccess.Frozen) args[0], (WorldDimensions) args[1], minY, maxY);
                        }
                        return null;
                    }
            );
            UPDATE_DIMENSIONS_METHOD.invoke(state, updater);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    private static final class LabelWidget extends AbstractWidget {
        private final int color;

        private LabelWidget(int x, int y, int width, int height, Component message) {
            this(x, y, width, height, message, 0xFFFFFF);
        }

        private LabelWidget(int x, int y, int width, int height, Component message, int color) {
            super(x, y, width, height, message);
            this.color = color;
            this.active = false;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float delta) {
            Font font = Minecraft.getInstance().font;
            font.draw(poseStack, this.getMessage(), this.getX(), this.getY(), this.color);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            narrationElementOutput.add(NarratedElementType.TITLE, this.getMessage());
        }
    }
}
