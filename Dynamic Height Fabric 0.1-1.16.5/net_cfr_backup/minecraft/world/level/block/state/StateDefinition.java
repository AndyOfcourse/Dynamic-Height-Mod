/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSortedMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.MapDecoder
 *  com.mojang.serialization.MapEncoder
 *  net.minecraft.world.level.block.state.StateDefinition$Factory
 *  net.minecraft.world.level.block.state.StateHolder
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.Property$Value
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class StateDefinition<O, S extends StateHolder<O, S>> {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
    private final O owner;
    private final ImmutableSortedMap<String, Property<?>> propertiesByName;
    private final ImmutableList<S> states;

    protected StateDefinition(Function<O, S> function, O object, Factory<O, S> factory, Map<String, Property<?>> map) {
        this.owner = object;
        this.propertiesByName = ImmutableSortedMap.copyOf(map);
        Supplier<StateHolder> supplier = () -> (StateHolder)function.apply(object);
        MapCodec<StateHolder> mapCodec = MapCodec.of((MapEncoder)Encoder.empty(), (MapDecoder)Decoder.unit(supplier));
        for (Map.Entry entry : this.propertiesByName.entrySet()) {
            mapCodec = StateDefinition.appendPropertyCodec(mapCodec, supplier, (String)entry.getKey(), (Property)entry.getValue());
        }
        MapCodec<StateHolder> mapCodec2 = mapCodec;
        LinkedHashMap map2 = Maps.newLinkedHashMap();
        ArrayList list3 = Lists.newArrayList();
        Stream<List<List<Object>>> stream = Stream.of(Collections.emptyList());
        for (Property property : this.propertiesByName.values()) {
            stream = stream.flatMap(list -> property.getPossibleValues().stream().map(comparable -> {
                ArrayList list2 = Lists.newArrayList((Iterable)list);
                list2.add(Pair.of((Object)property, (Object)comparable));
                return list2;
            }));
        }
        stream.forEach(list2 -> {
            ImmutableMap immutableMap = (ImmutableMap)list2.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
            StateHolder stateHolder = (StateHolder)factory.create(object, immutableMap, mapCodec2);
            map2.put(immutableMap, stateHolder);
            list3.add(stateHolder);
        });
        for (StateHolder stateHolder : list3) {
            stateHolder.populateNeighbours((Map)map2);
        }
        this.states = ImmutableList.copyOf((Collection)list3);
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> appendPropertyCodec(MapCodec<S> mapCodec, Supplier<S> supplier, String string, Property<T> property) {
        return Codec.mapPair(mapCodec, (MapCodec)property.valueCodec().fieldOf(string).setPartial(() -> property.value((StateHolder)supplier.get()))).xmap(pair -> (StateHolder)((StateHolder)pair.getFirst()).setValue(property, ((Property.Value)pair.getSecond()).value()), stateHolder -> Pair.of((Object)stateHolder, (Object)property.value(stateHolder)));
    }

    public ImmutableList<S> getPossibleStates() {
        return this.states;
    }

    public S any() {
        return (S)((StateHolder)this.states.get(0));
    }

    public O getOwner() {
        return this.owner;
    }

    public Collection<Property<?>> getProperties() {
        return this.propertiesByName.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("block", this.owner).add("properties", this.propertiesByName.values().stream().map(Property::getName).collect(Collectors.toList())).toString();
    }

    @Nullable
    public Property<?> getProperty(String string) {
        return (Property)this.propertiesByName.get((Object)string);
    }

    static /* synthetic */ Pattern method_11665() {
        return NAME_PATTERN;
    }
}
