/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  net.minecraft.world.level.block.state.StateHolder
 *  net.minecraft.world.level.block.state.properties.Property$Value
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class Property<T extends Comparable<T>> {
    private final Class<T> clazz;
    private final String name;
    private Integer hashCode;
    private final Codec<T> codec = Codec.STRING.comapFlatMap(string -> this.getValue((String)string).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unable to read property: " + this + " with value: " + string))), this::getName);
    private final Codec<Value<T>> valueCodec = this.codec.xmap(this::value, Value::value);

    protected Property(String string2, Class<T> class_) {
        this.clazz = class_;
        this.name = string2;
    }

    public Value<T> value(T comparable) {
        return new Value(this, comparable, null);
    }

    public Value<T> value(StateHolder<?, ?> stateHolder) {
        return new Value(this, stateHolder.getValue(this), null);
    }

    public Stream<Value<T>> getAllValues() {
        return this.getPossibleValues().stream().map(this::value);
    }

    public Codec<Value<T>> valueCodec() {
        return this.valueCodec;
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getValueClass() {
        return this.clazz;
    }

    public abstract Collection<T> getPossibleValues();

    public abstract String getName(T var1);

    public abstract Optional<T> getValue(String var1);

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("name", (Object)this.name).add("clazz", this.clazz).add("values", this.getPossibleValues()).toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Property) {
            Property property = (Property)object;
            return this.clazz.equals(property.clazz) && this.name.equals(property.name);
        }
        return false;
    }

    public final int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = this.generateHashCode();
        }
        return this.hashCode;
    }

    public int generateHashCode() {
        return 31 * this.clazz.hashCode() + this.name.hashCode();
    }
}
