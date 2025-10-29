# Flashcard Application Demo Walkthrough

## Part 1: Public Decks (No Login Required)
1. Navigate to the home page
2. Click on "Public Decks" to view all public decks
3. Sort the decks by different criteria (name, date, popularity)
4. Note that you can see decks from both 'user' and 'lance' users

## Part 2: Lance's Account - View Progress
1. Click "Login" and sign in as:
   - Username: `lance`
   - Password: `password`
2. Navigate to "My Decks" to see Lance's 5 decks (1 private, 4 public)
3. Go to "Study Report" to view study progress:
   - You'll see progress indicators for decks Lance has studied
   - Green = mastered, Yellow = learning, Red = needs practice
   - Note the correct answer counts for each deck

## Part 3: Study Session and Save Score
1. While logged in as Lance, go to "My Decks" or "Public Decks"
2. Select a deck to study (e.g., "Basic Math" from user's public decks)
3. Click "Start Study Session" or "Study"
4. Go through the flashcards:
   - View question
   - Try to answer
   - Reveal answer
   - Mark as correct/incorrect
5. Complete all cards in the deck
6. At the end of the session:
   - View your score summary
   - Click "Save Score" to record your progress
   - Your score will be saved to study history (1=red, 2=yellow, 3=green based on performance)
7. Return to "Study Report" to see your updated progress

## Part 4: Delete Private Deck
1. While logged in as Lance, go to "My Decks"
2. Find the "Private Research" deck (marked as private)
3. Click delete/remove on this deck
4. Confirm the deletion

## Part 5: Create New Deck with Unlisted Status
1. Click "Create New Deck"
2. Fill in:
   - Name: "Test Unlisted Deck"
   - Description: "Testing unlisted deck access"
   - Genre: Choose any
   - Status: Set to "Unlisted"
3. Add a few sample cards
4. Save the deck and note the URL (e.g., `/deck/11`)

## Part 6: Test Unlisted Deck Access
1. Log out of Lance's account
2. Navigate directly to the unlisted deck URL (e.g., `/deck/11`)
3. **Expected**: You CAN view the unlisted deck (status = 2 allows shared access)

## Part 7: Change to Private and Test Access
1. Log back in as Lance
2. Edit the "Test Unlisted Deck"
3. Change status from "Unlisted" to "Private" (status = 1)
4. Save and note the deck ID
5. Log out

## Part 8: Test Private Deck Access (Should Fail)
1. While logged out, try to access the private deck URL
2. **Expected**: Access denied
3. These endpoints should be protected for private decks

## Part 9: Admin Actions
1. Log out and then log in as admin:
   - Username: `admin`
   - Password: `password`
2. Navigate to "Public Decks" or deck management area
3. Find the "TEST" deck (owned by 'user')
4. As admin, delete the TEST deck
5. Confirm deletion - admins can delete any public deck