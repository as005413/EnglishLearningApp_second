package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import designSolutions.CustomEditText;
import designSolutions.DrawableClickListener;

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
    private Toolbar toolbar;
    private CustomEditText search;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getIds();
        hideKeyBordInSearch();
        editTextChangeListener();
        drawableClickListener();
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
        toolbar = findViewById(R.id.my_toolbar);
        search = findViewById(R.id.editTextSearch);
    }

    private void hideKeyBordInSearch() {
        search.setFocusableInTouchMode(false);
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                search.setFocusableInTouchMode(true);
                search.requestFocus();
                return false;
            }


        });

    }

    private void drawableClickListener() {
        search.setCursorVisible(true);
        search.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        search.setText("");
                        break;
                    case LEFT:
                        if (search.isCursorVisible())
                            search.setCursorVisible(false);
                        else search.setCursorVisible(true);
                        String searchingRequest = search.getText().toString();
                        searchWord(searchingRequest);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void searchWord(String searchingReuest) {
        //Logic for searching in DB
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

        this.word.setText(mainActivityIntent.getStringExtra("transcription"));
        String def = "a \0person \0who \0catches \0fish as a job or as a \0hobby ";
        definition.setMovementMethod(LinkMovementMethod.getInstance());
        definition.setText(addClikablePart(def), TextView.BufferType.SPANNABLE);

        definition.setVisibility(View.VISIBLE);
        String exampls = "Examples:<br>A giant squid shocked the <b><u>fisherman</u></b> who caught it in " +
                "his net off the coast of Vancouver Island." +
                "<br>At the time these <b><u>fisherman</u></b> were going out sports " +
                "fishing with hand lines.<br>" +
                "After more than one hour the fish and the <b><u>fisherman</u></b> was tired.";

        examples.setText(Html.fromHtml(exampls));
        examples.setVisibility(View.VISIBLE);
        translateBtn.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }

    private SpannableStringBuilder addClikablePart(final String def) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(def);
        int idx1 = def.indexOf("\0");
        int idx2;
        while (idx1 != -1) {
            idx2 = def.indexOf(" ", idx1) + 1;
            final String clickString = def.substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String popupTran;
                    switch (clickString) {
                        case "\0person ":
                            popupTran = "Человек";
                            break;
                        case "\0who ":
                            popupTran = "Кто";
                            break;
                        case "\0catches ":
                            popupTran = "Ловит";
                            break;
                        case "\0fish ":
                            popupTran = "Рыба";
                            break;
                        case "\0hobby ":
                            popupTran = "Хобби";
                            break;
                        default:
                            popupTran = "Something wrong!";
                    }
                    Toast.makeText(getApplicationContext(), popupTran, Toast.LENGTH_LONG).show();
                }

                @Override
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setColor(getResources().getColor(R.color.smoke_grey));
                }
            }, idx1, idx2, 0);
            idx1 = def.indexOf("\0", idx2);
        }
        return ssb;
    }

    public void createButton() {
        imgSoundBtn.setVisibility(View.VISIBLE);
    }

    public void OnClickSound(View view) {
        mediaPlayer.start();
    }

    @SuppressLint("SetTextI18n")
    public void onClickShowTranslation(View view) {
        String btnText = translateBtn.getText().toString();
        if (btnText.equals("Show translation"))
            translateBtn.setText(mainActivityIntent.getStringExtra("translation"));
        else translateBtn.setText("Show translation");
    }

    public void editTextChangeListener() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 1) {
                    search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_search_white_18dp, 0, R.drawable.baseline_close_white_18dp, 0);
                } else {
                    search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_search_white_18dp, 0, 0, 0);
                }
            }

        });
    }
}
