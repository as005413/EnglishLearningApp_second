package entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
    private String word;
    private String translation;
    private String transcription;

    public Card(String word, String translation, String transcription) {
        this.word = word;
        this.translation = translation;
        this.transcription = transcription;
    }

    protected Card(Parcel in) {
        word = in.readString();
        translation = in.readString();
        transcription = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        return "Translation: " + translation
                + "\n\n" + "Transcription: " + transcription
                + "\n\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
        parcel.writeString(translation);
        parcel.writeString(transcription);
    }
}
