package net.multiverse.dynamicheight;

import net.multiverse.dynamicheight.client.WorldHeightClientNetworking;
import net.multiverse.dynamicheight.client.WorldHeightScreenHooks;
import net.multiverse.dynamicheight.client.WorldHeightSettings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

final class DynamicHeightModClientHooks {
    private DynamicHeightModClientHooks() {
    }

    static void registerClientEvents(IEventBus modEventBus) {
        modEventBus.addListener(DynamicHeightModClientHooks::onClientSetup);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        WorldHeightSettings.resetToDefaults();
        // Game event bus listeners (client side only)
        NeoForge.EVENT_BUS.addListener(WorldHeightClientNetworking::onClientLoggedIn);
        NeoForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onScreenInit);
        NeoForge.EVENT_BUS.addListener(WorldHeightScreenHooks::onClientTick);
    }
}
