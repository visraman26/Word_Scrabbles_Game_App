# Word_Scrabbles_Game_App
An android game app played between two players.



DESCRIPTION: The game is between two players to make proper words.Whenever player1 get a right word then player1 will get points same as word length.
If player1 has made a word before and again player1 wants to challenge that word then the player2 will get points same as word length and vice versa. Or if the 
word that player1 want to challenge is invalid means not exist in dictionary then also player2 will get points same as word length.When there is 
no empty tile left, then game will over.</br></br>


Functions:</br></br>

Function bPass:</br>
There are two player. After starting the game player1 will enter a letter, if player1 thinks that there is a valid word after inserting a letter in 
horizontal rows or vertical column then player1 can challenge to player2 otherwise he pass the turn to another player.</br></br>

Function onLongClickListener:</br>
If player1 want to challenge then first click on the select text button and select the word by long clicking the letter then player1 clicks the challenge button.
The word will be selected.</br></br>

Function bChallenge:</br>
If player1 challenge to player2, if it is a valid word and has not used before then the score of current player will be increased by length of word otherwise 
score of the opponent player is increased by length of word and vice versa.</br></br>

Function bReset:</br>
Player can reset the game on clicking Reset button.By doing this all the tiles will be empty. 
And scores of both players will be equal to Zero.</br></br>

Function checkForSequence:</br>
Function is for checking that sequence of selected letters is valid or not.Here valid means it should be in a row and  selection should be in 
left to right direction.For a column it should be in up to down direction.</br></br>

