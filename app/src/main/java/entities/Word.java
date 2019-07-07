package entities;

public class Word {
    private String en;
    private String rus;

    public Word(String en, String rus){
        this.en = en;
        this.rus = rus;
    }

    public String getEn() {
        return en;
    }

    public String getRus() {
        return rus;
    }
}
