package entities;

public interface IDatabase {
    String getRusWord(String where, String rus);
    String getEnWord(String where, String en);
    String getTranscription(String where);
}
