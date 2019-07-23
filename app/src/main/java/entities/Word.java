package entities;

import android.os.Parcel;


import java.util.List;

public class Word  {
    private String en;
    private String transcription;
    private List<String> rusTranslations;

    public Word(String en, String transcription, List<String> rusTranslations) {
        this.en = en;
        this.transcription = transcription;
        this.rusTranslations = rusTranslations;
    }

    public Word() {
    }

    protected Word(Parcel in) {
        en = in.readString();
        transcription = in.readString();
        rusTranslations = in.createStringArrayList();
    }

    public List<String> getRusTranslations() {
        return rusTranslations;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getEn() {
        return en;
    }

    @Override
    public String toString() {
        String R_T = "";
        for (String rus : rusTranslations) R_T += rus + ", ";
        R_T = R_T.substring(0, R_T.length() - 2);

        return
                "English variant: " + en + "\n\n" +
                        "Transcription: " + transcription + "\n\n" +
                        "Russian translations: " + R_T + "\n";
    }


}
