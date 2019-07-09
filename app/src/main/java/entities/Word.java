package entities;

import java.util.ArrayList;

public class Word {
    private String en;
    private String rus;
    private String transcription;
    private ArrayList<String> rusTranslations;

    public Word(String en, String rus, String transcription, ArrayList<String> rusTranslations) {
        this.en = en;
        this.rus = rus;
        this.transcription = transcription;
        this.rusTranslations = rusTranslations;
    }

    public Word() {
    }

    public ArrayList<String> getRusTranslations() {
        return rusTranslations;
    }

    public void addToRusTranslations(String rusWord){
        rusTranslations.add(rusWord);
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }
}
