package com.example.englishlearningapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Word;
import entities.Data_base;

public class MainActivity extends AppCompatActivity {

    private Data_base db = Data_base.getInstance();
    private ArrayList<String> words;
    private ArrayList<Word> wordsObj_s;
    private ListView listView;

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

        String word = wordObj.getText().toString();
        word = validString(word);
        ArrayList<Word> D_B = getDataBase();
        words = new ArrayList<>();
        wordsObj_s = new ArrayList<>();
        listView = createListView(new ArrayList<String>());

        if (isEn(word)) {
            for (Word word_ : D_B)
                if (word_.getEn().contains(word)) {
                    words.add(word_.getEn());
                    wordsObj_s.add(word_);
                }
        } else if (isRus(word)) {
            for (Word word_ : D_B)
                for (String russian : word_.getRusTranslations())
                    if (word_.getRus().contains(word)) {
                        if (!wordsObj_s.contains(word_)) {
                            words.add(word_.getRus());
                            wordsObj_s.add(word_);
                        }
                        break;
                    }
        }
        if (wordsObj_s.isEmpty()) exceptionAlert("Unknown word :(");
        else listView = createListView(words);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String title = wordsObj_s.get(position).getEn();
                title = (char) (title.charAt(0) - 32) + title.substring(1, title.length());

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

    public boolean isEn(String word) {
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(word);
        return m.find();
    }

    public boolean isRus(String word) {
        Pattern p = Pattern.compile("[а-я]");
        Matcher m = p.matcher(word);
        return m.find();

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

