package entities.vocabulars;

import java.util.ArrayList;

import entities.Card;

public class Vocabulary implements IVocabulary {

    private String name;
    private String description;
    private ArrayList<Card> vocabContains = new ArrayList<>();

    public Vocabulary(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void addCard(Card card) {
        vocabContains.add(card);
    }

    @Override
    public void deleteCard(Card card) {
        vocabContains.remove(card);
    }


    @Override
    public ArrayList<Card> sentCardsForTrain() {
        return null;
    }

    @Override
    public boolean contains(String word) {
        return false;
    }

    @Override
    public Card getCard(Card card) {
        return vocabContains.get(vocabContains.indexOf(card));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
