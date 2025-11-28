/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.searchtree.MutableSearchTree
 *  net.minecraft.client.searchtree.ReloadableIdSearchTree$IntersectionIterator
 *  net.minecraft.client.searchtree.SuffixArray
 *  net.minecraft.resources.ResourceLocation
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.ReloadableIdSearchTree;
import net.minecraft.client.searchtree.SuffixArray;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class ReloadableIdSearchTree<T>
implements MutableSearchTree<T> {
    protected SuffixArray<T> namespaceTree = new SuffixArray();
    protected SuffixArray<T> pathTree = new SuffixArray();
    private final Function<T, Stream<ResourceLocation>> idGetter;
    private final List<T> contents = Lists.newArrayList();
    private final Object2IntMap<T> orderT = new Object2IntOpenHashMap();

    public ReloadableIdSearchTree(Function<T, Stream<ResourceLocation>> function) {
        this.idGetter = function;
    }

    public void refresh() {
        this.namespaceTree = new SuffixArray();
        this.pathTree = new SuffixArray();
        for (T object : this.contents) {
            this.index(object);
        }
        this.namespaceTree.generate();
        this.pathTree.generate();
    }

    public void add(T object) {
        this.orderT.put(object, this.contents.size());
        this.contents.add(object);
        this.index(object);
    }

    public void clear() {
        this.contents.clear();
        this.orderT.clear();
    }

    protected void index(T object) {
        this.idGetter.apply(object).forEach(resourceLocation -> {
            this.namespaceTree.add(object, resourceLocation.getNamespace().toLowerCase(Locale.ROOT));
            this.pathTree.add(object, resourceLocation.getPath().toLowerCase(Locale.ROOT));
        });
    }

    protected int comparePosition(T object, T object2) {
        return Integer.compare(this.orderT.getInt(object), this.orderT.getInt(object2));
    }

    public List<T> search(String string) {
        int i = string.indexOf(58);
        if (i == -1) {
            return this.pathTree.search(string);
        }
        List list = this.namespaceTree.search(string.substring(0, i).trim());
        String string2 = string.substring(i + 1).trim();
        List list2 = this.pathTree.search(string2);
        return Lists.newArrayList((Iterator)new IntersectionIterator(list.iterator(), list2.iterator(), this::comparePosition));
    }
}
