/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.debug.DebugRenderer$SimpleDebugRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

@Environment(value=EnvType.CLIENT)
public class StructureRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft minecraft;
    private final Map<DimensionType, Map<String, BoundingBox>> postMainBoxes = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, BoundingBox>> postPiecesBoxes = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, Boolean>> startPiecesMap = Maps.newIdentityHashMap();

    public StructureRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, double d, double e, double f) {
        Camera camera = this.minecraft.gameRenderer.getMainCamera();
        ClientLevel levelAccessor = this.minecraft.level;
        DimensionType dimensionType = levelAccessor.dimensionType();
        BlockPos blockPos = new BlockPos(camera.getPosition().x, 0.0, camera.getPosition().z);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.lines());
        if (this.postMainBoxes.containsKey(dimensionType)) {
            for (BoundingBox boundingBox : this.postMainBoxes.get(dimensionType).values()) {
                if (!blockPos.closerThan(boundingBox.getCenter(), 500.0)) continue;
                LevelRenderer.renderLineBox((PoseStack)poseStack, (VertexConsumer)vertexConsumer, (double)((double)boundingBox.x0 - d), (double)((double)boundingBox.y0 - e), (double)((double)boundingBox.z0 - f), (double)((double)(boundingBox.x1 + 1) - d), (double)((double)(boundingBox.y1 + 1) - e), (double)((double)(boundingBox.z1 + 1) - f), (float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
        if (this.postPiecesBoxes.containsKey(dimensionType)) {
            for (Map.Entry entry : this.postPiecesBoxes.get(dimensionType).entrySet()) {
                String string = (String)entry.getKey();
                BoundingBox boundingBox2 = (BoundingBox)entry.getValue();
                Boolean boolean_ = this.startPiecesMap.get(dimensionType).get(string);
                if (!blockPos.closerThan(boundingBox2.getCenter(), 500.0)) continue;
                if (boolean_.booleanValue()) {
                    LevelRenderer.renderLineBox((PoseStack)poseStack, (VertexConsumer)vertexConsumer, (double)((double)boundingBox2.x0 - d), (double)((double)boundingBox2.y0 - e), (double)((double)boundingBox2.z0 - f), (double)((double)(boundingBox2.x1 + 1) - d), (double)((double)(boundingBox2.y1 + 1) - e), (double)((double)(boundingBox2.z1 + 1) - f), (float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    continue;
                }
                LevelRenderer.renderLineBox((PoseStack)poseStack, (VertexConsumer)vertexConsumer, (double)((double)boundingBox2.x0 - d), (double)((double)boundingBox2.y0 - e), (double)((double)boundingBox2.z0 - f), (double)((double)(boundingBox2.x1 + 1) - d), (double)((double)(boundingBox2.y1 + 1) - e), (double)((double)(boundingBox2.z1 + 1) - f), (float)0.0f, (float)0.0f, (float)1.0f, (float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
        }
    }

    public void addBoundingBox(BoundingBox boundingBox, List<BoundingBox> list, List<Boolean> list2, DimensionType dimensionType) {
        if (!this.postMainBoxes.containsKey(dimensionType)) {
            this.postMainBoxes.put(dimensionType, Maps.newHashMap());
        }
        if (!this.postPiecesBoxes.containsKey(dimensionType)) {
            this.postPiecesBoxes.put(dimensionType, Maps.newHashMap());
            this.startPiecesMap.put(dimensionType, Maps.newHashMap());
        }
        this.postMainBoxes.get(dimensionType).put(boundingBox.toString(), boundingBox);
        for (int i = 0; i < list.size(); ++i) {
            BoundingBox boundingBox2 = list.get(i);
            Boolean boolean_ = list2.get(i);
            this.postPiecesBoxes.get(dimensionType).put(boundingBox2.toString(), boundingBox2);
            this.startPiecesMap.get(dimensionType).put(boundingBox2.toString(), boolean_);
        }
    }

    public void clear() {
        this.postMainBoxes.clear();
        this.postPiecesBoxes.clear();
        this.startPiecesMap.clear();
    }
}
