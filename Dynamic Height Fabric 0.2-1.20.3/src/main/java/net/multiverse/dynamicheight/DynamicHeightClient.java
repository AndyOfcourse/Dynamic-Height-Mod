
package net.multiverse.dynamicheight;

import net.fabricmc.api.ClientModInitializer;
import net.multiverse.dynamicheight.client.WorldHeightClientNetworking;
import net.multiverse.dynamicheight.client.WorldHeightScreenHooks;
import net.multiverse.dynamicheight.client.WorldHeightSettings;

public final class DynamicHeightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WorldHeightSettings.resetToDefaults();
        WorldHeightScreenHooks.register();
        WorldHeightClientNetworking.register();
    }
}
