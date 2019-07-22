package languages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseLanguage {

    private static List<Language> languagesList;

    //Add new language here, but before you must to add name of your language in ELanguages
    private static void createLanguageList() {
        languagesList = new ArrayList<>();
        languagesList.add(new Language(ELanguages.ENGLISH, Pattern.compile("[a-z]")));
        languagesList.add(new Language(ELanguages.RUSSIAN, Pattern.compile("[а-яё]")));
    }


    public static ELanguages defineLanguage(String wordForDefineLanguage) throws UnvalidatedLanguage {
       createLanguageList();
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
