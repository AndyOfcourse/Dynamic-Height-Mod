/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.StringWidget
 *  net.minecraft.client.gui.components.Tooltip
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.components.tabs.Tab
 *  net.minecraft.client.gui.components.tabs.TabManager
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.worldselection.CreateWorldScreen
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.MappedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
 *  net.minecraft.world.level.levelgen.NoiseGeneratorSettings
 *  net.minecraft.world.level.levelgen.WorldDimensions
 *  net.minecraftforge.client.event.ScreenEvent$Init$Post
 *  net.minecraftforge.client.event.ScreenEvent$Render$Post
 *  net.minecraftforge.common.MinecraftForge
 */
package net.multiverse.dynamicheight.client;

import com.mojang.serialization.Lifecycle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.multiverse.dynamicheight.client.WorldHeightSettings;
import net.multiverse.dynamicheight.util.NoiseGeneratorUtil;

public final class WorldHeightScreenHooks {
    private static final Component HEADER = Component.m_237113_((String)"Custom Height Settings");
    private static final Component FLOOR_LABEL = Component.m_237113_((String)"Floor (min Y)");
    private static final Component CEILING_LABEL = Component.m_237113_((String)"Ceiling (max Y)");
    private static final Map<ResourceKey<LevelStem>, NoiseGeneratorSettings> ORIGINAL_NOISE = new HashMap<ResourceKey<LevelStem>, NoiseGeneratorSettings>();
    private static StringWidget minLabel;
    private static StringWidget maxLabel;
    private static EditBox minBox;
    private static EditBox maxBox;
    private static Button resetButton;
    private static Field TAB_MANAGER_FIELD;
    private static Method TAB_MANAGER_METHOD;

