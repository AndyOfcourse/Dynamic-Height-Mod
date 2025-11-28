
package net.multiverse.dynamicheight;

import net.fabricmc.api.ModInitializer;
import net.multiverse.dynamicheight.network.WorldHeightNetwork;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

public final class DynamicHeightMod implements ModInitializer {
    public static final String MOD_ID = "dynamicheight";

    @Override
    public void onInitialize() {
        WorldHeightNetwork.register();
        WorldHeightManager.register();
    }
}
