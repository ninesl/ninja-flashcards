package flashcards.dao;

import flashcards.model.Deck;

import java.util.List;
import java.util.Map;

public interface DeckDao {
    Deck createDeck(Deck deck);
    Deck updateDeck(Deck deck);
    void deleteDeck(long deckId);
    Deck getDeckById(Long deckId);
    List<Deck> getAllDecks();
    List<Deck> getDecksByOwnerId(long ownerId);
    List<Deck> getDecksByStatusId(int status); //1-Private 2-Pending 3-Public 4- unlisted??
    //TODO get public decks by genre, or filter in vue

    int getHistoryUserDeck(long userId, long deckId);
    int updateUserDeckHistory(long userId, long deckId, double scorePercent);
    int createUserDeckHistory(long userId, long deckId, double scorePercent);
    int updateUserDeckHistory(long userId, long deckId, double scorePercent, int correctAnswers);
    int createUserDeckHistory(long userId, long deckId, double scorePercent, int correctAnswers);
    
    List<Map<String, Object>> getUserStudyReport(long userId);


}
