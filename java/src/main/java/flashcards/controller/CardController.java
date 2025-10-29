package flashcards.controller;

import flashcards.dao.CardDao;
import flashcards.model.Card;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class CardController {
    private CardDao cardDao;

    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }


    @PreAuthorize("@deckSecurity.canRead(#deckId, authentication)")
    @RequestMapping(path = "/deck/{deckId}/card", method = RequestMethod.GET)
    public List<Card> getAllCard(@PathVariable long deckId) {
        return cardDao.getAllCards(deckId);
    }

    @PreAuthorize("@deckSecurity.canModify(#deckId, authentication)")
    @RequestMapping(path = "/deck/{deckId}/card", method = RequestMethod.POST)
    public Card addCard(@PathVariable long deckId, @RequestBody Card card) {
        return cardDao.addCard(card);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(path = "/deck/{deck-id}/card/{card-id}", method = RequestMethod.PUT)
    public Card updateCard(@RequestBody Card card) {
        return cardDao.updateCard(card);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(path = "/deck/{deck-id}/card/{id}", method = RequestMethod.DELETE)
    public boolean deleteCard(@PathVariable long id) {
        return cardDao.deleteCard(id);
    }
}
