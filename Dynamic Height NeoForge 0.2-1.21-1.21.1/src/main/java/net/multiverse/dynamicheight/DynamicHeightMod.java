package net.multiverse.dynamicheight;

import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(DynamicHeightMod.MOD_ID)
public final class DynamicHeightMod {
    public static final String MOD_ID = "dynamicheight";

    public DynamicHeightMod(IEventBus modEventBus) {
        modEventBus.addListener(WorldHeightNetwork::register);
        NeoForge.EVENT_BUS.addListener(WorldHeightManager::onLevelLoad);
        NeoForge.EVENT_BUS.addListener(WorldHeightManager::onLevelUnload);
        NeoForge.EVENT_BUS.addListener(WorldHeightManager::onRightClickBlock);
        NeoForge.EVENT_BUS.addListener(WorldHeightNetwork::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(WorldHeightNetwork::onPlayerChangedDimension);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            DynamicHeightModClientHooks.registerClientEvents(modEventBus);
        }
    }
}
