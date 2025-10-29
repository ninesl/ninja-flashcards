-- H2 compatible test data matching the actual database schema
DROP TABLE IF EXISTS user_deck_history;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS deck;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id IDENTITY PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL
);

CREATE TABLE deck (
    deck_id IDENTITY PRIMARY KEY,
    deck_status int NOT NULL DEFAULT 1,
    deck_name varchar NOT NULL,
    deck_desc varchar NOT NULL,
    owner_id int NOT NULL,
    genre varchar NOT NULL,
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE card (
    card_id IDENTITY PRIMARY KEY,
    question varchar NOT NULL,
    answer varchar NOT NULL,
    image_url varchar,
    deck_id int NOT NULL,
    CONSTRAINT fk_deck_id FOREIGN KEY (deck_id) REFERENCES deck(deck_id)
);

CREATE TABLE user_deck_history (
    user_id int NOT NULL,
    deck_id int NOT NULL,
    score int NOT NULL DEFAULT 1,
    correct_answers int DEFAULT 0,
    last_updated timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_deck_id PRIMARY KEY (user_id, deck_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_deck_id FOREIGN KEY (deck_id) REFERENCES deck(deck_id)
);

-- Insert test users
INSERT INTO users (username, password_hash, role) VALUES 
('testuser', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER'),
('testadmin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_ADMIN'),
('user1', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER');

-- Insert test decks (1=private, 2=pending, 3=approved/public)
INSERT INTO deck (deck_status, deck_name, deck_desc, owner_id, genre) VALUES
(3, 'Public Test Deck', 'A public deck for testing', 1, 'Technology'),
(1, 'Private Test Deck', 'A private deck for testing', 1, 'Technology'),
(3, 'Admin Deck', 'An admin deck', 2, 'General');

-- Insert test cards
INSERT INTO card (question, answer, deck_id) VALUES
('What is Java?', 'A programming language', 1),
('What is Spring?', 'A Java framework', 1),
('Private Question', 'Private Answer', 2),
('Admin Question', 'Admin Answer', 3);