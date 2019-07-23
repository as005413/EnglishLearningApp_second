package entities.vocabulars;

import java.util.HashMap;

public final class UserVocabulars {
   private HashMap<String, Vocabulary> userVovabulars = new HashMap<>();

    public void createVocabulary(String name, String description) {
        Vocabulary vocabulary;
        if (userVovabulars.containsKey(name))
            vocabulary = userVovabulars.get(name);
        else
            vocabulary = new Vocabulary(name, description);

        userVovabulars.put(name, vocabulary);
    }

    public void deleteVocabulary(String name) {
        //Request password for deleting
        userVovabulars.remove(name);
    }

    public Vocabulary getVocabulary(String name){
        return userVovabulars.get(name);
    }
}
