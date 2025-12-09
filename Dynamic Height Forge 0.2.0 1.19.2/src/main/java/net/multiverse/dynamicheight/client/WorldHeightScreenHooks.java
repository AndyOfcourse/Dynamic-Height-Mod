package net.multiverse.dynamicheight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Adds the UI controls for selecting custom world heights on the create-world screen.
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

        resetButton = new Button(panelX, startY, panelWidth, 20, HEADER, button -> {
            WorldHeightSettings.resetToDefaults();
            refreshBoxes(createWorldScreen);
            applySelection(createWorldScreen);
        });
        event.addListener(resetButton);

        int rowY = startY + 28;
        minLabel = new LabelWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL, font);
        event.addListener(minLabel);

        minBox = new EditBox(font, panelX, rowY += 12, fieldWidth, 20, FLOOR_LABEL);
        minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
        minBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        minBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMinY(parsed);
                applySelection(createWorldScreen);
            }
        });
        event.addListener(minBox);

        maxLabel = new LabelWidget(panelX, rowY += 28, fieldWidth, 10, CEILING_LABEL, font);
        event.addListener(maxLabel);

        maxBox = new EditBox(font, panelX, rowY += 12, fieldWidth, 20, CEILING_LABEL);
        maxBox.setValue(Integer.toString(WorldHeightSettings.getMaxY()));
        maxBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
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
        if (resetButton != null) {
            resetButton.visible = true;
            resetButton.active = true;
        }
        if (minBox != null) {
            if (!minBox.isFocused()) {
                minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
            }
            minBox.visible = true;
            minBox.active = true;
        }
        if (maxBox != null) {
            if (!maxBox.isFocused()) {
                maxBox.setValue(Integer.toString(WorldHeightSettings.getMaxY()));
            }
            maxBox.visible = true;
            maxBox.active = true;
        }
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
        // No direct dimension manipulation is required for 1.19.3.
    }

    private static final class LabelWidget extends AbstractWidget {
        private final Font font;

        private LabelWidget(int x, int y, int width, int height, Component message, Font font) {
            super(x, y, width, height, message);
            this.font = font;
            this.active = false;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            this.font.draw(poseStack, this.getMessage(), (float)this.x, (float)this.y, 0xFFFFFF);
        }

        @Override
        public void updateNarration(NarrationElementOutput output) {
        }
    }
}
