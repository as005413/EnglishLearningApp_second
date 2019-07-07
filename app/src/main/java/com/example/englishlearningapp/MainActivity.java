package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import entities.Word;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public ArrayList<Word> createDataBase() {
        Word word1 = new Word("House", "Дом");
        Word word2 = new Word("Person", "Личность");
        Word word3 = new Word("Cat", "Кот");
        Word word4 = new Word("Dog", "Собака");
        Word word5 = new Word("Laptop", "Ноутбук");

        ArrayList<Word> wordsDB = new ArrayList<>(5);
        wordsDB.add(word1);
        wordsDB.add(word2);
        wordsDB.add(word3);
        wordsDB.add(word4);
        wordsDB.add(word5);
        return wordsDB;
    }

    public void onClick(View view) {
        EditText wordObj = (EditText) findViewById(R.id.editText2);
        Switch ru_en = (Switch)findViewById(R.id.switch1);
        TextView translation = (TextView) findViewById(R.id.textView);
        String word = wordObj.getText().toString();
        ArrayList<Word> D_B = createDataBase();

        if(ru_en.isChecked()){ //Now we get english variant
            for(Word word_ : D_B){
                if(word_.getEn().equals(word)){
                    translation.setText(word_.getRus());
                    return;
                }
            }

        }else if(!ru_en.isChecked()){
            for (Word word_ : D_B){
                if(word_.getRus().equals(word)){
                    translation.setText(word_.getEn());
                    return;
                }
            }
        }
        exceptionAlert("Unknown word :(");
    }

    public void exceptionAlert(String alert){ // "alert" will be contain alert message
        Toast.makeText(MainActivity.this,
                alert,
                Toast.LENGTH_SHORT)
                .show();
    }
}
