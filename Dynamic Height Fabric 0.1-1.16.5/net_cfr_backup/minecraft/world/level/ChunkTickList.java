/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.ChunkTickList$ScheduledTick
 *  net.minecraft.world.level.TickList
 *  net.minecraft.world.level.TickNextTickData
 *  net.minecraft.world.level.TickPriority
 */
package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkTickList;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickNextTickData;
import net.minecraft.world.level.TickPriority;

/*
 * Exception performing whole class analysis ignored.
 */
public class ChunkTickList<T>
implements TickList<T> {
    private final List<ScheduledTick<T>> ticks;
    private final Function<T, ResourceLocation> toId;

    public ChunkTickList(Function<T, ResourceLocation> function, List<TickNextTickData<T>> list, long l) {
        this(function, list.stream().map(tickNextTickData -> new ScheduledTick(tickNextTickData.getType(), tickNextTickData.pos, (int)(tickNextTickData.triggerTick - l), tickNextTickData.priority, null)).collect(Collectors.toList()));
    }

    private ChunkTickList(Function<T, ResourceLocation> function, List<ScheduledTick<T>> list) {
        this.ticks = list;
        this.toId = function;
    }

    public boolean hasScheduledTick(BlockPos blockPos, T object) {
        return false;
    }

    public void scheduleTick(BlockPos blockPos, T object, int i, TickPriority tickPriority) {
        this.ticks.add(new ScheduledTick(object, blockPos, i, tickPriority, null));
    }

    public boolean willTickThisTick(BlockPos blockPos, T object) {
        return false;
    }

    public ListTag save() {
        ListTag listTag = new ListTag();
        for (ScheduledTick<T> scheduledTick : this.ticks) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("i", this.toId.apply(ScheduledTick.method_26370(scheduledTick)).toString());
            compoundTag.putInt("x", scheduledTick.pos.getX());
            compoundTag.putInt("y", scheduledTick.pos.getY());
            compoundTag.putInt("z", scheduledTick.pos.getZ());
            compoundTag.putInt("t", scheduledTick.delay);
            compoundTag.putInt("p", scheduledTick.priority.getValue());
            listTag.add((Object)compoundTag);
        }
        return listTag;
    }

    public static <T> ChunkTickList<T> create(ListTag listTag, Function<T, ResourceLocation> function, Function<ResourceLocation, T> function2) {
        ArrayList list = Lists.newArrayList();
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            T object = function2.apply(new ResourceLocation(compoundTag.getString("i")));
            if (object == null) continue;
            BlockPos blockPos = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
            list.add(new ScheduledTick(object, blockPos, compoundTag.getInt("t"), TickPriority.byValue((int)compoundTag.getInt("p")), null));
        }
        return new ChunkTickList<T>(function, list);
    }

    public void copyOut(TickList<T> tickList) {
        this.ticks.forEach(scheduledTick -> tickList.scheduleTick(scheduledTick.pos, ScheduledTick.method_26370((ScheduledTick)scheduledTick), scheduledTick.delay, scheduledTick.priority));
    }
}
