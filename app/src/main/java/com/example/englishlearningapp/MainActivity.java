package com.example.englishlearningapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import entities.Word;
import entities.Database;
import languages.ChooseLanguage;
import languages.ELanguages;
import languages.Language;
import languages.UnvalidatedLanguage;

public class MainActivity extends AppCompatActivity {

    private Database db = Database.getInstance();
    private ArrayList<String> words;
    private ArrayList<Word> wordsObj_s;
    private ListView listView;
    private EditText wordObj;
    private ChooseLanguage chooseLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseLanguage = new ChooseLanguage();
        chooseLanguage.createLanguageList(
                new Language(ELanguages.ENGLISH, Pattern.compile("[a-z]")),
                new Language(ELanguages.RUSSIAN, Pattern.compile("[а-яё]"))
        );

        wordObj = findViewById(R.id.editText2);
        if (db.getData_base().isEmpty()) //This command fixed bug with stacking database
            db.createDB(this);
    }

    public ArrayList<Word> getDataBase() {
        return db.getData_base();
    }

    public void onClick(View view) {
        String word = wordObj.getText().toString();
        word = validString(word);
        ArrayList<Word> D_B = getDataBase();
        words = new ArrayList<>();
        wordsObj_s = new ArrayList<>();
        listView = createListView(new ArrayList<String>());

        ELanguages language = null;
        try {
            language = chooseLanguage.defineLanguage(word);
        } catch (UnvalidatedLanguage unvalidatedLanguage) {
            unvalidatedLanguage.printStackTrace();
        }

        switch (language) {
            case ENGLISH:
                for (Word word_ : D_B)
                    if (word_.getEn().startsWith(word)) {
                        words.add(word_.getEn());
                        wordsObj_s.add(word_);
                    }
                break;
            case RUSSIAN:
                for (Word word_ : D_B)
                    for (String russian : word_.getRusTranslations())
                        if (russian.startsWith(word)) {
                            words.add(russian);
                            wordsObj_s.add(word_);
                        }
                break;
        }

        if (wordsObj_s.isEmpty()) exceptionAlert("Unknown word :(");
        else listView = createListView(words);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String title = wordsObj_s.get(position).getEn();
                title = (char) (title.charAt(0) - 32) + title.substring(1);

                builder.setTitle(title)
                        .setMessage(wordsObj_s.get(position).toString())
                        .setCancelable(false)
                        .setNegativeButton("Clear!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void exceptionAlert(String alert) { // "alert" will be contain alert message
        Toast.makeText(MainActivity.this,
                alert,
                Toast.LENGTH_SHORT)
                .show();
    }

    public ListView createListView(ArrayList<String> words) {
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, words);
        listView.setAdapter(adapter);
        return listView;
    }

    private String validString(String str) {
        str = str.trim();
        str = str.toLowerCase();
        return str;
    }
}

