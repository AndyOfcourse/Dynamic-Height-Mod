package net.multiverse.dynamicheight.util;

import net.minecraft.world.level.Level;

import java.lang.reflect.Field;

public final class BuildHeightUtil {
    private BuildHeightUtil() {
    }

    public static void updateCachedHeights(Level level, int minY, int maxY, int sectionSize) {
        int height = maxY - minY + 1;
        int sections = Math.max(1, height / sectionSize);
        updateField(level, level.getMinBuildHeight(), minY);
        updateField(level, level.getMaxBuildHeight(), maxY + 1);
        updateField(level, level.getHeight(), height);
        updateField(level, level.getSectionsCount(), sections);
    }

    private static void updateField(Level level, int currentValue, int newValue) {
        Class<?> cls = level.getClass();
        while (cls != null && cls != Object.class) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.getType() != int.class) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    if (field.getInt(level) == currentValue) {
                        field.setInt(level, newValue);
                        return;
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
            cls = cls.getSuperclass();
        }
    }
}
