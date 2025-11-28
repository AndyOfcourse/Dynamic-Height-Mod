package net.multiverse.dynamicheight.client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.multiverse.dynamicheight.mixin.ScreenInvoker;
import net.multiverse.dynamicheight.util.DimensionTypeUtil;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Adds a configuration panel to the Create World screen for controlling the custom build limits.
 */
public final class WorldHeightScreenHooks {
    private static final Component HEADER = Component.literal("Custom Height Settings");
    private static final Component FLOOR_LABEL = Component.literal("Floor (min Y)");
    private static final Component CEILING_LABEL = Component.literal("Ceiling (max Y)");
    private static StringWidget minLabel;
    private static StringWidget maxLabel;
    private static EditBox minBox;
    private static EditBox maxBox;
    private static Button resetButton;

    private static Field TAB_MANAGER_FIELD;
    private static Method TAB_MANAGER_METHOD;

    private WorldHeightScreenHooks() {
    }

    public static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof CreateWorldScreen createWorldScreen) {
            onInit(createWorldScreen);
        } else {
            clearWidgets();
        }
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen instanceof CreateWorldScreen screen) {
            refreshBoxes(screen);
        }
    }

    private static void clearWidgets() {
        minLabel = null;
        maxLabel = null;
        minBox = null;
        maxBox = null;
        resetButton = null;
    }

    private static void onInit(CreateWorldScreen screen) {
        clearWidgets();

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
        minLabel = new StringWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL, Minecraft.getInstance().font);
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
        maxLabel = new StringWidget(panelX, rowY, fieldWidth, 10, CEILING_LABEL, Minecraft.getInstance().font);
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
        refreshBoxes(screen);
    }

    private static void refreshBoxes(CreateWorldScreen screen) {
        boolean visible = isWorldTabActive(screen);

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
            if (!minBox.isFocused()) {
                minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
            }
            minBox.visible = visible;
            minBox.active = visible;
        }
        if (maxBox != null) {
            if (!maxBox.isFocused()) {
                maxBox.setValue(Integer.toString(WorldHeightSettings.getMaxY()));
            }
            maxBox.visible = visible;
            maxBox.active = visible;
        }
    }

    private static void applySelection(CreateWorldScreen screen) {
        var state = screen.getUiState();
        state.updateDimensions((registryAccess, dimensions) ->
                rebuildDimensions(registryAccess, dimensions, WorldHeightSettings.getMinY(), WorldHeightSettings.getMaxY()));
    }

    private static WorldDimensions rebuildDimensions(RegistryAccess.Frozen registryAccess, WorldDimensions existing, int minY, int maxY) {
        MappedRegistry<LevelStem> rebuilt = new MappedRegistry<>(Registries.LEVEL_STEM, Lifecycle.stable());
        existing.dimensions().forEach((key, stem) -> rebuilt.register(key, rebuildStem(registryAccess, stem, minY, maxY), RegistrationInfo.BUILT_IN));
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

    private static Object resolveTabManager(CreateWorldScreen screen) {
        try {
            if (TAB_MANAGER_FIELD == null) {
                TAB_MANAGER_FIELD = findFieldWithType(screen.getClass(), TabManager.class);
                if (TAB_MANAGER_FIELD != null) {
                    TAB_MANAGER_FIELD.setAccessible(true);
                }
            }
            if (TAB_MANAGER_FIELD != null) {
                return TAB_MANAGER_FIELD.get(screen);
            }
            if (TAB_MANAGER_METHOD == null) {
                TAB_MANAGER_METHOD = findZeroArgMethodReturning(screen.getClass(), TabManager.class);
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
}
