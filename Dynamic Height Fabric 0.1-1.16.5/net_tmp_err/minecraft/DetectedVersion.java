package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DetectedVersion implements GameVersion {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final GameVersion BUILT_IN = new DetectedVersion();
   private final String id;
   private final String name;
   private final boolean stable;
   private final int worldVersion;
   private final int protocolVersion;
   private final int packVersion;
   private final Date buildTime;
   private final String releaseTarget;

   private DetectedVersion() {
      this.id = UUID.randomUUID().toString().replaceAll("-", "");
      this.name = "1.16.5";
      this.stable = true;
      this.worldVersion = 2586;
      this.protocolVersion = SharedConstants.getProtocolVersion();
      this.packVersion = 6;
      this.buildTime = new Date();
      this.releaseTarget = "1.16.5";
   }

   private DetectedVersion(JsonObject jsonObject) {
      this.id = GsonHelper.getAsString(jsonObject, "id");
      this.name = GsonHelper.getAsString(jsonObject, "name");
      this.releaseTarget = GsonHelper.getAsString(jsonObject, "release_target");
      this.stable = GsonHelper.getAsBoolean(jsonObject, "stable");
      this.worldVersion = GsonHelper.getAsInt(jsonObject, "world_version");
      this.protocolVersion = GsonHelper.getAsInt(jsonObject, "protocol_version");
      this.packVersion = GsonHelper.getAsInt(jsonObject, "pack_version");
      this.buildTime = Date.from(ZonedDateTime.parse(GsonHelper.getAsString(jsonObject, "build_time")).toInstant());
   }

   public static GameVersion tryDetectVersion() {
      try (InputStream inputStream = DetectedVersion.class.getResourceAsStream("/version.json")) {
         if (inputStream == null) {
            LOGGER.warn("Missing version information!");
            return BUILT_IN;
         } else {
            DetectedVersion var4;
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
               var4 = new DetectedVersion(GsonHelper.parse(inputStreamReader));
            }

            return var4;
         }
      } catch (JsonParseException | IOException var34) {
         throw new IllegalStateException("Game version information is corrupt", var34);
      }
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getReleaseTarget() {
      return this.releaseTarget;
   }

   public int getWorldVersion() {
      return this.worldVersion;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public int getPackVersion() {
      return this.packVersion;
   }

   public Date getBuildTime() {
      return this.buildTime;
   }

   public boolean isStable() {
      return this.stable;
   }
}
