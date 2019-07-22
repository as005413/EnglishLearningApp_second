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
import stringAdditions.StringValidating;

import entities.Database;

public class MainActivity extends AppCompatActivity {

    private Database db = Database.getInstance();
    private ListView listView;
    private EditText wordObj;
    private ArrayList<Card> cards;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (savedInstanceState != null) {
            cards = savedInstanceState.getParcelableArrayList("cards");
            listView = createListView();
            onListViewClick();
            db = savedInstanceState.getParcelable("database");
        }
        wordObj = findViewById(R.id.editText2);
        if (db.getData_base().isEmpty()) //This command fixed bug with stacking database
            db.createDB(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cards", cards);
        outState.putParcelable("database",db);
    }


    public ArrayList<Word> getDataBase() {
        return db.getData_base();
    }
//TODO Отловить NPE
    public void onClick(View view) {

        String word = wordObj.getText().toString();

        word = StringValidating.validString(word);

        ArrayList<Word> D_B = getDataBase();
        cards = new ArrayList<>();
        listView = createListView();


        ELanguages language = null;
        try {
            language = ChooseLanguage.defineLanguage(word);
        } catch (UnvalidatedLanguage unvalidatedLanguage) {
            unvalidatedLanguage.printStackTrace();
        }

        cards = Database.searching(D_B,word, language);

        if (cards.isEmpty()) exceptionAlert("Unknown word :(");
        else listView = createListView();
        onListViewClick();
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
                intent.putExtra("database",db);
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

}

