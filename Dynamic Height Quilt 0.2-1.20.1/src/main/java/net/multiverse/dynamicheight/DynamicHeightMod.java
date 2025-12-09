package net.multiverse.dynamicheight;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

public final class DynamicHeightMod implements ModInitializer {
    public static final String MOD_ID = "dynamicheight";

    @Override
    public void onInitialize(ModContainer mod) {
        WorldHeightNetwork.registerServer();
        WorldHeightManager.registerEvents();
    }
}
