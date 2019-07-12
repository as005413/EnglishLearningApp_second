package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Word;
import entities.Data_base;

public class MainActivity extends AppCompatActivity {

    Data_base db = Data_base.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db.createDB(this);
    }

    public ArrayList<Word> getDataBase() {
        return db.getData_base();
    }

    public void onClick(View view) {
        EditText wordObj = findViewById(R.id.editText2);
        TextView translation = findViewById(R.id.textView);
        TextView transcription = findViewById(R.id.textView2);

        transcription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        String word = wordObj.getText().toString();
        word = word.toLowerCase();
        word = word.trim();
        ArrayList<Word> D_B = getDataBase();

        if (isEn(word)) { //Now we get english variant
            for (Word word_ : D_B) {
                if (word_.getEn().equals(word)) {
                    translation.setTextSize(18f);
                    translation.setText(word_.getRus());
                    transcription.setText(word_.getTranscription());
                    return;
                }
            }
        } else if (isRus(word)) {
            for (Word word_ : D_B) {
                for (String russian : word_.getRusTranslations()) {
                    if (russian.equals(word)) {
                        translation.setTextSize(18f);
                        translation.setText(word_.getEn());
                        transcription.setText(word_.getTranscription());
                        return;
                    }
                }
            }
        }
        exceptionAlert("Unknown word :(");
    }

    public void exceptionAlert(String alert) { // "alert" will be contain alert message
        Toast.makeText(MainActivity.this,
                alert,
                Toast.LENGTH_SHORT)
                .show();
    }

    public boolean isEn(String word){
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(word);
        return m.find();
    }

    public boolean isRus(String word){
        Pattern p = Pattern.compile("[а-я]");
        Matcher m = p.matcher(word);
        return m.find();

    }
}

