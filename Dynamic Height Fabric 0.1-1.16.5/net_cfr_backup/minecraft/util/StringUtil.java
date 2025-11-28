package net.minecraft.util;

import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class StringUtil {
	private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

	@Environment(EnvType.CLIENT)
	public static String formatTickDuration(int i) {
		int j = i / 20;
		int k = j / 60;
		j %= 60;
		return j < 10 ? k + ":0" + j : k + ":" + j;
	}

	@Environment(EnvType.CLIENT)
	public static String stripColor(String string) {
		return STRIP_COLOR_PATTERN.matcher(string).replaceAll("");
	}

	public static boolean isNullOrEmpty(@Nullable String string) {
		return StringUtils.isEmpty(string);
	}
}
