/*
 * Decompiled with CFR 0.152.
 */
package net.multiverse.dynamicheight.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Map;

public final class RecordReflection {
    private RecordReflection() {
    }

    public static <T> T cloneRecordWith(T record, Map<String, ?> overrides) {
        if (record == null) {
            throw new IllegalArgumentException("Cannot clone null record");
        }
        Class<?> recordClass = record.getClass();
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException("Expected record instance but found " + recordClass.getName());
        }
        try {
            RecordComponent[] components = recordClass.getRecordComponents();
            Object[] args = new Object[components.length];
            Class[] paramTypes = new Class[components.length];
            for (int i = 0; i < components.length; ++i) {
                Object value;
                RecordComponent component = components[i];
                paramTypes[i] = component.getType();
                args[i] = value = overrides.containsKey(component.getName()) ? overrides.get(component.getName()) : RecordReflection.readComponentValue(record, component);
            }
            Constructor<?> ctor = recordClass.getDeclaredConstructor(paramTypes);
            if (!ctor.canAccess(null)) {
                ctor.setAccessible(true);
            }
            return (T)ctor.newInstance(args);
        }
        catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to clone record " + String.valueOf(record.getClass()), e);
        }
    }

    private static Object readComponentValue(Object record, RecordComponent component) throws ReflectiveOperationException {
        Method accessor = component.getAccessor();
        if (accessor != null) {
            if (!accessor.canAccess(record)) {
                accessor.setAccessible(true);
            }
            return accessor.invoke(record, new Object[0]);
        }
        try {
            Method fallback = record.getClass().getDeclaredMethod(component.getName(), new Class[0]);
            if (!fallback.canAccess(record)) {
                fallback.setAccessible(true);
            }
            return fallback.invoke(record, new Object[0]);
        }
        catch (NoSuchMethodException ignored) {
            Field field = record.getClass().getDeclaredField(component.getName());
            if (!field.canAccess(record)) {
                field.setAccessible(true);
            }
            return field.get(record);
        }
    }
}

