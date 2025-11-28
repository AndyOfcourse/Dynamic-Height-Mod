package net.multiverse.dynamicheight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.multiverse.dynamicheight.mixin.ScreenInvoker;

/**
 * Lightweight UI helper for 1.19.2's Create World screen. Adds two text fields and a reset
 * button so players can set their desired build limits before the world starts.
 */
public final class WorldHeightScreenHooks {
    private static final Component HEADER = new TextComponent("Custom Height Settings");
    private static final Component FLOOR_LABEL = new TextComponent("Floor (min Y)");
    private static final Component CEILING_LABEL = new TextComponent("Ceiling (max Y)");

    private static LabelWidget minLabel;
    private static LabelWidget maxLabel;
    private static EditBox minBox;
    private static EditBox maxBox;
    private static Button resetButton;

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
            if (client.screen instanceof CreateWorldScreen) {
                updateFieldValues();
            }
        });
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

        resetButton = new Button(panelX, startY, panelWidth, 20, HEADER, button -> {
            WorldHeightSettings.resetToDefaults();
            updateFieldValues();
        });
        addWidget(screen, resetButton);

        int rowY = startY + 28;
        minLabel = new LabelWidget(panelX, rowY, fieldWidth, 10, FLOOR_LABEL);
        addWidget(screen, minLabel);
        rowY += 12;

        minBox = new EditBox(Minecraft.getInstance().font, panelX, rowY, fieldWidth, 20, FLOOR_LABEL);
        minBox.setValue(Integer.toString(WorldHeightSettings.getMinY()));
        minBox.setFilter(WorldHeightScreenHooks::allowIntegerInput);
        minBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMinY(parsed);
                updateFieldValues();
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
        maxBox.setResponder(value -> {
            Integer parsed = parseInt(value);
            if (parsed != null) {
                WorldHeightSettings.setMaxY(parsed);
                updateFieldValues();
            }
        });
        addWidget(screen, maxBox);
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
            font.draw(poseStack, this.getMessage(), this.x, this.y, this.color);
        }

        @Override
        public void updateNarration(NarrationElementOutput narrationElementOutput) {
            narrationElementOutput.add(NarratedElementType.TITLE, this.getMessage());
        }
    }
}
