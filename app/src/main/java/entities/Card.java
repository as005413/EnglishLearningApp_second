package entities;

public class Card {
    private String word;
    private String translation;
    private String transcription;

    public Card(String word, String translation, String transcription) {
        this.word = word;
        this.translation = translation;
        this.transcription = transcription;
    }

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
}
