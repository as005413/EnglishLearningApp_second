package com.example.englishlearningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;

import designSolutions.CustomEditText;
import designSolutions.DrawableClickListener;
import entities.Card;
import entities.vocabulars.UserVocabulars;
import stringAdditions.StringValidating;


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
    private Card currentCard;
    private UserVocabulars userVocabulars;
    private ImageButton imgBook;
    private ImageButton menu;
    private LinearLayout linearLayout;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_show);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getIds();

        search.setOnClickListener(ETonClickListener);
        search.setOnEditorActionListener(onEditorActionListener);
        hideKeyBordInSearch();
        editTextChangeListener();
        drawableClickListener();
        showCard();
//STUB
        imgBook.setOnClickListener(imgBookClickListener);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//STUB END

        setLinearLayoutClickListener();

    }

    private ImageButton.OnClickListener imgBookClickListener = new ImageButton.OnClickListener(){
        @Override
        public void onClick(View view) {
            userVocabulars = new UserVocabulars();
            userVocabulars.createVocabulary("System test","Some description");
            userVocabulars.getVocabulary("System test").addCard(currentCard);
        }
    };

    private ImageButton.OnClickListener menuClickListener = new ImageButton.OnClickListener(){
        @Override
        public void onClick(View view) {
            Toast.makeText(CardShowActivity.this,
                    userVocabulars.getVocabulary("System test").getCard(currentCard).toString()
                    ,Toast.LENGTH_LONG).show();
        }
    };

    private EditText.OnClickListener ETonClickListener = new View.OnClickListener (){
        @Override
        public void onClick(View view) {
            Drawable drawable = getResources().getDrawable(R.drawable.search_view);
            search.setBackground(drawable);
            search.setCursorVisible(true);
            search.setHint(R.string.search);
        }
    };

    private EditText.OnEditorActionListener onEditorActionListener = new EditText.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
           switch (i){
               case EditorInfo
                       .IME_ACTION_SEARCH:
                   String searchingRequest = search.getText().toString();
                   search.setHint(R.string.search);
                   if (!searchingRequest.isEmpty()) {
                       Intent intent = new Intent(CardShowActivity.this, MainActivity.class);
                       intent.putExtra("word", searchingRequest);
                       startActivity(intent);
                   }
                   break;
           }
            return false;
        }
    };

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
        mainActivityIntent = getIntent();
        menu = findViewById(R.id.showMenu);
        imgBook = findViewById(R.id.addToVacab);
        currentCard = mainActivityIntent.getParcelableExtra("card");
        linearLayout = findViewById(R.id.linearLayout);
    }

    private void setLinearLayoutClickListener(){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setFocusable(false);
                search.setHint("");
                if (search.getText() != null) search.setText("");
                search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_search_white_18dp,
                        0, 0, 0);
                search.setBackground(getResources().getDrawable(R.color.scroll_activity_card_show_back));
            }
        });
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

        search.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        search.setText(null);
                        search.setHint("Search");
                        break;
                    case LEFT:
                        Drawable drawable = getResources().getDrawable(R.drawable.search_view);
                        search.setBackground(drawable);
                        search.setCompoundDrawablesWithIntrinsicBounds(0,
                                0, R.drawable.baseline_close_black_18dp, 0);
                        String searchingRequest = search.getText().toString();
                        search.setHint(R.string.search);
                        if (!searchingRequest.isEmpty()) {
                            Intent intent = new Intent(CardShowActivity.this, MainActivity.class);
                            intent.putExtra("word", searchingRequest);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void showCard() {

        //HARDCODE FOR ONE FULL CARD EXAMPLE
        String title = currentCard.getWord();
        String word = currentCard.getTranscription();

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

        this.word.setText(word);
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
            translateBtn.setText(StringValidating.firstLetterToUpperCase(currentCard.getTranslation()));
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
                    search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search_view_icon,
                            0, R.drawable.baseline_close_black_18dp, 0);
                } else {
                    search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search_view_icon,
                            0, 0, 0);
                }
            }
        });
    }
}
