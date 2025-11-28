package net.minecraft.util.profiling;

import java.util.Set;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public interface ProfileCollector extends ProfilerFiller {
	ProfileResults getResults();

	@Nullable
	ActiveProfiler.PathEntry getEntry(String string);

	Set<Pair<String, MetricCategory>> getChartedPaths();
}
