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

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by pressing "View game history", entering an
integer into the field beside the "Filter rounds with scores >=" button and then clicking this button. This
will filter the list of rounds to only display the rounds with scores above the given score.
- You can generate the second required action related to adding Xs to a Y by pressing "View game history" and pressing
"Play all sounds". This will cycle through the list of completed rounds and play their sound lists.
- You can locate my visual component by pressing "Start new round", where you will see buttons with instrument icons.
The buttons display a music note icon when pressed.
- You can save the state of my application by pressing "Save round history".
- You can reload the state of my application by pressing "Load round history".

