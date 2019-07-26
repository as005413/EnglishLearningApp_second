package stringAdditions;

import org.jetbrains.annotations.NotNull;

public class StringValidating {
    public static String validString(@NotNull String str) {
        if (str.isEmpty()) return str;
        str = str.trim();
        str = str.toLowerCase();
        return str;
    }

    public static String firstLetterToUpperCase(@NotNull String str) {
        if (str.isEmpty()) return str;
            String string = str.substring(0, 1);
            return string.toUpperCase() + str.substring(1);

    }
}
