-- H2 Database Schema - Compatible version of database/schema.sql for testing

-- Drop tables if they exist (H2 syntax)
DROP TABLE IF EXISTS user_deck_history;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS deck;
DROP TABLE IF EXISTS users;

-- Users table (PostgreSQL serial -> H2 IDENTITY)
CREATE TABLE users (
    user_id IDENTITY PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL
);

-- Insert default users (same passwords as PostgreSQL version)
INSERT INTO users (username, password_hash, role) VALUES 
('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER'),
('admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_ADMIN');

-- Deck table  
CREATE TABLE deck (
    deck_id IDENTITY PRIMARY KEY,
    deck_status int NOT NULL DEFAULT 1, -- 1 private, 2 pending, 3 approved
    deck_name varchar NOT NULL,
    deck_desc varchar NOT NULL,
    owner_id int NOT NULL,
    genre varchar NOT NULL,
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

-- Card table
CREATE TABLE card (
    card_id IDENTITY PRIMARY KEY,
    question varchar NOT NULL,
    answer varchar NOT NULL,
    image_url varchar,
    deck_id int NOT NULL,
    CONSTRAINT fk_deck_id FOREIGN KEY (deck_id) REFERENCES deck(deck_id)
);

-- User deck history table
CREATE TABLE user_deck_history (
    user_id int NOT NULL,
    deck_id int NOT NULL,
    score int NOT NULL DEFAULT 1, -- 1 red, 2 yellow, 3 green
    correct_answers int DEFAULT 0,
    last_updated timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_deck_id PRIMARY KEY (user_id, deck_id),
    CONSTRAINT fk_udh_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_udh_deck_id FOREIGN KEY (deck_id) REFERENCES deck(deck_id)
);

-- Insert some test data for integration tests
INSERT INTO deck (deck_status, deck_name, deck_desc, owner_id, genre) VALUES
(3, 'Public Java Deck', 'Java programming questions', 1, 'Technology'),
(1, 'Private Math Deck', 'Math practice questions', 1, 'Mathematics'),
(3, 'Admin Science Deck', 'Science trivia', 2, 'Science');

INSERT INTO card (question, answer, deck_id) VALUES
('What is polymorphism?', 'The ability of objects to take multiple forms', 1),
('What is inheritance?', 'A mechanism where a class acquires properties of another class', 1),
('What is 2 + 2?', '4', 2),
('What is the square root of 16?', '4', 2),
('What is the chemical symbol for water?', 'H2O', 3),
('What planet is closest to the sun?', 'Mercury', 3);

INSERT INTO user_deck_history (user_id, deck_id, score, correct_answers) VALUES
(1, 1, 3, 2),
(1, 3, 2, 1),
(2, 1, 3, 2);