-- ********************************************************************************
-- Sample data for flashcard decks
-- admin (user_id = 1): no decks
-- user (user_id = 2): 5 decks - 3 public (including TEST), 1 private, 1 unlisted
-- lance (user_id = 3): 5 decks - 1 private, 4 public
-- ********************************************************************************

-- Users with default password "password"
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('lance','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

-- Sample decks owned by user (user_id = 2)
-- 3 public decks (status = 3)
INSERT INTO deck (deck_status, deck_name, deck_desc, owner_id, genre) VALUES
(3, 'TEST', 'Test deck for public use', 2, 'Science'),
(3, 'Basic Math', 'Fundamental math concepts', 2, 'Math'),
(3, 'History Facts', 'Important historical facts', 2, 'History'),

-- 1 private deck (status = 1)
(1, 'Personal Notes', 'Private study materials', 2, 'Science'),

-- 1 unlisted deck (status = 2)
(2, 'Shared Study Guide', 'Unlisted deck for sharing', 2, 'Science'),

-- Sample decks owned by lance (user_id = 3)
-- 1 private deck (status = 1)
(1, 'Private Research', 'My private research materials', 3, 'Science'),

-- 4 public decks (status = 3)
(3, 'Spanish Basics', 'Common Spanish phrases', 3, 'Language'),
(3, 'JavaScript Guide', 'JS programming concepts', 3, 'Programming'),
(3, 'World Geography', 'Countries and capitals', 3, 'Geography'),
(3, 'Chemistry 101', 'Basic chemistry concepts', 3, 'Science');

-- Sample cards for TEST deck (deck_id = 1) - owned by user
INSERT INTO card (question, answer, deck_id) VALUES
('What is 2 + 2?', '4', 1),
('What is the capital of France?', 'Paris', 1),
('What is H2O?', 'Water', 1);

-- Sample cards for Basic Math deck (deck_id = 2) - owned by user
INSERT INTO card (question, answer, deck_id) VALUES
('What is 7 × 8?', '56', 2),
('What is the area of a circle formula?', 'π × r²', 2),
('What is 15% of 200?', '30', 2);

-- Sample cards for History Facts deck (deck_id = 3) - owned by user
INSERT INTO card (question, answer, deck_id) VALUES
('When did World War II end?', '1945', 3),
('Who was the first president of the USA?', 'George Washington', 3),
('What year did the Berlin Wall fall?', '1989', 3);

-- Sample cards for Personal Notes deck (deck_id = 4) - owned by user (private)
INSERT INTO card (question, answer, deck_id) VALUES
('What is photosynthesis?', 'Process plants use to make food from sunlight', 4),
('DNA stands for?', 'Deoxyribonucleic acid', 4),
('What is gravity?', 'Force that attracts objects toward Earth', 4);

-- Sample cards for Shared Study Guide deck (deck_id = 5) - owned by user (unlisted)
INSERT INTO card (question, answer, deck_id) VALUES
('What is the Pythagorean theorem?', 'a² + b² = c²', 5),
('What is the derivative of x²?', '2x', 5),
('What is ∫x dx?', 'x²/2 + C', 5);

-- Sample cards for Private Research deck (deck_id = 6) - owned by lance (private)
INSERT INTO card (question, answer, deck_id) VALUES
('Advanced topic 1', 'Advanced answer 1', 6),
('Advanced topic 2', 'Advanced answer 2', 6),
('Advanced topic 3', 'Advanced answer 3', 6);

-- Sample cards for Spanish Basics deck (deck_id = 7) - owned by lance
INSERT INTO card (question, answer, deck_id) VALUES
('Hello', 'Hola', 7),
('Thank you', 'Gracias', 7),
('How are you?', '¿Cómo estás?', 7),
('Goodbye', 'Adiós', 7);

-- Sample cards for JavaScript Guide deck (deck_id = 8) - owned by lance
INSERT INTO card (question, answer, deck_id) VALUES
('What does "var" declare?', 'A variable', 8),
('What is a function?', 'A reusable block of code', 8),
('What does "console.log()" do?', 'Prints output to the browser console', 8);

-- Sample cards for World Geography deck (deck_id = 9) - owned by lance
INSERT INTO card (question, answer, deck_id) VALUES
('Capital of France', 'Paris', 9),
('Capital of Japan', 'Tokyo', 9),
('Capital of Brazil', 'Brasília', 9),
('Capital of Germany', 'Berlin', 9);

-- Sample cards for Chemistry 101 deck (deck_id = 10) - owned by lance
INSERT INTO card (question, answer, deck_id) VALUES
('What is the symbol for gold?', 'Au', 10),
('What is H2O?', 'Water', 10),
('What is the atomic number of carbon?', '6', 10),
('What is NaCl?', 'Sodium chloride (salt)', 10);

-- Sample user_deck_history entries for lance (user_id = 3)
-- Lance's study history for his own decks
INSERT INTO user_deck_history (user_id, deck_id, score, correct_answers, last_updated) VALUES
-- Private Research deck (his own private deck) - good progress
(3, 6, 3, 3, '2024-01-15 10:30:00'),

-- Spanish Basics deck - excellent progress
(3, 7, 3, 4, '2024-01-20 14:15:00'),

-- JavaScript Guide deck - moderate progress
(3, 8, 2, 2, '2024-01-18 09:45:00'),

-- World Geography deck - struggling a bit
(3, 9, 1, 1, '2024-01-19 16:20:00'),

-- Chemistry 101 deck - good progress
(3, 10, 3, 3, '2024-01-21 11:00:00'),

-- Lance's study history for user's public decks
-- TEST deck (user's public deck) - excellent progress
(3, 1, 3, 3, '2024-01-22 13:30:00'),

-- Basic Math deck (user's public deck) - moderate progress
(3, 2, 2, 2, '2024-01-17 15:45:00'),

-- History Facts deck (user's public deck) - struggling
(3, 3, 1, 1, '2024-01-16 08:30:00');

-- Reset sequences to ensure new registrations work properly
SELECT setval('users_user_id_seq', (SELECT MAX(user_id) FROM users));
SELECT setval('deck_deck_id_seq', (SELECT MAX(deck_id) FROM deck));
SELECT setval('card_card_id_seq', (SELECT MAX(card_id) FROM card));