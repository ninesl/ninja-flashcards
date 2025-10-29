package flashcards.dao;

import flashcards.model.Card;

import java.util.List;

public interface CardDao {

    List<Card> getAllCards(long deck_id);

    Card addCard(Card card);

    Card updateCard(Card card);

    boolean deleteCard(long card_id);
}
