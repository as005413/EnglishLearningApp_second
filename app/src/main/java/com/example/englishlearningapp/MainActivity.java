package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
        EditText wordObj = (EditText) findViewById(R.id.editText2);
        Switch ru_en = (Switch) findViewById(R.id.switch1);
        TextView translation = (TextView) findViewById(R.id.textView);

        String word = wordObj.getText().toString();
        ArrayList<Word> D_B = getDataBase();

        if (ru_en.isChecked()) { //Now we get english variant
            for (Word word_ : D_B) {
                if (word_.getEn().equals(word)) {
                    translation.setText(word_.getRus());
                    return;
                }
            }
//TODO При вводе английского слова при русском Switch и наоборот автопереключатель свитча и фикс регистра букв
        } else if (!ru_en.isChecked()) {
            for (Word word_ : D_B) {
                for (String russian : word_.getRusTranslations()) {
                    if (russian.equals(word)) {
                        translation.setText(word_.getEn());
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
}
