package flashcards.security;

import flashcards.dao.DeckDao;
import flashcards.dao.UserDao;
import flashcards.model.Deck;
import flashcards.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("deckSecurity")
public class DeckSecurityService {
    
    private final DeckDao deckDao;
    private final UserDao userDao;
    
    public DeckSecurityService(DeckDao deckDao, UserDao userDao) {
        this.deckDao = deckDao;
        this.userDao = userDao;
    }
    
    private boolean isAdmin(Authentication auth) {
        if (auth == null) return false;
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private boolean isAuthenticated(Authentication auth) {
        if (auth == null) return false;
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_USER") || a.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private boolean isOwner(Long deckId, Authentication auth) {
        if (!isAuthenticated(auth)) return false;
        try {
            Deck deck = deckDao.getDeckById(deckId);
            User user = userDao.findByUsername(auth.getName());
            return user != null && user.getId() == deck.getOwnerId();
        } catch (Exception e) {
            return false;
        }
    }
    
    private int getDeckStatus(Long deckId) {
        try {
            Deck deck = deckDao.getDeckById(deckId);
            return deck.getStatus();
        } catch (Exception e) {
            return 0; // Invalid deck
        }
    }
    
    public boolean canRead(Long deckId, Authentication auth) {
        int status = getDeckStatus(deckId);
                if (status == 2 || status == 3) {
            return true;
        }
        
        if (status == 1) {
            return isAdmin(auth) || isOwner(deckId, auth);
        }
        
        return false;
    }
    
    public boolean canModify(Long deckId, Authentication auth) {
        if (!isAuthenticated(auth)) return false;
        return isAdmin(auth) || isOwner(deckId, auth);
    }
    
 
    public boolean canDelete(Long deckId, Authentication auth) {
        if (!isAuthenticated(auth)) return false;
        return isAdmin(auth) || isOwner(deckId, auth);
    }
    
    public boolean canCreate(Authentication auth) {
        return isAuthenticated(auth);
    }

    public boolean canAccessUserDecks(Long userId, Authentication auth) {
        if (!isAuthenticated(auth)) return false;
        if (isAdmin(auth)) return true;
        
        try {
            User user = userDao.findByUsername(auth.getName());
            return user != null && user.getId().equals(userId);
        } catch (Exception e) {
            return false;
        }
    }
}