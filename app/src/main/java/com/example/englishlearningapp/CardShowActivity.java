package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardShowActivity extends AppCompatActivity {
    private TextView head;
    private TextView word;
    private ImageView imageView;
    private ImageButton imgSoundBtn;
    private Intent mainActivityIntent;
    private MediaPlayer mediaPlayer;
    private TextView definition;
    private TextView examples;
    private Button translateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);
        getIds();
        getSupportActionBar().hide();
        showCard();
    }

    private void getIds() {
        head = findViewById(R.id.textViewHead);
        word = findViewById(R.id.textViewWord);
        imgSoundBtn = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        definition = findViewById(R.id.textViewDefinition);
        examples = findViewById(R.id.textViewExamples);
        translateBtn = findViewById(R.id.buttonTranslate);
    }

    public void showCard() {
        mainActivityIntent = getIntent();
        //HARDCODE FOR ONE FULL CARD EXAMPLE
        String title = mainActivityIntent.getStringExtra("title");
        String word = mainActivityIntent.getStringExtra("word");


        if (title.equals("Fisherman") || title.equals("Рыбак")) {
            createFishermanCard(title, word);
        } else {
            head.setText(title);
            this.word.setText(word);
            this.word.setTextSize(32f);
        }
    }

    @SuppressLint("SetTextI18n")
    public void createFishermanCard(String title, String word) {
        imageView.setImageResource(R.drawable.fisherman_in_color);

        mediaPlayer = MediaPlayer.create(this, R.raw.pronunciation_en_fisherman);
        createButton();

        head.setText(title);

        this.word.setTextSize(24f);
        this.word.setText(mainActivityIntent.getStringExtra("transcription"));

        definition.setTextSize(24f);
        String def = "a [person] [who] [catches] [fish] as a job or as a [hobby].";
        definition.setMovementMethod(LinkMovementMethod.getInstance());
        definition.setText(addClikablePart(def),TextView.BufferType.SPANNABLE);

        //definition.setText("a person who catches fish as a job or as a hobby.");
        definition.setVisibility(View.VISIBLE);
        String exampls = "Examples:<br>A giant squid shocked the <b><u>fisherman</u></b> who caught it in " +
                "his net off the coast of Vancouver Island." +
                "<br>At the time these <b><u>fisherman</u></b> were going out sports " +
                "fishing with hand lines.<br>" +
                "After more than one hour the fish and the <b><u>fisherman</u></b> was tired.";
//        examples.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(view);
//            }
//        }
        //);
        examples.setText(Html.fromHtml(exampls));
        examples.setTextSize(18f);
        examples.setVisibility(View.VISIBLE);
        translateBtn.setVisibility(View.VISIBLE);
    }

    private SpannableStringBuilder addClikablePart(final String def) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(def);
        int idx1 = def.indexOf("[");
        int idx2;
        while (idx1 != -1) {
            idx2 = def.indexOf("]", idx1) + 1;
            final String clickString = def.substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String popupTran;
                    switch (clickString){
                        case "[person]" :
                                popupTran = "человек";
                                break;
                        case "[who]":
                            popupTran = "кто";
                            break;
                        case "[catches]":
                            popupTran = "ловит";
                            break;
                        case "[fish]":
                            popupTran = "рыба";
                            break;
                        case "[hobby]":
                            popupTran = "хобби";
                            break;
                            default:
                                popupTran = "something wrong!";
                    }
                   showPopupMenu(widget ,popupTran);
                }

                @Override
                public void updateDrawState(TextPaint textPaint){
                    textPaint.setColor(getResources().getColor(R.color.grey_line));
                }
            }, idx1, idx2, 0);
            idx1 = def.indexOf("[", idx2);
        }
        return ssb;
    }

    public void createButton() {
        imgSoundBtn.setVisibility(View.VISIBLE);
    }

    public void OnClickSound(View view) {
        mediaPlayer.start();
    }

    private void showPopupMenu(View view, String str) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.getMenu().add(1, R.id.menugroup1, 1, str);
        popupMenu.show();
    }

    @SuppressLint("SetTextI18n")
    public void onClickShowTranslation(View view) {
        String btnText = translateBtn.getText().toString();

        if(btnText.equals("Show translation"))
        translateBtn.setText(mainActivityIntent.getStringExtra("translation"));
        else translateBtn.setText("Show translation");
    }
}
