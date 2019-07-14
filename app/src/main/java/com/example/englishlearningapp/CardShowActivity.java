package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CardShowActivity extends AppCompatActivity {
    TextView head;
    TextView word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        showCard();
    }

    public void showCard(){
        Intent mainActivityIntent = getIntent();
        head = findViewById(R.id.textViewHead);
        word = findViewById(R.id.textViewWord);
        head.setText(mainActivityIntent.getStringExtra("title"));
        word.setText(mainActivityIntent.getStringExtra("word"));
    }
}
