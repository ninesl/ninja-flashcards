package flashcards.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Flashcards model classes.
 * Tests core business logic without external dependencies.
 */
public class ModelUnitTest {

    @Test
    public void testUserCreation() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActivated(true);

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isActivated());
    }

    @Test
    public void testUserWithAuthorities() {
        User user = new User(1L, "admin", "password", "ROLE_ADMIN,ROLE_USER");
        
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals(2, user.getAuthorities().size());
        assertTrue(user.isActivated());
    }

    @Test
    public void testDeckCreation() {
        Deck deck = new Deck();
        deck.setDeckId(1L);
        deck.setDeckName("Math Basics");
        deck.setDeckDesc("Basic math flashcards");
        deck.setGenre("Mathematics");
        deck.setOwnerId(1L);
        deck.setStatus(1); // PRIVATE

        assertEquals(1L, deck.getDeckId());
        assertEquals("Math Basics", deck.getDeckName());
        assertEquals("Basic math flashcards", deck.getDeckDesc());
        assertEquals("Mathematics", deck.getGenre());
        assertEquals(1L, deck.getOwnerId());
        assertEquals(1, deck.getStatus());
    }

    @Test
    public void testDeckStatusValidation() {
        Deck deck = new Deck();
        
        // Test PRIVATE status
        deck.setStatus(1);
        assertEquals(1, deck.getStatus());
        
        // Test UNLISTED status  
        deck.setStatus(2);
        assertEquals(2, deck.getStatus());
        
        // Test PUBLIC status
        deck.setStatus(3);
        assertEquals(3, deck.getStatus());
    }

    @Test
    public void testCardCreation() {
        Card card = new Card();
        card.setCardId(1L);
        card.setQuestion("What is 2 + 2?");
        card.setAnswer("4");
        card.setDeckId(1L);

        assertEquals(1L, card.getCardId());
        assertEquals("What is 2 + 2?", card.getQuestion());
        assertEquals("4", card.getAnswer());
        assertEquals(1L, card.getDeckId());
    }

    @Test
    public void testLoginDTOValidation() {
        LoginDTO login = new LoginDTO();
        login.setUsername("testuser");
        login.setPassword("password123");

        assertEquals("testuser", login.getUsername());
        assertEquals("password123", login.getPassword());
        assertNotNull(login.getUsername());
        assertNotNull(login.getPassword());
    }

    @Test
    public void testRegisterUserDTOValidation() {
        RegisterUserDTO register = new RegisterUserDTO();
        register.setUsername("newuser");
        register.setPassword("newpassword");
        register.setRole("ROLE_USER");

        assertEquals("newuser", register.getUsername());
        assertEquals("newpassword", register.getPassword());
        assertEquals("ROLE_USER", register.getRole());
    }

    @Test
    public void testAuthorityCreation() {
        Authority auth = new Authority("ROLE_USER");
        assertEquals("ROLE_USER", auth.getName());
        
        Authority adminAuth = new Authority("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", adminAuth.getName());
    }
}