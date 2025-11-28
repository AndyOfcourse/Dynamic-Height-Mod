package net.minecraft.world.level.storage.loot.providers.score;

import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.jetbrains.annotations.Nullable;

public interface ScoreboardNameProvider {
	@Nullable
	String getScoreboardName(LootContext lootContext);

	LootScoreProviderType getType();

	Set<LootContextParam<?>> getReferencedContextParams();
}
