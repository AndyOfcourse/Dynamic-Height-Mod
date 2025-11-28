package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import org.jetbrains.annotations.Nullable;

public class NbtIo {
   public static CompoundTag readCompressed(File file) throws IOException {
      CompoundTag var3;
      try (InputStream inputStream = new FileInputStream(file)) {
         var3 = readCompressed(inputStream);
      }

      return var3;
   }

   public static CompoundTag readCompressed(InputStream inputStream) throws IOException {
      CompoundTag var3;
      try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)))) {
         var3 = read(dataInputStream, NbtAccounter.UNLIMITED);
      }

      return var3;
   }

   public static void writeCompressed(CompoundTag compoundTag, File file) throws IOException {
      try (OutputStream outputStream = new FileOutputStream(file)) {
         writeCompressed(compoundTag, outputStream);
      }
   }

   public static void writeCompressed(CompoundTag compoundTag, OutputStream outputStream) throws IOException {
      try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)))) {
         write(compoundTag, dataOutputStream);
      }
   }

   @Environment(EnvType.CLIENT)
   public static void write(CompoundTag compoundTag, File file) throws IOException {
      try (
         FileOutputStream fileOutputStream = new FileOutputStream(file);
         DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
      ) {
         write(compoundTag, dataOutputStream);
      }
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static CompoundTag read(File file) throws IOException {
      if (!file.exists()) {
         return null;
      } else {
         CompoundTag var5;
         try (
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
         ) {
            var5 = read(dataInputStream, NbtAccounter.UNLIMITED);
         }

         return var5;
      }
   }

   public static CompoundTag read(DataInput dataInput) throws IOException {
      return read(dataInput, NbtAccounter.UNLIMITED);
   }

   public static CompoundTag read(DataInput dataInput, NbtAccounter nbtAccounter) throws IOException {
      Tag tag = readUnnamedTag(dataInput, 0, nbtAccounter);
      if (tag instanceof CompoundTag) {
         return (CompoundTag)tag;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(CompoundTag compoundTag, DataOutput dataOutput) throws IOException {
      writeUnnamedTag(compoundTag, dataOutput);
   }

   private static void writeUnnamedTag(Tag tag, DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(tag.getId());
      if (tag.getId() != 0) {
         dataOutput.writeUTF("");
         tag.write(dataOutput);
      }
   }

   private static Tag readUnnamedTag(DataInput dataInput, int i, NbtAccounter nbtAccounter) throws IOException {
      byte b = dataInput.readByte();
      if (b == 0) {
         return EndTag.INSTANCE;
      } else {
         dataInput.readUTF();

         try {
            return TagTypes.getType(b).load(dataInput, i, nbtAccounter);
         } catch (IOException var7) {
            CrashReport crashReport = CrashReport.forThrowable(var7, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.addCategory("NBT Tag");
            crashReportCategory.setDetail("Tag type", b);
            throw new ReportedException(crashReport);
         }
      }
   }
}
