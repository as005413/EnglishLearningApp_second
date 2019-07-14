package languages;

import java.util.regex.Pattern;

public class Language {
    private ELanguages language;
    private Pattern regexForThisLanguage;

    public Language(ELanguages language, Pattern regexForThisLanguage) {
        this.language = language;
        this.regexForThisLanguage = regexForThisLanguage;
    }

    public ELanguages getLanguage() {
        return language;
    }

    public Pattern getRegexForThisLanguage() {
        return regexForThisLanguage;
    }
}
