package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import entities.Word;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public HashMap<String, Word> createDataBase() {
        HashMap<String, Word> wordsDB = new HashMap<>();

        Word word1 = new Word("House", "Dom");
        wordsDB.put(word1.getWord(), word1);

        Word word2 = new Word("Person", "Личность");
        wordsDB.put(word2.getWord(), word2);

        Word word3 = new Word("Cat", "Кот");
        wordsDB.put(word3.getTranslation(), word3);

        Word word4 = new Word("Dog", "Собака");
        wordsDB.put(word4.getTranslation(), word4);

        Word word5 = new Word("Laptop", "Ноутбук");
        wordsDB.put(word5.getTranslation(), word5);
        return wordsDB;
    }

    public void onClick(View view) {
        EditText englishWordObj = (EditText)findViewById(R.id.editText2);
        String englishWord = englishWordObj.getText().toString();

        Word word = createDataBase().get(englishWord);

        TextView translation = (TextView)findViewById(R.id.textView);
        translation.setText(word.getTranslation());
    }
}
