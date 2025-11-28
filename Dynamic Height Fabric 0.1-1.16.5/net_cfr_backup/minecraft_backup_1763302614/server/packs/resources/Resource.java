package net.minecraft.server.packs.resources;

import java.io.Closeable;
import java.io.InputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

public interface Resource extends Closeable {
	@Environment(EnvType.CLIENT)
	ResourceLocation getLocation();

	InputStream getInputStream();

	@Nullable
	@Environment(EnvType.CLIENT)
	<T> T getMetadata(MetadataSectionSerializer<T> metadataSectionSerializer);

	String getSourceName();
}