    private WorldHeightScreenHooks() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onInit);
        MinecraftForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onRender);
    }

    private static void onInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof CreateWorldScreen)) {
            return;
        }
        CreateWorldScreen screen2 = (CreateWorldScreen)screen;
        ORIGINAL_NOISE.clear();
        minLabel = null;
        maxLabel = null;
        minBox = null;
        maxBox = null;
        resetButton = null;
        int panelWidth = 220;
        int panelX = 20;
        int fieldWidth = panelWidth * 2 / 5;
        int startY = 20;
        resetButton = Button.m_253074_((Component)HEADER, button -> {
            WorldHeightSettings.resetToDefaults();
            WorldHeightScreenHooks.refreshBoxes(screen2);
            WorldHeightScreenHooks.applySelection(screen2);
        }).m_252987_(panelX, startY, panelWidth, 20).m_257505_(Tooltip.m_257550_((Component)Component.m_237113_((String)"Reset to the default overworld height"))).m_253136_();
        event.addListener((GuiEventListener)resetButton);
        int rowY = startY + 28;
        minLabel = new StringWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL, Minecraft.m_91087_().f_91062_);
        event.addListener((GuiEventListener)minLabel);
        minBox = new EditBox(Minecraft.m_91087_().f_91062_, panelX, rowY += 12, fieldWidth, 20, FLOOR_LABEL);
        minBox.m_94144_(Integer.toString(WorldHeightSettings.getMinY()));
        minBox.m_94153_(WorldHeightScreenHooks::allowIntegerInput);
        minBox.m_257544_(Tooltip.m_257550_((Component)Component.m_237113_((String)"Clamped to multiples of 16 down to vanilla minimum (-64).")));
        minBox.m_94151_(value -> {
            Integer parsed = WorldHeightScreenHooks.parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMinY(parsed);
                WorldHeightScreenHooks.applySelection(screen2);
            }
        });
        event.addListener((GuiEventListener)minBox);
        maxLabel = new StringWidget(panelX, rowY += 28, fieldWidth, 10, CEILING_LABEL, Minecraft.m_91087_().f_91062_);
        event.addListener((GuiEventListener)maxLabel);
        maxBox = new EditBox(Minecraft.m_91087_().f_91062_, panelX, rowY += 12, fieldWidth, 20, CEILING_LABEL);
        maxBox.m_94144_(Integer.toString(WorldHeightSettings.getMaxY()));
        maxBox.m_94153_(WorldHeightScreenHooks::allowIntegerInput);
        maxBox.m_257544_(Tooltip.m_257550_((Component)Component.m_237113_((String)"Snaps to 16-block steps and never below vanilla ceiling.")));
        maxBox.m_94151_(value -> {
            Integer parsed = WorldHeightScreenHooks.parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMaxY(parsed);
                WorldHeightScreenHooks.applySelection(screen2);
            }
        });
        event.addListener((GuiEventListener)maxBox);
        WorldHeightScreenHooks.applySelection(screen2);
        WorldHeightScreenHooks.refreshBoxes(screen2);
    }

    private static void onRender(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof CreateWorldScreen) {
            CreateWorldScreen screen2 = (CreateWorldScreen)screen;
            WorldHeightScreenHooks.refreshBoxes(screen2);
        }
    }

    private static void refreshBoxes(CreateWorldScreen screen) {
        boolean visible = WorldHeightScreenHooks.isWorldTabActive(screen);
        if (resetButton != null) {
            WorldHeightScreenHooks.resetButton.f_93624_ = visible;
            WorldHeightScreenHooks.resetButton.f_93623_ = visible;
        }
        if (minLabel != null) {
            WorldHeightScreenHooks.minLabel.f_93624_ = visible;
        }
        if (maxLabel != null) {
            WorldHeightScreenHooks.maxLabel.f_93624_ = visible;
        }
        if (minBox != null) {
            if (!minBox.m_93696_()) {
                minBox.m_94144_(Integer.toString(WorldHeightSettings.getMinY()));
            }
            WorldHeightScreenHooks.minBox.f_93624_ = visible;
            WorldHeightScreenHooks.minBox.f_93623_ = visible;
        }
        if (maxBox != null) {
            if (!maxBox.m_93696_()) {
                maxBox.m_94144_(Integer.toString(WorldHeightSettings.getMaxY()));
            }
            WorldHeightScreenHooks.maxBox.f_93624_ = visible;
            WorldHeightScreenHooks.maxBox.f_93623_ = visible;
        }
    }

    private static boolean isWorldTabActive(CreateWorldScreen screen) {
        TabManager manager = WorldHeightScreenHooks.resolveTabManager(screen);
        if (manager == null) {
            return true;
        }
        Tab current = manager.m_267695_();
        if (current == null) {
            return true;
        }
        String simpleName = current.getClass().getSimpleName();
        if (simpleName.contains("WorldTab")) {
            return true;
        }
        Package pkg = current.getClass().getPackage();
        return pkg != null && pkg.getName().contains("worldselection");
    }

    private static TabManager resolveTabManager(CreateWorldScreen screen) {
        try {
            if (TAB_MANAGER_FIELD == null && (TAB_MANAGER_FIELD = WorldHeightScreenHooks.findFieldWithType(screen.getClass(), TabManager.class)) != null) {
                TAB_MANAGER_FIELD.setAccessible(true);
            }
            if (TAB_MANAGER_FIELD != null) {
                return (TabManager)TAB_MANAGER_FIELD.get(screen);
            }
            if (TAB_MANAGER_METHOD == null && (TAB_MANAGER_METHOD = WorldHeightScreenHooks.findZeroArgMethodReturning(screen.getClass(), TabManager.class)) != null) {
                TAB_MANAGER_METHOD.setAccessible(true);
            }
            if (TAB_MANAGER_METHOD != null) {
                return (TabManager)TAB_MANAGER_METHOD.invoke((Object)screen, new Object[0]);
            }
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
        return null;
    }

    private static Field findFieldWithType(Class<?> owner, Class<?> type) {
        for (Class<?> current = owner; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (field.getType() != type) continue;
                return field;
            }
        }
        return null;
    }

    private static Method findZeroArgMethodReturning(Class<?> owner, Class<?> type) {
        for (Class<?> current = owner; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getParameterCount() != 0 || method.getReturnType() != type) continue;
                return method;
            }
        }
        return null;
    }

    private static boolean allowIntegerInput(String value) {
        if (value == null || value.isEmpty() || "-".equals(value)) {
            return true;
        }
        return WorldHeightScreenHooks.parseInt(value) != null;
    }

    private static Integer parseInt(String value) {
        if (value == null || value.isEmpty() || "-".equals(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    private static void applySelection(CreateWorldScreen screen) {
        screen.m_267748_().m_267717_((registryAccess, dimensions) -> WorldHeightScreenHooks.rebuildDimensions(registryAccess, dimensions, WorldHeightSettings.getMinY(), WorldHeightSettings.getMaxY()));
    }

    private static WorldDimensions rebuildDimensions(RegistryAccess.Frozen registryAccess, WorldDimensions existing, int minY, int maxY) {
        Registry stems = existing.f_243948_();
        MappedRegistry rebuilt = new MappedRegistry(Registries.f_256862_, Lifecycle.stable());
        stems.m_6579_().forEach(entry -> {
            ResourceKey key = (ResourceKey)entry.getKey();
            LevelStem stem = (LevelStem)entry.getValue();
            rebuilt.m_255290_(key, (Object)WorldHeightScreenHooks.rebuildStem((ResourceKey<LevelStem>)key, stem, minY, maxY), stems.m_6228_((Object)stem));
        });
        return new WorldDimensions(rebuilt.m_203521_());
    }

    private static LevelStem rebuildStem(ResourceKey<LevelStem> key, LevelStem stem, int minY, int maxY) {
        Holder typeHolder = stem.f_63975_();
        DimensionType base = (DimensionType)typeHolder.m_203334_();
        int height = maxY - minY;
        DimensionType updated = new DimensionType(base.f_63854_(), base.f_223549_(), base.f_63856_(), base.f_63857_(), base.f_63858_(), base.f_63859_(), base.f_63862_(), base.f_63863_(), minY, height, Math.max(16, height), base.f_63836_(), base.f_63837_(), base.f_63838_(), base.f_223550_());
        if (typeHolder instanceof Holder.Reference) {
            Holder.Reference reference = (Holder.Reference)typeHolder;
            reference.m_247654_((Object)updated);
            typeHolder = reference;
        } else {
            typeHolder = Holder.m_205709_((Object)updated);
        }
        ChunkGenerator generator = stem.f_63976_();
        if (generator instanceof NoiseBasedChunkGenerator) {
            NoiseBasedChunkGenerator noiseGenerator = (NoiseBasedChunkGenerator)generator;
            generator = WorldHeightScreenHooks.rebuildNoiseGenerator(key, noiseGenerator, minY, height);
        }
        return new LevelStem(typeHolder, generator);
    }

    private static NoiseBasedChunkGenerator rebuildNoiseGenerator(ResourceKey<LevelStem> key, NoiseBasedChunkGenerator template, int minY, int height) {
        NoiseGeneratorSettings vanilla = ORIGINAL_NOISE.computeIfAbsent(key, k -> (NoiseGeneratorSettings)template.m_224341_().m_203334_());
        NoiseGeneratorSettings updatedSettings = NoiseGeneratorUtil.stretch(vanilla, minY, height);
        return NoiseGeneratorUtil.recreate(template, updatedSettings);
    }
}

