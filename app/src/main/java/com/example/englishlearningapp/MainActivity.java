package com.example.englishlearningapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

import designSolutions.CustomAdapter;
import entities.Card;
import entities.Word;
import entities.Database;
import languages.ChooseLanguage;
import languages.ELanguages;
import languages.Language;
import languages.UnvalidatedLanguage;

public class MainActivity extends AppCompatActivity {

    private Database db = Database.getInstance();
    private ListView listView;
    private EditText wordObj;
    private ChooseLanguage chooseLanguage;
    private ArrayList<Card> cards;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseLanguage = new ChooseLanguage();
        chooseLanguage.createLanguageList(
                new Language(ELanguages.ENGLISH, Pattern.compile("[a-z]")),
                new Language(ELanguages.RUSSIAN, Pattern.compile("[а-яё]"))
        );

        if (savedInstanceState != null) {
            cards = savedInstanceState.getParcelableArrayList("cards");
            listView = createListView();
            onListViewClick();
        }
        wordObj = findViewById(R.id.editText2);
        if (db.getData_base().isEmpty()) //This command fixed bug with stacking database
            db.createDB(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cards", cards);
    }


    public ArrayList<Word> getDataBase() {
        return db.getData_base();
    }
//TODO Отловить NPE
    public void onClick(View view) {

        String word = wordObj.getText().toString();

        word = validString(word);

        ArrayList<Word> D_B = getDataBase();
        cards = new ArrayList<>();
        listView = createListView();

        ELanguages language = defineLanguage(word);

        switch (language) {
            case ENGLISH: {
                for (Word word_ : D_B)
                    for (String russian : word_.getRusTranslations())
                        if (word_.getEn().equals(word))
                            cards.add(new Card(
                                    firstLetterToUpperCase(word_.getEn()),
                                    firstLetterToUpperCase(russian),
                                    word_.getTranscription()));
                break;
            }
            case RUSSIAN: {
                for (Word word_ : D_B)
                    for (String russian : word_.getRusTranslations())
                        if (russian.equals(word))
                            cards.add(new Card(
                                    firstLetterToUpperCase(russian),
                                    firstLetterToUpperCase(word_.getEn()),
                                    word_.getTranscription()));
                break;
            }
        }


        if (cards.isEmpty()) exceptionAlert("Unknown word :(");
        else listView = createListView();
        onListViewClick();
    }

    private ELanguages defineLanguage(String word) {
        ELanguages language = null;
        try {
            language = chooseLanguage.defineLanguage(word);
        } catch (UnvalidatedLanguage unvalidatedLanguage) {
            unvalidatedLanguage.printStackTrace();
        }
        return language;
    }

    private void onListViewClick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String title = cards.get(position).getWord();
                String translation = cards.get(position).getTranslation();
                String transcription = cards.get(position).getTranscription();
                Intent intent = new Intent(MainActivity.this, CardShowActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("word", cards.get(position).toString());
                intent.putExtra("translation", translation);
                intent.putExtra("transcription", transcription);
                startActivity(intent);
            }
        });
    }

    public void exceptionAlert(String alert) { // "alert" will be contain alert message
        Toast.makeText(MainActivity.this,
                alert,
                Toast.LENGTH_SHORT)
                .show();
    }

    private ListView createListView() {
        ListView listView = findViewById(R.id.listView);
        adapter = new CustomAdapter(cards, getApplicationContext());
        listView.setAdapter(adapter);
        return listView;
    }

    private String validString(@NotNull String str) {
        if (str == null || str.isEmpty()) return str;
        str = str.trim();
        str = str.toLowerCase();
        return str;
    }

    private String firstLetterToUpperCase(@NotNull String str) {
        if (str == null || str.isEmpty()) return str;
        return (char) (str.charAt(0) - 32) + str.substring(1);
    }
}

