package net.multiverse.dynamicheight.mixin;

import java.util.List;
import java.util.concurrent.Executor;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.RandomSequences;
import net.multiverse.dynamicheight.worldheight.WorldHeightManager;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void dynamicheight$applySavedHeight(MinecraftServer minecraftServer,
                                                Executor executor,
                                                LevelStorageSource.LevelStorageAccess levelStorageAccess,
                                                ServerLevelData serverLevelData,
                                                ResourceKey<Level> resourceKey,
                                                LevelStem levelStem,
                                                ChunkProgressListener chunkProgressListener,
                                                boolean bl,
                                                long seed,
                                                List<CustomSpawner> spawners,
                                                boolean tickTime,
                                                @Nullable RandomSequences randomSequences,
                                                CallbackInfo ci) {
        WorldHeightManager.initializeLevel((ServerLevel) (Object) this);
    }
}
