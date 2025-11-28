/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.renderer.texture.Stitcher$Holder
 *  net.minecraft.client.renderer.texture.Stitcher$Region
 *  net.minecraft.client.renderer.texture.Stitcher$SpriteLoader
 *  net.minecraft.client.renderer.texture.StitcherException
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite$Info
 *  net.minecraft.util.Mth
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.StitcherException;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;

@Environment(value=EnvType.CLIENT)
public class Stitcher {
    private static final Comparator<Holder> HOLDER_COMPARATOR = Comparator.comparing(holder -> -holder.height).thenComparing(holder -> -holder.width).thenComparing(holder -> holder.spriteInfo.name());
    private final int mipLevel;
    private final Set<Holder> texturesToBeStitched = Sets.newHashSetWithExpectedSize((int)256);
    private final List<Region> storage = Lists.newArrayListWithCapacity((int)256);
    private int storageX;
    private int storageY;
    private final int maxWidth;
    private final int maxHeight;

    public Stitcher(int i, int j, int k) {
        this.mipLevel = k;
        this.maxWidth = i;
        this.maxHeight = j;
    }

    public int getWidth() {
        return this.storageX;
    }

    public int getHeight() {
        return this.storageY;
    }

    public void registerSprite(TextureAtlasSprite.Info info) {
        Holder holder = new Holder(info, this.mipLevel);
        this.texturesToBeStitched.add(holder);
    }

    public void stitch() {
        ArrayList list = Lists.newArrayList(this.texturesToBeStitched);
        list.sort(HOLDER_COMPARATOR);
        for (Holder holder2 : list) {
            if (this.addToStorage(holder2)) continue;
            throw new StitcherException(holder2.spriteInfo, (Collection)list.stream().map(holder -> holder.spriteInfo).collect(ImmutableList.toImmutableList()));
        }
        this.storageX = Mth.smallestEncompassingPowerOfTwo((int)this.storageX);
        this.storageY = Mth.smallestEncompassingPowerOfTwo((int)this.storageY);
    }

    public void gatherSprites(SpriteLoader spriteLoader) {
        for (Region region2 : this.storage) {
            region2.walk(region -> {
                Holder holder = region.getHolder();
                TextureAtlasSprite.Info info = holder.spriteInfo;
                spriteLoader.load(info, this.storageX, this.storageY, region.getX(), region.getY());
            });
        }
    }

    private static int smallestFittingMinTexel(int i, int j) {
        return (i >> j) + ((i & (1 << j) - 1) == 0 ? 0 : 1) << j;
    }

    private boolean addToStorage(Holder holder) {
        for (Region region : this.storage) {
            if (!region.add(holder)) continue;
            return true;
        }
        return this.expand(holder);
    }

    private boolean expand(Holder holder) {
        Region region;
        boolean bl5;
        boolean bl4;
        boolean bl2;
        int i = Mth.smallestEncompassingPowerOfTwo((int)this.storageX);
        int j = Mth.smallestEncompassingPowerOfTwo((int)this.storageY);
        int k = Mth.smallestEncompassingPowerOfTwo((int)(this.storageX + holder.width));
        int l = Mth.smallestEncompassingPowerOfTwo((int)(this.storageY + holder.height));
        boolean bl = k <= this.maxWidth;
        boolean bl3 = bl2 = l <= this.maxHeight;
        if (!bl && !bl2) {
            return false;
        }
        boolean bl32 = bl && i != k;
        boolean bl6 = bl4 = bl2 && j != l;
        if (bl32 ^ bl4) {
            bl5 = bl32;
        } else {
            boolean bl7 = bl5 = bl && i <= j;
        }
        if (bl5) {
            if (this.storageY == 0) {
                this.storageY = holder.height;
            }
            region = new Region(this.storageX, 0, holder.width, this.storageY);
            this.storageX += holder.width;
        } else {
            region = new Region(0, this.storageY, this.storageX, holder.height);
            this.storageY += holder.height;
        }
        region.add(holder);
        this.storage.add(region);
        return true;
    }

    static /* synthetic */ int method_4556(int i, int j) {
        return Stitcher.smallestFittingMinTexel(i, j);
    }
}
