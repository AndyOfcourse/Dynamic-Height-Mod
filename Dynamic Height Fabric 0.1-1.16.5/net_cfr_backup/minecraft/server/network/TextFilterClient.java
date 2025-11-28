/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.SharedConstants
 *  net.minecraft.server.network.TextFilter
 *  net.minecraft.server.network.TextFilterClient$IgnoreStrategy
 *  net.minecraft.server.network.TextFilterClient$PlayerContext
 *  net.minecraft.server.network.TextFilterClient$RequestFailedException
 *  net.minecraft.util.GsonHelper
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SharedConstants;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.network.TextFilterClient;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFilterClient
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1);
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("Chat-Filter-Worker-" + WORKER_COUNT.getAndIncrement());
        return thread;
    };
    private final URL chatEndpoint;
    private final URL joinEndpoint;
    private final URL leaveEndpoint;
    private final String authKey;
    private final int ruleId;
    private final String serverId;
    private final IgnoreStrategy chatIgnoreStrategy;
    private final ExecutorService workerPool;

    private void processJoinOrLeave(GameProfile gameProfile, URL uRL, Executor executor) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server", this.serverId);
        jsonObject.addProperty("room", "Chat");
        jsonObject.addProperty("user_id", gameProfile.getId().toString());
        jsonObject.addProperty("user_display_name", gameProfile.getName());
        executor.execute(() -> {
            try {
                this.processRequest(jsonObject, uRL);
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to send join/leave packet to {} for player {}", (Object)uRL, (Object)gameProfile, (Object)exception);
            }
        });
    }

    private CompletableFuture<Optional<String>> requestMessageProcessing(GameProfile gameProfile, String string, IgnoreStrategy ignoreStrategy, Executor executor) {
        if (string.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.of(""));
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rule", (Number)this.ruleId);
        jsonObject.addProperty("server", this.serverId);
        jsonObject.addProperty("room", "Chat");
        jsonObject.addProperty("player", gameProfile.getId().toString());
        jsonObject.addProperty("player_display_name", gameProfile.getName());
        jsonObject.addProperty("text", string);
        return CompletableFuture.supplyAsync(() -> {
            try {
                JsonObject jsonObject2 = this.processRequestResponse(jsonObject, this.chatEndpoint);
                boolean bl = GsonHelper.getAsBoolean((JsonObject)jsonObject2, (String)"response", (boolean)false);
                if (bl) {
                    return Optional.of(string);
                }
                String string2 = GsonHelper.getAsString((JsonObject)jsonObject2, (String)"hashed", null);
                if (string2 == null) {
                    return Optional.empty();
                }
                int i = GsonHelper.getAsJsonArray((JsonObject)jsonObject2, (String)"hashes").size();
                return ignoreStrategy.shouldIgnore(string2, i) ? Optional.empty() : Optional.of(string2);
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to validate message '{}'", (Object)string, (Object)exception);
                return Optional.empty();
            }
        }, executor);
    }

    @Override
    public void close() {
        this.workerPool.shutdownNow();
    }

    private void drainStream(InputStream inputStream) throws IOException {
        byte[] bs = new byte[1024];
        while (inputStream.read(bs) != -1) {
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JsonObject processRequestResponse(JsonObject jsonObject, URL uRL) throws IOException {
        HttpURLConnection httpURLConnection = this.makeRequest(jsonObject, uRL);
        Throwable throwable = null;
        try (InputStream inputStream = httpURLConnection.getInputStream();){
            JsonObject jsonObject2;
            if (httpURLConnection.getResponseCode() == 204) {
                JsonObject jsonObject3 = new JsonObject();
                return jsonObject3;
            }
            try {
                jsonObject2 = Streams.parse((JsonReader)new JsonReader((Reader)new InputStreamReader(inputStream))).getAsJsonObject();
            }
            catch (Throwable throwable2) {
                try {
                    this.drainStream(inputStream);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            this.drainStream(inputStream);
            return jsonObject2;
        }
    }

    private void processRequest(JsonObject jsonObject, URL uRL) throws IOException {
        HttpURLConnection httpURLConnection = this.makeRequest(jsonObject, uRL);
        try (InputStream inputStream = httpURLConnection.getInputStream();){
            this.drainStream(inputStream);
        }
    }

    private HttpURLConnection makeRequest(JsonObject jsonObject, URL uRL) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(2000);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Authorization", "Basic " + this.authKey);
        httpURLConnection.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getCurrentVersion().getName());
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
             JsonWriter jsonWriter = new JsonWriter((Writer)outputStreamWriter);){
            Streams.write((JsonElement)jsonObject, (JsonWriter)jsonWriter);
        }
        int i = httpURLConnection.getResponseCode();
        if (i < 200 || i >= 300) {
            throw new RequestFailedException(i + " " + httpURLConnection.getResponseMessage(), null);
        }
        return httpURLConnection;
    }

    public TextFilter createContext(GameProfile gameProfile) {
        return new PlayerContext(this, gameProfile, null);
    }

    static /* synthetic */ ExecutorService method_31291(TextFilterClient textFilterClient) {
        return textFilterClient.workerPool;
    }

    static /* synthetic */ URL method_31303(TextFilterClient textFilterClient) {
        return textFilterClient.joinEndpoint;
    }

    static /* synthetic */ void method_31293(TextFilterClient textFilterClient, GameProfile gameProfile, URL uRL, Executor executor) {
        textFilterClient.processJoinOrLeave(gameProfile, uRL, executor);
    }

    static /* synthetic */ URL method_31305(TextFilterClient textFilterClient) {
        return textFilterClient.leaveEndpoint;
    }

    static /* synthetic */ IgnoreStrategy method_31307(TextFilterClient textFilterClient) {
        return textFilterClient.chatIgnoreStrategy;
    }

    static /* synthetic */ CompletableFuture method_31292(TextFilterClient textFilterClient, GameProfile gameProfile, String string, IgnoreStrategy ignoreStrategy, Executor executor) {
        return textFilterClient.requestMessageProcessing(gameProfile, string, ignoreStrategy, executor);
    }
}
