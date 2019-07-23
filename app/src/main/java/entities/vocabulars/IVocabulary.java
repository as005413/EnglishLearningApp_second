package entities.vocabulars;

import java.util.ArrayList;

import entities.Card;

public interface IVocabulary {
    void addCard(Card card); //Add card from vocabulary
    void deleteCard(Card card); //Delete card from vocabulary
    ArrayList<Card> sentCardsForTrain(); //First of all you need to describe logic for choose cards for a train, and then set it.
    boolean contains(String word); //Contain vocabulary card with "word"
    Card getCard(Card card);
}
