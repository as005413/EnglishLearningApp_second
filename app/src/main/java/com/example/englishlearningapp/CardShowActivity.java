package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardShowActivity extends AppCompatActivity {
    private TextView head;
    private TextView word;
    private ImageView imageView;
    private ImageButton imgSoundBtn;
    private Intent mainActivityIntent;
    private MediaPlayer mediaPlayer;


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

        imgSoundBtn = findViewById(R.id.imageButton);
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
        imageView.setImageResource(R.drawable.fisherman_in_color);
        //imageView.setBackgroundResource(R.drawable.edit_text_border);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_17211);
        createButton();
        this.word.setTextSize(16f);
        head.setText(title + "\t[noun]");
        this.word.setText(
                word +
                        "Definition:\nSomeone who catches fish as a job or as a hobby.\n\n" +
                        "Examples:\n1. A giant squid shocked the fisherman who caught it in " +
                        "his net off the coast of Vancouver Island.\n\n" +
                        "2. At the time these fisherman were going out sports " +
                        "fishing with hand lines.\n\n" +
                        "3.After more than one hour both the fish and the fishermen were tired."
        );
    }

    public void createButton() {
        imgSoundBtn.setVisibility(View.VISIBLE);
    }

    public void OnClickSound(View view) {
        mediaPlayer.start();
    }
}
