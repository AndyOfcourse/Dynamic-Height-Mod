/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.blaze3d.font.GlyphProvider
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.Util
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.font.AllMissingGlyphProvider
 *  net.minecraft.client.gui.font.FontSet
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.font.GlyphProvider;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.AllMissingGlyphProvider;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(value=EnvType.CLIENT)
public class FontManager
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation MISSING_FONT = new ResourceLocation("minecraft", "missing");
    private final FontSet missingFontSet;
    private final Map<ResourceLocation, FontSet> fontSets = Maps.newHashMap();
    private final TextureManager textureManager;
    private Map<ResourceLocation, ResourceLocation> renames = ImmutableMap.of();
    private final PreparableReloadListener reloadListener = new /* Unavailable Anonymous Inner Class!! */;

    public FontManager(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.missingFontSet = (FontSet)Util.make((Object)new FontSet(textureManager, MISSING_FONT), fontSet -> fontSet.reload((List)Lists.newArrayList((Object[])new GlyphProvider[]{new AllMissingGlyphProvider()})));
    }

    public void setRenames(Map<ResourceLocation, ResourceLocation> map) {
        this.renames = map;
    }

    public Font createFont() {
        return new Font(resourceLocation -> this.fontSets.getOrDefault(this.renames.getOrDefault(resourceLocation, (ResourceLocation)resourceLocation), this.missingFontSet));
    }

    public PreparableReloadListener getReloadListener() {
        return this.reloadListener;
    }

    @Override
    public void close() {
        this.fontSets.values().forEach(FontSet::close);
        this.missingFontSet.close();
    }

    static /* synthetic */ Logger method_18629() {
        return LOGGER;
    }

    static /* synthetic */ Map method_18630(FontManager fontManager) {
        return fontManager.fontSets;
    }

    static /* synthetic */ TextureManager method_18632(FontManager fontManager) {
        return fontManager.textureManager;
    }
}
