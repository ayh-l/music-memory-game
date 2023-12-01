# My Personal Project

## Project Description: Memory Game

This application is a simple memory game where the player
must remember a sequence of sounds. There are four
types of sounds which will make up each sequence, and the
sequence length will start at one sound, adding one new sound
each time the player successfully remembers the sequence.

This application is meant for anyone who would like to test their
memory or have a little bit of fun. I chose to create this application
because I enjoy memory games and I also love music, and a sound-based
memory game allows me to mesh the two!

## User Stories

- As a user, I want to be able to start a new game with a sequence
of length 1.
- As a user, I want to be able to view my
highest score.
- As a user, I want to be able to view the number of rounds I have played.
- As a user, (during a game) I want to be able to enter a sequence of
defined sounds and receive the next sequence of sounds if correct, or
end the game and add it to my list of total games if incorrect.
- As a user, I want to be able to view all the rounds I have played (with their list of sounds).
- As a user, I want to be able to save my current game session (ie.
all the rounds played during this session) if I so choose.
- As a user, I want to be able to load my saved game data (ie. highscore,
number of rounds played, etc.) if I so choose.

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by pressing "View game history", entering an
integer into the field beside the "Filter rounds with scores >=" button and then clicking this button. This
will filter the list of rounds to only display the rounds with scores above the given score.
- You can generate the second required action related to adding Xs to a Y by pressing "View game history" and pressing
"Remove all rounds". This will remove all the rounds stored in the history and reset the high score and the number of
played rounds.
- You can locate my visual component by pressing "Start new round", where you will see buttons with instrument icons.
The buttons display a music note icon when pressed.
- You can save the state of my application by pressing "Save round history".
- You can reload the state of my application by pressing "Load round history".

## Phase 4: Task 2
Fri Dec 01 15:35:48 PST 2023
Filter was reset


Fri Dec 01 15:35:48 PST 2023
Filtered out rounds with scores below 5


Fri Dec 01 15:35:50 PST 2023
Filter was reset


Fri Dec 01 15:35:50 PST 2023
Filtered out rounds with scores below 1


Fri Dec 01 15:35:51 PST 2023
All rounds were removed from history


Fri Dec 01 15:35:57 PST 2023
Added a round with score 2


Fri Dec 01 15:35:59 PST 2023
Filter was reset


Fri Dec 01 15:35:59 PST 2023
Filtered out rounds with scores below 1

## Phase 4: Task 3
After reflecting on the structure of my project through the UML diagram, I noticed some unnecessary associations.
For instance, Round has an association with Sound which I realize is unnecessary, as I could store sounds using their
int labels instead. Perhaps one of the most problematic aspects I noticed, however,
is the amount of coupling within the ui package, which I found to be problematic. The coupling is a result of each panel
needing access to the RoundPlayerVisual in order to access the current RoundHistory, and I believe these associations
can be significantly reduced by applying the Singleton pattern onto the RoundHistory class. Doing so would be suitable,
as there is only ever one RoundHistory at a time, and would remove the need for each panel (HistoryPanel, GamePanel and 
MenuPanel) to be associated with
RoundPlayerVisual, and for RoundPlayerVisual to be associated with RoundHistory. I believe I can also remove the
associations from RoundPlayerVisual to HistoryPanel and to GamePanel using the Observer pattern. I created these
associations for the purpose of updating the panels before displaying each panel, and implementing the Observer pattern
(where RoundPlayerVisual would be the Subject and HistoryPanel and GamePanel would be the Observers) would allow me
to reduce coupling by allowing each panel to manage its own updating processes through notification by the
RoundPlayerVisual that the display will be changing.