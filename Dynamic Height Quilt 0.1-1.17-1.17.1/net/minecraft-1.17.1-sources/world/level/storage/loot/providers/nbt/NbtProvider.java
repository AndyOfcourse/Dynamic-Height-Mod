package net.minecraft.world.level.storage.loot.providers.nbt;

import java.util.Set;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.jetbrains.annotations.Nullable;

public interface NbtProvider {
	@Nullable
	Tag get(LootContext lootContext);

	Set<LootContextParam<?>> getReferencedContextParams();

	LootNbtProviderType getType();
}
