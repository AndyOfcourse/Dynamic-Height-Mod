/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.GsonHelper
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.network.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class HoverEvent {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Action<?> action;
    private final Object value;

    public <T> HoverEvent(Action<T> action, T object) {
        this.action = action;
        this.value = object;
    }

    public Action<?> getAction() {
        return this.action;
    }

    @Nullable
    public <T> T getValue(Action<T> action) {
        if (this.action == action) {
            return (T)Action.method_27673(action, (Object)this.value);
        }
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        HoverEvent hoverEvent = (HoverEvent)object;
        return this.action == hoverEvent.action && Objects.equals(this.value, hoverEvent.value);
    }

    public String toString() {
        return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }

    public int hashCode() {
        int i = this.action.hashCode();
        i = 31 * i + (this.value != null ? this.value.hashCode() : 0);
        return i;
    }

    @Nullable
    public static HoverEvent deserialize(JsonObject jsonObject) {
        String string = GsonHelper.getAsString((JsonObject)jsonObject, (String)"action", null);
        if (string == null) {
            return null;
        }
        Action action = Action.getByName((String)string);
        if (action == null) {
            return null;
        }
        JsonElement jsonElement = jsonObject.get("contents");
        if (jsonElement != null) {
            return action.deserialize(jsonElement);
        }
        MutableComponent component = Component.Serializer.fromJson((JsonElement)jsonObject.get("value"));
        if (component != null) {
            return action.deserializeFromLegacy((Component)component);
        }
        return null;
    }

    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", this.action.getName());
        jsonObject.add("contents", this.action.serializeArg(this.value));
        return jsonObject;
    }

    static /* synthetic */ Logger method_27666() {
        return LOGGER;
    }
}
