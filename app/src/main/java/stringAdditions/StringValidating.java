package stringAdditions;

import org.jetbrains.annotations.NotNull;

public class StringValidating {
    public static String validString(@NotNull String str) {
        if (str == null || str.isEmpty()) return str;
        str = str.trim();
        str = str.toLowerCase();
        return str;
    }

    public static String firstLetterToUpperCase(@NotNull String str) {
        if (str == null || str.isEmpty()) return str;
        return (char) (str.charAt(0) - 32) + str.substring(1);
    }
}
