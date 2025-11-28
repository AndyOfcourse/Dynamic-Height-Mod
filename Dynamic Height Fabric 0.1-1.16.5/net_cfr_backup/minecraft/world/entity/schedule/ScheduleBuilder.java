/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.entity.schedule.Schedule
 *  net.minecraft.world.entity.schedule.ScheduleBuilder$ActivityTransition
 */
package net.minecraft.world.entity.schedule;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;

public class ScheduleBuilder {
    private final Schedule schedule;
    private final List<ActivityTransition> transitions = Lists.newArrayList();

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    public ScheduleBuilder changeActivityAt(int i, Activity activity) {
        this.transitions.add(new ActivityTransition(i, activity));
        return this;
    }

    public Schedule build() {
        this.transitions.stream().map(ActivityTransition::getActivity).collect(Collectors.toSet()).forEach(arg_0 -> ((Schedule)this.schedule).ensureTimelineExistsFor(arg_0));
        this.transitions.forEach(activityTransition -> {
            Activity activity = activityTransition.getActivity();
            this.schedule.getAllTimelinesExceptFor(activity).forEach(timeline -> timeline.addKeyframe(activityTransition.getTime(), 0.0f));
            this.schedule.getTimelineFor(activity).addKeyframe(activityTransition.getTime(), 1.0f);
        });
        return this.schedule;
    }
}
