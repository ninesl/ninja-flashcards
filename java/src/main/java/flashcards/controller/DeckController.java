package flashcards.controller;

import flashcards.dao.DeckDao;
import flashcards.dao.JdbcDeckDao;
import flashcards.dao.UserDao;
import flashcards.model.Deck;
import flashcards.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class DeckController {

    private DeckDao deckDao;
    private UserDao userDao;

    //CONSTRUCTOR
    public DeckController(DeckDao deckDao, UserDao userDao){
        this.deckDao = deckDao;
        this.userDao = userDao;
    }

    @PreAuthorize("@deckSecurity.canCreate(authentication)")
    @RequestMapping(path="/deck", method = RequestMethod.POST)
    public Deck createDeck(@RequestBody Deck deck){
        return deckDao.createDeck(deck);
    }

    @PreAuthorize("@deckSecurity.canRead(#id, authentication)")
    @RequestMapping(path="/deck/{id}", method = RequestMethod.GET)
    public Deck getDeckByDeckId(@PathVariable long id) { return deckDao.getDeckById(id); }

    @PreAuthorize("@deckSecurity.canAccessUserDecks(#userId, authentication)")
    @RequestMapping(path = "/deck/myDecks/{userId}", method = RequestMethod.GET)
    public List<Deck> getDeckByUserId(@PathVariable long userId) {
        return deckDao.getDecksByOwnerId(userId);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(path = "/deck", method = RequestMethod.GET)
    public List<Deck> getAllDecks(){
        //Maybe never have access to all decks?
        List<Deck> decks = deckDao.getAllDecks();
        return decks;
    }

    @PreAuthorize("@deckSecurity.canModify(#id, authentication)")
    @ResponseStatus(value= HttpStatus.OK)
    @RequestMapping(path = "/deck/{id}", method = RequestMethod.PUT)
    public Deck updateDeck(@PathVariable long id, @RequestBody Deck deck) {
        return deckDao.updateDeck(deck);
    }

    @PreAuthorize("@deckSecurity.canDelete(#id, authentication)")
    @ResponseStatus(value= HttpStatus.OK)
    @RequestMapping(path = "/deck/{id}", method = RequestMethod.DELETE)
    public void deleteDeck(@PathVariable Long id) {
        deckDao.deleteDeck(id);
    }
    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/deck/public", method = RequestMethod.GET)
    public List<Deck> getPublicDecks() {
        return deckDao.getDecksByStatusId(JdbcDeckDao.PUBLIC);
    }





    //Score History Endpoints

    @PreAuthorize("@deckSecurity.canRead(#deckId, authentication)")
    //@ResponseStatus(value= HttpStatus.OK)
    @RequestMapping(path="/deck/{deckId}/history/{userId}", method = RequestMethod.GET)
    public int getHistoryUserDeck(@PathVariable long userId, @PathVariable long deckId) {
        return deckDao.getHistoryUserDeck(userId, deckId);
    }

    @PreAuthorize("@deckSecurity.canRead(#deckId, authentication)")
    //@ResponseStatus(value= HttpStatus.OK)
    @RequestMapping(path = "/deck/{deckId}/history/{userId}", method = RequestMethod.PUT) //Updating and Inserting with this endpoint
    public int updateUserDeckHistory(@PathVariable long userId, @PathVariable long deckId, @RequestParam(required = true, name = "score") double scorePercent, @RequestParam(required = false, name = "correct") Integer correctAnswers) {
        if (correctAnswers != null) {
            return deckDao.updateUserDeckHistory(userId, deckId, scorePercent, correctAnswers);
        } else {
            return deckDao.updateUserDeckHistory(userId, deckId, scorePercent);
        }
    }
    
    @PreAuthorize("@deckSecurity.canRead(#deckId, authentication)")
    @RequestMapping(path = "/deck/{deckId}/history/{userId}", method = RequestMethod.POST) //Updating and Inserting with this endpoint
    public int createUserDeckHistory(@PathVariable long userId, @PathVariable long deckId, @RequestParam(required = true, name = "score") double scorePercent, @RequestParam(required = false, name = "correct") Integer correctAnswers) {
        if (correctAnswers != null) {
            return deckDao.createUserDeckHistory(userId, deckId, scorePercent, correctAnswers);
        } else {
            return deckDao.createUserDeckHistory(userId, deckId, scorePercent);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(path = "/deck/report/{userId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getUserStudyReport(@PathVariable long userId) {
        return deckDao.getUserStudyReport(userId);
    }

}
