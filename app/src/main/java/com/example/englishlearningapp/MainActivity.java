package com.example.englishlearningapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import designSolutions.CustomAdapter;
import entities.Card;
import entities.Word;
import entities.Database;
import languages.ChooseLanguage;
import languages.ELanguages;

import languages.UnvalidatedLanguage;
import stringAdditions.StringValidating;

public class MainActivity extends AppCompatActivity {

    private Database db = Database.getInstance();
    private ArrayList<Word> D_B;
    private ListView listView;
    private EditText wordObj;
    private Intent cardShowIntent;
    private ArrayList<Card> cards;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardShowIntent = getIntent();

        if (savedInstanceState != null) {
            cards = savedInstanceState.getParcelableArrayList("cards");
            listView = createListView();
            onListViewClick();
            db = savedInstanceState.getParcelable("database");
        }

        wordObj = findViewById(R.id.editText2);
        if (db.getData_base().isEmpty()) //This command fixed bug with stacking database
            db.createDB(this);
        D_B = db.getData_base();

        wordChangedListener();
        if (cardShowIntent != null) {
            wordObj.setText(cardShowIntent.getStringExtra("word"));
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cards", cards);
    }

    private void wordChangedListener(){
        wordObj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String word = "";
                search(word);
            }
        });
    }

    private void search(String word) {
        if (wordObj.length() > 0) {
            word = wordObj.getText().toString();

            word = StringValidating.validString(word);

            cards = new ArrayList<>();
            listView = createListView();


            ELanguages language = null;
            try {
                language = ChooseLanguage.defineLanguage(word);
            } catch (UnvalidatedLanguage unvalidatedLanguage) {
                unvalidatedLanguage.printStackTrace();
            }

            cards = Database.searching(D_B, word, language);

            if (cards.isEmpty()) exceptionAlert("Unknown word :(");
            else listView = createListView();
            onListViewClick();
        }
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

}

