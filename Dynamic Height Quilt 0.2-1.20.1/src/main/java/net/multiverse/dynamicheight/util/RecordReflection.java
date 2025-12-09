package net.multiverse.dynamicheight.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Map;

/**
 * Minimal helper for duplicating Mojang record instances while overriding a subset of their fields.
 */
public final class RecordReflection {
    private RecordReflection() {
    }

    @SuppressWarnings("unchecked")
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
            Class<?>[] paramTypes = new Class<?>[components.length];

            for (int i = 0; i < components.length; i++) {
                RecordComponent component = components[i];
                paramTypes[i] = component.getType();
                Object value = overrides.containsKey(component.getName())
                        ? overrides.get(component.getName())
                        : readComponentValue(record, component);
                args[i] = value;
            }

            Constructor<?> ctor = recordClass.getDeclaredConstructor(paramTypes);
            if (!ctor.canAccess(null)) {
                ctor.setAccessible(true);
            }
            return (T) ctor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to clone record " + record.getClass(), e);
        }
    }

    private static Object readComponentValue(Object record, RecordComponent component) throws ReflectiveOperationException {
        Method accessor = component.getAccessor();
        if (accessor != null) {
            if (!accessor.canAccess(record)) {
                accessor.setAccessible(true);
            }
            return accessor.invoke(record);
        }

        try {
            Method fallback = record.getClass().getDeclaredMethod(component.getName());
            if (!fallback.canAccess(record)) {
                fallback.setAccessible(true);
            }
            return fallback.invoke(record);
        } catch (NoSuchMethodException ignored) {
            Field field = record.getClass().getDeclaredField(component.getName());
            if (!field.canAccess(record)) {
                field.setAccessible(true);
            }
            return field.get(record);
        }
    }
}
