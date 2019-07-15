package entities;

import android.content.Context;
import android.content.res.AssetManager;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Database {
    private static final Database ourInstance = new Database();
    private ArrayList<Word> data_base = new ArrayList<>();


    public static Database getInstance() {
        return ourInstance;
    }

    public ArrayList<Word> getData_base() {
        return data_base;
    }

    private Database() {
    }

    public void createDB(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream fstream = manager.open("data_base.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fstream));
            String oneWord;

            while ((oneWord = bufferedReader.readLine()) != null) {
                String
                        en = "",
                        rus = "",
                        transcription;
                ArrayList<String> rusTranslations;

                transcription = createTranscription(oneWord);
                en = createEnWord(oneWord, en);
                rus = createRusWord(oneWord,rus);
                rusTranslations = createSomeRussianTranslations(rus);


                Word word = new Word(en, transcription, rusTranslations);
                data_base.add(word);
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound_-_");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOE_-_");
            e.printStackTrace();
        }
    }

    private String createRusWord(@NotNull String where, String rus) {
        for (int i = 0; i < where.length(); i++) {
            if ((int) where.charAt(i) >= 'а' && where.charAt(i) <= 'я' ||
                    where.charAt(i) == ',') {
                rus += where.charAt(i);
            } else if (where.charAt(i) == ' ' && where.charAt(i + 1) >= 'а' && where.charAt(i + 1) <= 'я' &&
                    where.charAt(i - 1) >= 'а' && where.charAt(i - 1) <= 'я') rus += ' ';

            else if (where.charAt(i) == ' ' && where.charAt(i + 1) == '(') rus += ' ';
        }
        return rus;
    }

    private String createEnWord(@NotNull String where, String en) {
        int numberOfStart = 0;
        for (int i = 0; i < where.length(); i++) {
            if ((int) where.charAt(i) >= 97 &&
                    (int) where.charAt(i) <= 122) {
                numberOfStart = i;
                break;
            }
        }

        for (int i = numberOfStart;
             where.charAt(i) >= 97 &&
                     where.charAt(i) <= 122 ||
                     where.charAt(i) == ' ';
             i++) {

            if (where.charAt(i) == ' ' &&
                    (where.charAt(i + 1) < 97 || where.charAt(i + 1) > 122))
                break;
            en += where.charAt(i);
        }
        return en;
    }

    private ArrayList<String> createSomeRussianTranslations(String rus) {
        ArrayList<String> rusTranslations = new ArrayList<>();
        rus += ',';
        StringBuffer rus_ = new StringBuffer(rus);

        if (rus_.indexOf("(") + rus_.indexOf(")") > 0) {
            rus_.replace(rus_.indexOf("("), rus_.indexOf(")") + 1, "");
        }

        while (!rus_.toString().equals("")) {
            rusTranslations.add(rus_.substring(0, rus_.indexOf(",")));
            rus_.replace(0, rus_.indexOf(",") + 1, "");
        }
        return rusTranslations;
    }

    @NotNull
    private String createTranscription(@NotNull String where) {
        return where.substring(where.indexOf('['), where.indexOf(']') + 1);
    }


}