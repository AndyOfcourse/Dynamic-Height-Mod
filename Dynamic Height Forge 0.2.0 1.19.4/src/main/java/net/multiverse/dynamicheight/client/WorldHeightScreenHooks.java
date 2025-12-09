package net.multiverse.dynamicheight.client;

import com.mojang.serialization.Lifecycle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Adds the UI controls for selecting custom world heights on the create-world screen.
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
    private static Field tabManagerField;
    private static Method tabManagerMethod;

    private WorldHeightScreenHooks() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onInit);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onRender);
    }

    private static void onInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof CreateWorldScreen createWorldScreen)) {
            return;
        }

        minLabel = null;
        maxLabel = null;
        minBox = null;
        maxBox = null;
        resetButton = null;

        int panelWidth = 220;
        int panelX = 20;
        int fieldWidth = panelWidth * 2 / 5;
        int startY = 20;
        Font font = Minecraft.getInstance().font;

        resetButton = Button.builder(HEADER, button -> {
            WorldHeightSettings.resetToDefaults();
            refreshBoxes(createWorldScreen);
            applySelection(createWorldScreen);
        }).bounds(panelX, startY, panelWidth, 20)
                .tooltip(Tooltip.create(Component.literal("Reset to the default overworld height")))
                .build();
        event.addListener(resetButton);

        int rowY = startY + 28;
        minLabel = new StringWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL, font);
        event.addListener(minLabel);

        minBox = new EditBox(font, panelX, rowY += 12, fieldWidth, 20, FLOOR_LABEL);
        minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
        minBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        minBox.setTooltip(Tooltip.create(Component.literal("Clamped to multiples of 16 down to vanilla minimum (-64).")));
        minBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMinY(parsed);
                applySelection(createWorldScreen);
            }
        });
        event.addListener(minBox);

        maxLabel = new StringWidget(panelX, rowY += 28, fieldWidth, 10, CEILING_LABEL, font);
        event.addListener(maxLabel);

        maxBox = new EditBox(font, panelX, rowY += 12, fieldWidth, 20, CEILING_LABEL);
        maxBox.setValue(Integer.toString(WorldHeightSettings.getMaxY()));
        maxBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        maxBox.setTooltip(Tooltip.create(Component.literal("Snaps to 16-block steps and never below vanilla ceiling.")));
        maxBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMaxY(parsed);
                applySelection(createWorldScreen);
            }
        });
        event.addListener(maxBox);

        applySelection(createWorldScreen);
        refreshBoxes(createWorldScreen);
    }

    private static void onRender(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof CreateWorldScreen createWorldScreen) {
            refreshBoxes(createWorldScreen);
        }
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

    private static boolean isWorldTabActive(CreateWorldScreen screen) {
        TabManager manager = resolveTabManager(screen);
        if (manager == null) {
            return true;
        }
        Tab currentTab = manager.getCurrentTab();
        if (currentTab == null) {
            return true;
        }
        String simpleName = currentTab.getClass().getSimpleName();
        if (simpleName.contains("WorldTab")) {
            return true;
        }
        Package pkg = currentTab.getClass().getPackage();
        return pkg != null && pkg.getName().contains("worldselection");
    }

    private static TabManager resolveTabManager(CreateWorldScreen screen) {
        try {
            if (tabManagerField == null) {
                tabManagerField = findFieldWithType(screen.getClass(), TabManager.class);
                if (tabManagerField != null) {
                    tabManagerField.setAccessible(true);
                }
            }
            if (tabManagerField != null) {
                return (TabManager)tabManagerField.get(screen);
            }
            if (tabManagerMethod == null) {
                tabManagerMethod = findZeroArgMethodReturning(screen.getClass(), TabManager.class);
                if (tabManagerMethod != null) {
                    tabManagerMethod.setAccessible(true);
                }
            }
            if (tabManagerMethod != null) {
                return (TabManager)tabManagerMethod.invoke(screen);
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    private static Field findFieldWithType(Class<?> owner, Class<?> type) {
        for (Class<?> current = owner; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (field.getType() == type) {
                    return field;
                }
            }
        }
        return null;
    }

    private static Method findZeroArgMethodReturning(Class<?> owner, Class<?> type) {
        for (Class<?> current = owner; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getParameterCount() == 0 && method.getReturnType() == type) {
                    return method;
                }
            }
        }
        return null;
    }

    private static boolean allowIntegerInput(String value) {
        return value == null || value.isEmpty() || "-".equals(value) || parseInt(value) != null;
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

    private static void applySelection(CreateWorldScreen screen) {
        screen.getUiState().updateDimensions((registryAccess, dimensions) -> rebuildDimensions(registryAccess, dimensions, WorldHeightSettings.getMinY(), WorldHeightSettings.getMaxY()));
    }

    private static WorldDimensions rebuildDimensions(RegistryAccess.Frozen registryAccess, WorldDimensions existing, int minY, int maxY) {
        Registry<LevelStem> stems = existing.dimensions();
        MappedRegistry<LevelStem> rebuilt = new MappedRegistry<>(Registries.LEVEL_STEM, Lifecycle.stable());
        stems.entrySet().forEach(entry -> {
            ResourceKey<LevelStem> key = entry.getKey();
            LevelStem stem = entry.getValue();
            rebuilt.register(key, rebuildStem(key, stem, minY, maxY), stems.lifecycle(stem));
        });
        return new WorldDimensions(rebuilt.freeze());
    }

    private static LevelStem rebuildStem(ResourceKey<LevelStem> key, LevelStem stem, int minY, int maxY) {
        Holder<DimensionType> typeHolder = stem.type();
        DimensionType base = typeHolder.value();
        int height = maxY - minY;
        DimensionType updated = new DimensionType(base.fixedTime(), base.hasSkyLight(), base.hasCeiling(), base.ultraWarm(), base.natural(), base.coordinateScale(), base.bedWorks(), base.respawnAnchorWorks(), minY, height, Math.max(16, height), base.infiniburn(), base.effectsLocation(), base.ambientLight(), base.monsterSettings());
        if (typeHolder instanceof Holder.Reference<DimensionType> reference) {
            reference.bindValue(updated);
            typeHolder = reference;
        } else {
            typeHolder = Holder.direct(updated);
        }
        return new LevelStem(typeHolder, stem.generator());
    }
}
