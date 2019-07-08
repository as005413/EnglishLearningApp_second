package entities;

public class Word {
    private String en;
    private String rus;
    private String transcription;

    public Word(String en, String rus, String transcription) {
        this.en = en;
        this.rus = rus;
        this.transcription = transcription;
    }

    public Word() {
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
