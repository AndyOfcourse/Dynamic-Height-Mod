package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

@Environment(EnvType.CLIENT)
public class PopupScreen extends Screen {
   private final FormattedText message;
   private final ImmutableList<PopupScreen.ButtonOption> buttonOptions;
   private MultiLineLabel messageLines = MultiLineLabel.EMPTY;
   private int contentTop;
   private int buttonWidth;

   protected PopupScreen(Component component, List<FormattedText> list, ImmutableList<PopupScreen.ButtonOption> immutableList) {
      super(component);
      this.message = FormattedText.composite(list);
      this.buttonOptions = immutableList;
   }

   @Override
   public String getNarrationMessage() {
      return super.getNarrationMessage() + ". " + this.message.getString();
   }

   @Override
   public void init(Minecraft minecraft, int i, int j) {
      super.init(minecraft, i, j);

      PopupScreen.ButtonOption buttonOption;
      for(UnmodifiableIterator k = this.buttonOptions.iterator();
         k.hasNext();
         this.buttonWidth = Math.max(this.buttonWidth, 20 + this.font.width(buttonOption.message) + 20)
      ) {
         buttonOption = (PopupScreen.ButtonOption)k.next();
      }

      int k = 5 + this.buttonWidth + 5;
      int l = k * this.buttonOptions.size();
      this.messageLines = MultiLineLabel.create(this.font, this.message, l);
      int m = this.messageLines.getLineCount() * 9;
      this.contentTop = (int)((double)j / 2.0 - (double)m / 2.0);
      int n = this.contentTop + m + 9 * 2;
      int o = (int)((double)i / 2.0 - (double)l / 2.0);

      for(UnmodifiableIterator var9 = this.buttonOptions.iterator(); var9.hasNext(); o += k) {
         PopupScreen.ButtonOption buttonOption2 = (PopupScreen.ButtonOption)var9.next();
         this.addButton(new Button(o, n, this.buttonWidth, 20, buttonOption2.message, buttonOption2.onPress));
      }
   }

   @Override
   public void render(PoseStack poseStack, int i, int j, float f) {
      this.renderDirtBackground(0);
      drawCenteredString(poseStack, this.font, this.title, this.width / 2, this.contentTop - 9 * 2, -1);
      this.messageLines.renderCentered(poseStack, this.width / 2, this.contentTop);
      super.render(poseStack, i, j, f);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Environment(EnvType.CLIENT)
   public static final class ButtonOption {
      private final Component message;
      private final Button.OnPress onPress;

      public ButtonOption(Component component, Button.OnPress onPress) {
         this.message = component;
         this.onPress = onPress;
      }
   }
}
