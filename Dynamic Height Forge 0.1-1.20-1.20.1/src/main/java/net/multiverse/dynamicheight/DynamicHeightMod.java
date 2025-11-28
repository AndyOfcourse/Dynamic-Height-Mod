package net.multiverse.dynamicheight;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.multiverse.dynamicheight.client.WorldHeightClientNetworking;
import net.multiverse.dynamicheight.client.WorldHeightScreenHooks;
import net.multiverse.dynamicheight.client.WorldHeightSettings;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

@Mod(DynamicHeightMod.MOD_ID)
public class DynamicHeightMod {
    public static final String MOD_ID = "dynamicheight";

    public DynamicHeightMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        WorldHeightNetwork.register();
        event.enqueueWork(WorldHeightManager::register);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                WorldHeightSettings.resetToDefaults();
                WorldHeightScreenHooks.register();
                WorldHeightClientNetworking.register();
            });
        }
    }
}
