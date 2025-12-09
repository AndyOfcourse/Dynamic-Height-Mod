/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.network.Channel
 *  net.minecraftforge.network.ChannelBuilder
 *  net.minecraftforge.network.SimpleChannel
 */
package net.multiverse.dynamicheight.network;

import java.lang.reflect.Method;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import net.multiverse.dynamicheight.network.WorldHeightSyncPacket;

public final class WorldHeightNetwork {
    private static final int PROTOCOL_VERSION = 1;
    private static int nextMessageId = 0;
    private static final ResourceLocation CHANNEL_NAME = resolveChannelName();
    public static final SimpleChannel CHANNEL = ChannelBuilder
            .named(CHANNEL_NAME)
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();

    private WorldHeightNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(WorldHeightSyncPacket.class, nextMessageId++)
                .encoder(WorldHeightSyncPacket::encode)
                .decoder(WorldHeightSyncPacket::decode)
                .consumerNetworkThread(WorldHeightSyncPacket::handle)
                .add();
    }

    public static void broadcast(net.minecraft.server.level.ServerLevel level, net.multiverse.dynamicheight.worldheight.WorldHeightSavedData data) {
        // TODO: add a server-to-client payload if client-side visuals need the updated height.
    }

    /**
     * Resolve a channel name that works across mapping/obfuscation differences.
     */
    private static ResourceLocation resolveChannelName() {
        final String fullId = "dynamicheight:main";
        // Preferred: fromNamespaceAndPath(namespace, path)
        ResourceLocation rl = tryStaticFactory(
                ResourceLocation.class,
                new String[]{"fromNamespaceAndPath", "m_339182_"},
                new Class<?>[]{String.class, String.class},
                new Object[]{"dynamicheight", "main"});
        if (rl != null) {
            return rl;
        }
        // Fallback: withDefaultNamespace(fullId)
        rl = tryStaticFactory(
                ResourceLocation.class,
                new String[]{"withDefaultNamespace", "m_135820_"},
                new Class<?>[]{String.class},
                new Object[]{fullId});
        if (rl != null) {
            return rl;
        }
        // Fallback: parse(fullId)
        rl = tryStaticFactory(
                ResourceLocation.class,
                new String[]{"parse", "m_338530_"},
                new Class<?>[]{String.class},
                new Object[]{fullId});
        if (rl != null) {
            return rl;
        }
        // Last resort: tryParse(fullId)
        rl = tryStaticFactory(
                ResourceLocation.class,
                new String[]{"tryParse", "m_258138_"},
                new Class<?>[]{String.class},
                new Object[]{fullId});
        if (rl != null) {
            return rl;
        }
        throw new IllegalStateException("Unable to resolve ResourceLocation factory for " + fullId);
    }

    private static ResourceLocation tryStaticFactory(Class<?> owner, String[] candidates, Class<?>[] params, Object[] args) {
        for (String name : candidates) {
            try {
                Method m = owner.getDeclaredMethod(name, params);
                m.setAccessible(true);
                Object result = m.invoke(null, args);
                if (result instanceof ResourceLocation rl) {
                    return rl;
                }
            } catch (NoSuchMethodException ignored) {
                // Try next candidate name
            } catch (Exception e) {
                throw new RuntimeException("Failed calling ResourceLocation#" + name, e);
            }
        }
        return null;
    }
}
