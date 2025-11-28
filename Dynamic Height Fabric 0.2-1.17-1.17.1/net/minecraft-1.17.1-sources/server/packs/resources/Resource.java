package net.minecraft.server.packs.resources;

import java.io.Closeable;
import java.io.InputStream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

public interface Resource extends Closeable {
	ResourceLocation getLocation();

	InputStream getInputStream();

	boolean hasMetadata();

	@Nullable
	<T> T getMetadata(MetadataSectionSerializer<T> metadataSectionSerializer);

	String getSourceName();
}
