/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.PeekingIterator
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  net.minecraft.nbt.ByteArrayTag
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.CollectionTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.EndTag
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongArrayTag
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.NbtOps$NbtRecordBuilder
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.ShortTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 */
package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class NbtOps
implements DynamicOps<Tag> {
    public static final NbtOps INSTANCE = new NbtOps();

    protected NbtOps() {
    }

    public Tag empty() {
        return EndTag.INSTANCE;
    }

    public <U> U convertTo(DynamicOps<U> dynamicOps, Tag tag) {
        switch (tag.getId()) {
            case 0: {
                return (U)dynamicOps.empty();
            }
            case 1: {
                return (U)dynamicOps.createByte(((NumericTag)tag).getAsByte());
            }
            case 2: {
                return (U)dynamicOps.createShort(((NumericTag)tag).getAsShort());
            }
            case 3: {
                return (U)dynamicOps.createInt(((NumericTag)tag).getAsInt());
            }
            case 4: {
                return (U)dynamicOps.createLong(((NumericTag)tag).getAsLong());
            }
            case 5: {
                return (U)dynamicOps.createFloat(((NumericTag)tag).getAsFloat());
            }
            case 6: {
                return (U)dynamicOps.createDouble(((NumericTag)tag).getAsDouble());
            }
            case 7: {
                return (U)dynamicOps.createByteList(ByteBuffer.wrap(((ByteArrayTag)tag).getAsByteArray()));
            }
            case 8: {
                return (U)dynamicOps.createString(tag.getAsString());
            }
            case 9: {
                return (U)this.convertList(dynamicOps, tag);
            }
            case 10: {
                return (U)this.convertMap(dynamicOps, tag);
            }
            case 11: {
                return (U)dynamicOps.createIntList(Arrays.stream(((IntArrayTag)tag).getAsIntArray()));
            }
            case 12: {
                return (U)dynamicOps.createLongList(Arrays.stream(((LongArrayTag)tag).getAsLongArray()));
            }
        }
        throw new IllegalStateException("Unknown tag type: " + tag);
    }

    public DataResult<Number> getNumberValue(Tag tag) {
        if (tag instanceof NumericTag) {
            return DataResult.success((Object)((NumericTag)tag).getAsNumber());
        }
        return DataResult.error((String)"Not a number");
    }

    public Tag createNumeric(Number number) {
        return DoubleTag.valueOf((double)number.doubleValue());
    }

    public Tag createByte(byte b) {
        return ByteTag.valueOf((byte)b);
    }

    public Tag createShort(short s) {
        return ShortTag.valueOf((short)s);
    }

    public Tag createInt(int i) {
        return IntTag.valueOf((int)i);
    }

    public Tag createLong(long l) {
        return LongTag.valueOf((long)l);
    }

    public Tag createFloat(float f) {
        return FloatTag.valueOf((float)f);
    }

    public Tag createDouble(double d) {
        return DoubleTag.valueOf((double)d);
    }

    public Tag createBoolean(boolean bl) {
        return ByteTag.valueOf((boolean)bl);
    }

    public DataResult<String> getStringValue(Tag tag) {
        if (tag instanceof StringTag) {
            return DataResult.success((Object)tag.getAsString());
        }
        return DataResult.error((String)"Not a string");
    }

    public Tag createString(String string) {
        return StringTag.valueOf((String)string);
    }

    private static CollectionTag<?> createGenericList(byte b, byte c) {
        if (NbtOps.typesMatch(b, c, (byte)4)) {
            return new LongArrayTag(new long[0]);
        }
        if (NbtOps.typesMatch(b, c, (byte)1)) {
            return new ByteArrayTag(new byte[0]);
        }
        if (NbtOps.typesMatch(b, c, (byte)3)) {
            return new IntArrayTag(new int[0]);
        }
        return new ListTag();
    }

    private static boolean typesMatch(byte b, byte c, byte d) {
        return b == d && (c == d || c == 0);
    }

    private static <T extends Tag> void fillOne(CollectionTag<T> collectionTag, Tag tag2, Tag tag22) {
        if (tag2 instanceof CollectionTag) {
            CollectionTag collectionTag2 = (CollectionTag)tag2;
            collectionTag2.forEach(tag -> collectionTag.add(tag));
        }
        collectionTag.add((Object)tag22);
    }

    private static <T extends Tag> void fillMany(CollectionTag<T> collectionTag, Tag tag2, List<Tag> list) {
        if (tag2 instanceof CollectionTag) {
            CollectionTag collectionTag2 = (CollectionTag)tag2;
            collectionTag2.forEach(tag -> collectionTag.add(tag));
        }
        list.forEach(tag -> collectionTag.add(tag));
    }

    public DataResult<Tag> mergeToList(Tag tag, Tag tag2) {
        if (!(tag instanceof CollectionTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + tag), (Object)tag);
        }
        CollectionTag<?> collectionTag = NbtOps.createGenericList(tag instanceof CollectionTag ? ((CollectionTag)tag).getElementType() : (byte)0, tag2.getId());
        NbtOps.fillOne(collectionTag, tag, tag2);
        return DataResult.success(collectionTag);
    }

    public DataResult<Tag> mergeToList(Tag tag, List<Tag> list) {
        if (!(tag instanceof CollectionTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + tag), (Object)tag);
        }
        CollectionTag<?> collectionTag = NbtOps.createGenericList(tag instanceof CollectionTag ? ((CollectionTag)tag).getElementType() : (byte)0, list.stream().findFirst().map(Tag::getId).orElse((byte)0));
        NbtOps.fillMany(collectionTag, tag, list);
        return DataResult.success(collectionTag);
    }

    public DataResult<Tag> mergeToMap(Tag tag, Tag tag2, Tag tag3) {
        if (!(tag instanceof CompoundTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + tag), (Object)tag);
        }
        if (!(tag2 instanceof StringTag)) {
            return DataResult.error((String)("key is not a string: " + tag2), (Object)tag);
        }
        CompoundTag compoundTag = new CompoundTag();
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag2 = (CompoundTag)tag;
            compoundTag2.getAllKeys().forEach(string -> compoundTag.put(string, compoundTag2.get(string)));
        }
        compoundTag.put(tag2.getAsString(), tag3);
        return DataResult.success((Object)compoundTag);
    }

    public DataResult<Tag> mergeToMap(Tag tag, MapLike<Tag> mapLike) {
        if (!(tag instanceof CompoundTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + tag), (Object)tag);
        }
        CompoundTag compoundTag = new CompoundTag();
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag2 = (CompoundTag)tag;
            compoundTag2.getAllKeys().forEach(string -> compoundTag.put(string, compoundTag2.get(string)));
        }
        ArrayList list = Lists.newArrayList();
        mapLike.entries().forEach(pair -> {
            Tag tag = (Tag)pair.getFirst();
            if (!(tag instanceof StringTag)) {
                list.add(tag);
                return;
            }
            compoundTag.put(tag.getAsString(), (Tag)pair.getSecond());
        });
        if (!list.isEmpty()) {
            return DataResult.error((String)("some keys are not strings: " + list), (Object)compoundTag);
        }
        return DataResult.success((Object)compoundTag);
    }

    public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success(compoundTag.getAllKeys().stream().map(string -> Pair.of((Object)this.createString((String)string), (Object)compoundTag.get(string))));
    }

    public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success(biConsumer -> compoundTag.getAllKeys().forEach(string -> biConsumer.accept(this.createString((String)string), compoundTag.get(string))));
    }

    public DataResult<MapLike<Tag>> getMap(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success((Object)new /* Unavailable Anonymous Inner Class!! */);
    }

    public Tag createMap(Stream<Pair<Tag, Tag>> stream) {
        CompoundTag compoundTag = new CompoundTag();
        stream.forEach(pair -> compoundTag.put(((Tag)pair.getFirst()).getAsString(), (Tag)pair.getSecond()));
        return compoundTag;
    }

    public DataResult<Stream<Tag>> getStream(Tag tag2) {
        if (tag2 instanceof CollectionTag) {
            return DataResult.success(((CollectionTag)tag2).stream().map(tag -> tag));
        }
        return DataResult.error((String)"Not a list");
    }

    public DataResult<Consumer<Consumer<Tag>>> getList(Tag tag) {
        if (tag instanceof CollectionTag) {
            CollectionTag collectionTag = (CollectionTag)tag;
            return DataResult.success(arg_0 -> collectionTag.forEach(arg_0));
        }
        return DataResult.error((String)("Not a list: " + tag));
    }

    public DataResult<ByteBuffer> getByteBuffer(Tag tag) {
        if (tag instanceof ByteArrayTag) {
            return DataResult.success((Object)ByteBuffer.wrap(((ByteArrayTag)tag).getAsByteArray()));
        }
        return super.getByteBuffer((Object)tag);
    }

    public Tag createByteList(ByteBuffer byteBuffer) {
        return new ByteArrayTag(DataFixUtils.toArray((ByteBuffer)byteBuffer));
    }

    public DataResult<IntStream> getIntStream(Tag tag) {
        if (tag instanceof IntArrayTag) {
            return DataResult.success((Object)Arrays.stream(((IntArrayTag)tag).getAsIntArray()));
        }
        return super.getIntStream((Object)tag);
    }

    public Tag createIntList(IntStream intStream) {
        return new IntArrayTag(intStream.toArray());
    }

    public DataResult<LongStream> getLongStream(Tag tag) {
        if (tag instanceof LongArrayTag) {
            return DataResult.success((Object)Arrays.stream(((LongArrayTag)tag).getAsLongArray()));
        }
        return super.getLongStream((Object)tag);
    }

    public Tag createLongList(LongStream longStream) {
        return new LongArrayTag(longStream.toArray());
    }

    public Tag createList(Stream<Tag> stream) {
        PeekingIterator peekingIterator = Iterators.peekingIterator(stream.iterator());
        if (!peekingIterator.hasNext()) {
            return new ListTag();
        }
        Tag tag2 = (Tag)peekingIterator.peek();
        if (tag2 instanceof ByteTag) {
            ArrayList list = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((ByteTag)tag).getAsByte()));
            return new ByteArrayTag((List)list);
        }
        if (tag2 instanceof IntTag) {
            ArrayList list = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((IntTag)tag).getAsInt()));
            return new IntArrayTag((List)list);
        }
        if (tag2 instanceof LongTag) {
            ArrayList list = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((LongTag)tag).getAsLong()));
            return new LongArrayTag((List)list);
        }
        ListTag listTag = new ListTag();
        while (peekingIterator.hasNext()) {
            Tag tag22 = (Tag)peekingIterator.next();
            if (tag22 instanceof EndTag) continue;
            listTag.add((Object)tag22);
        }
        return listTag;
    }

    public Tag remove(Tag tag, String string3) {
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)tag;
            CompoundTag compoundTag2 = new CompoundTag();
            compoundTag.getAllKeys().stream().filter(string2 -> !Objects.equals(string2, string3)).forEach(string -> compoundTag2.put(string, compoundTag.get(string)));
            return compoundTag2;
        }
        return tag;
    }

    public String toString() {
        return "NBT";
    }

    public RecordBuilder<Tag> mapBuilder() {
        return new NbtRecordBuilder(this);
    }

    public /* synthetic */ Object remove(Object object, String string) {
        return this.remove((Tag)object, string);
    }

    public /* synthetic */ Object createLongList(LongStream longStream) {
        return this.createLongList(longStream);
    }

    public /* synthetic */ DataResult getLongStream(Object object) {
        return this.getLongStream((Tag)object);
    }

    public /* synthetic */ Object createIntList(IntStream intStream) {
        return this.createIntList(intStream);
    }

    public /* synthetic */ DataResult getIntStream(Object object) {
        return this.getIntStream((Tag)object);
    }

    public /* synthetic */ Object createByteList(ByteBuffer byteBuffer) {
        return this.createByteList(byteBuffer);
    }

    public /* synthetic */ DataResult getByteBuffer(Object object) {
        return this.getByteBuffer((Tag)object);
    }

    public /* synthetic */ Object createList(Stream stream) {
        return this.createList((Stream<Tag>)stream);
    }

    public /* synthetic */ DataResult getList(Object object) {
        return this.getList((Tag)object);
    }

    public /* synthetic */ DataResult getStream(Object object) {
        return this.getStream((Tag)object);
    }

    public /* synthetic */ DataResult getMap(Object object) {
        return this.getMap((Tag)object);
    }

    public /* synthetic */ Object createMap(Stream stream) {
        return this.createMap((Stream<Pair<Tag, Tag>>)stream);
    }

    public /* synthetic */ DataResult getMapEntries(Object object) {
        return this.getMapEntries((Tag)object);
    }

    public /* synthetic */ DataResult getMapValues(Object object) {
        return this.getMapValues((Tag)object);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, MapLike mapLike) {
        return this.mergeToMap((Tag)object, (MapLike<Tag>)mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, Object object2, Object object3) {
        return this.mergeToMap((Tag)object, (Tag)object2, (Tag)object3);
    }

    public /* synthetic */ DataResult mergeToList(Object object, List list) {
        return this.mergeToList((Tag)object, (List<Tag>)list);
    }

    public /* synthetic */ DataResult mergeToList(Object object, Object object2) {
        return this.mergeToList((Tag)object, (Tag)object2);
    }

    public /* synthetic */ Object createString(String string) {
        return this.createString(string);
    }

    public /* synthetic */ DataResult getStringValue(Object object) {
        return this.getStringValue((Tag)object);
    }

    public /* synthetic */ Object createBoolean(boolean bl) {
        return this.createBoolean(bl);
    }

    public /* synthetic */ Object createDouble(double d) {
        return this.createDouble(d);
    }

    public /* synthetic */ Object createFloat(float f) {
        return this.createFloat(f);
    }

    public /* synthetic */ Object createLong(long l) {
        return this.createLong(l);
    }

    public /* synthetic */ Object createInt(int i) {
        return this.createInt(i);
    }

    public /* synthetic */ Object createShort(short s) {
        return this.createShort(s);
    }

    public /* synthetic */ Object createByte(byte b) {
        return this.createByte(b);
    }

    public /* synthetic */ Object createNumeric(Number number) {
        return this.createNumeric(number);
    }

    public /* synthetic */ DataResult getNumberValue(Object object) {
        return this.getNumberValue((Tag)object);
    }

    public /* synthetic */ Object convertTo(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, (Tag)object);
    }

    public /* synthetic */ Object empty() {
        return this.empty();
    }
}
