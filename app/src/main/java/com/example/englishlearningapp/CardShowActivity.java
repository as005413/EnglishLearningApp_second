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

        Intent mainActiviryIntent = getIntent();
        head = findViewById(R.id.textViewHead);
        word = findViewById(R.id.textViewWord);
        head.setText(mainActiviryIntent.getStringExtra("title"));
        word.setText(mainActiviryIntent.getStringExtra("word"));
    }
}
