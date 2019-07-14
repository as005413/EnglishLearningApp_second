package languages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ChooseLanguage {

    private List<Language> languagesList;

    public ChooseLanguage() {
    }

    public void createLanguageList(Language... args) {
        languagesList = new ArrayList<>();
        for (Language language : args)
            languagesList.add(language);
    }

    public ELanguages defineLanguage(String wordForDefineLanguage) throws UnvalidatedLanguage {
        Matcher matcher;
        for (Language language : languagesList) {
            matcher = language
                    .getRegexForThisLanguage()
                    .matcher(wordForDefineLanguage);
            if (matcher.find()) return language.getLanguage();
        }
        throw new UnvalidatedLanguage("This language does not exit");
    }
}
