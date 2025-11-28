package net.multiverse.dynamicheight;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import net.multiverse.dynamicheight.client.WorldHeightClientNetworking;
import net.multiverse.dynamicheight.client.WorldHeightScreenHooks;
import net.multiverse.dynamicheight.client.WorldHeightSettings;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;

public final class DynamicHeightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        WorldHeightSettings.resetToDefaults();
        WorldHeightNetwork.registerClient();
        WorldHeightScreenHooks.register();
        WorldHeightClientNetworking.register();
    }
}
