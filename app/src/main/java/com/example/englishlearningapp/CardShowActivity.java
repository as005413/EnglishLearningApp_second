package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardShowActivity extends AppCompatActivity {
    private TextView head;
    private TextView word;
    private ImageView imageView;
    private Intent mainActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        showCard();
    }

    public void showCard() {
        mainActivityIntent = getIntent();
        head = findViewById(R.id.textViewHead);
        word = findViewById(R.id.textViewWord);
        //HARDCODE FOR ONE FULL CARD EXAMPLE
        String title = mainActivityIntent.getStringExtra("title");
        String word = mainActivityIntent.getStringExtra("word");


        if (title.equals("Fisherman") || title.equals("Рыбак")) {
            createFishermanCard(title, word);
        } else {
            head.setText(title);
            this.word.setText(word);
        }
    }

    @SuppressLint("SetTextI18n")
    public void createFishermanCard(String title, String word) {

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.fisherman);
        imageView.setBackgroundResource(R.drawable.edit_text_border);
        this.word.setTextSize(24f);
        head.setText(title + "\t[noun]");
        this.word.setText(
                word
                        + '\n'
                        + "Definition: Someone who catches fish as a job or as a hobby."
        );
    }
}
