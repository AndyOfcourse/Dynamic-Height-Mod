/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen$RuleEntry
 *  net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen$RuleList
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TranslatableComponent
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.level.GameRules
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class EditGameRulesScreen
extends Screen {
    private final Consumer<Optional<GameRules>> exitCallback;
    private RuleList rules;
    private final Set<RuleEntry> invalidEntries = Sets.newHashSet();
    private Button doneButton;
    @Nullable
    private List<FormattedCharSequence> tooltip;
    private final GameRules gameRules;

    public EditGameRulesScreen(GameRules gameRules, Consumer<Optional<GameRules>> consumer) {
        super((Component)new TranslatableComponent("editGamerule.title"));
        this.gameRules = gameRules;
        this.exitCallback = consumer;
    }

    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        super.init();
        this.rules = new RuleList(this, this.gameRules);
        this.children.add(this.rules);
        this.addButton((AbstractWidget)new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20, CommonComponents.GUI_CANCEL, button -> this.exitCallback.accept(Optional.empty())));
        this.doneButton = (Button)this.addButton((AbstractWidget)new Button(this.width / 2 - 155, this.height - 29, 150, 20, CommonComponents.GUI_DONE, button -> this.exitCallback.accept(Optional.of(this.gameRules))));
    }

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public void onClose() {
        this.exitCallback.accept(Optional.empty());
    }

    public void render(PoseStack poseStack, int i, int j, float f) {
        this.tooltip = null;
        this.rules.render(poseStack, i, j, f);
        EditGameRulesScreen.drawCenteredString((PoseStack)poseStack, (Font)this.font, (Component)this.title, (int)(this.width / 2), (int)20, (int)0xFFFFFF);
        super.render(poseStack, i, j, f);
        if (this.tooltip != null) {
            this.renderTooltip(poseStack, this.tooltip, i, j);
        }
    }

    private void setTooltip(@Nullable List<FormattedCharSequence> list) {
        this.tooltip = list;
    }

    private void updateDoneButton() {
        this.doneButton.active = this.invalidEntries.isEmpty();
    }

    private void markInvalid(RuleEntry ruleEntry) {
        this.invalidEntries.add(ruleEntry);
        this.updateDoneButton();
    }

    private void clearInvalid(RuleEntry ruleEntry) {
        this.invalidEntries.remove(ruleEntry);
        this.updateDoneButton();
    }

    static /* synthetic */ Minecraft method_27621(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Minecraft method_29984(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Minecraft method_27629(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Minecraft method_29985(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Minecraft method_29986(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Minecraft method_27627(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ void method_27622(EditGameRulesScreen editGameRulesScreen, RuleEntry ruleEntry) {
        editGameRulesScreen.clearInvalid(ruleEntry);
    }

    static /* synthetic */ void method_27628(EditGameRulesScreen editGameRulesScreen, RuleEntry ruleEntry) {
        editGameRulesScreen.markInvalid(ruleEntry);
    }

    static /* synthetic */ Minecraft method_27630(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.minecraft;
    }

    static /* synthetic */ Font method_27631(EditGameRulesScreen editGameRulesScreen) {
        return editGameRulesScreen.font;
    }

    static /* synthetic */ void method_27623(EditGameRulesScreen editGameRulesScreen, List list) {
        editGameRulesScreen.setTooltip(list);
    }
}
