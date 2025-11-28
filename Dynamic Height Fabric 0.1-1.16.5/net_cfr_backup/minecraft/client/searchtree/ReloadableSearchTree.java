/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.searchtree.ReloadableIdSearchTree
 *  net.minecraft.client.searchtree.ReloadableIdSearchTree$IntersectionIterator
 *  net.minecraft.client.searchtree.ReloadableSearchTree$MergingUniqueIterator
 *  net.minecraft.client.searchtree.SuffixArray
 *  net.minecraft.resources.ResourceLocation
 */
package net.minecraft.client.searchtree;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.searchtree.ReloadableIdSearchTree;
import net.minecraft.client.searchtree.ReloadableSearchTree;
import net.minecraft.client.searchtree.SuffixArray;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class ReloadableSearchTree<T>
extends ReloadableIdSearchTree<T> {
    protected SuffixArray<T> tree = new SuffixArray();
    private final Function<T, Stream<String>> filler;

    public ReloadableSearchTree(Function<T, Stream<String>> function, Function<T, Stream<ResourceLocation>> function2) {
        super(function2);
        this.filler = function;
    }

    public void refresh() {
        this.tree = new SuffixArray();
        super.refresh();
        this.tree.generate();
    }

    protected void index(T object) {
        super.index(object);
        this.filler.apply(object).forEach(string -> this.tree.add(object, string.toLowerCase(Locale.ROOT)));
    }

    public List<T> search(String string) {
        int i = string.indexOf(58);
        if (i < 0) {
            return this.tree.search(string);
        }
        List list = this.namespaceTree.search(string.substring(0, i).trim());
        String string2 = string.substring(i + 1).trim();
        List list2 = this.pathTree.search(string2);
        List list3 = this.tree.search(string2);
        return Lists.newArrayList((Iterator)new ReloadableIdSearchTree.IntersectionIterator(list.iterator(), (Iterator)new MergingUniqueIterator(list2.iterator(), list3.iterator(), (arg_0, arg_1) -> ((ReloadableSearchTree)this).comparePosition(arg_0, arg_1)), (arg_0, arg_1) -> ((ReloadableSearchTree)this).comparePosition(arg_0, arg_1)));
    }
}
