package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.WorldTemplate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.realms.RealmsScreen;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class RealmsScreenWithCallback extends RealmsScreen {
	protected abstract void callback(@Nullable WorldTemplate worldTemplate);
}
