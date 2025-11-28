package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public interface DataProvider {
   HashFunction SHA1 = Hashing.sha1();

   void run(HashCache hashCache) throws IOException;

   String getName();

   static void save(Gson gson, HashCache hashCache, JsonElement jsonElement, Path path) throws IOException {
      String string = gson.toJson(jsonElement);
      String string2 = SHA1.hashUnencodedChars(string).toString();
      if (!Objects.equals(hashCache.getHash(path), string2) || !Files.exists(path)) {
         Files.createDirectories(path.getParent());

         try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(string);
         }
      }

      hashCache.putNew(path, string2);
   }
}
