# TheRoyalGameOfUr

![Game of Ur](http://kaisertim.de/ressources/GameOfUr.jpg)
## History

"The Royal Game of Ur, also known as the Game of Twenty Squares, refers to an ancient game represented by two gameboards found in the Royal Tombs of Ur in Iraq by Sir Leonard Woolley in the 1920s. The two boards date from the First Dynasty of Ur, before 2600 BC, thus making the Royal Game of Ur one of the oldest examples of board gaming equipment found, although Senet boards found in Egyptian graves predate it as much as 900 years. The Ur-style Twenty Squares gameboard was also known in Egypt as Asseb, and has been found in Pharaoh Tutankhamen's tomb, among other places. Discovery of a tablet partially describing the gameplay has allowed the game to be played again after over 2000 years, although reconstructions of the detailed rules have differed widely."  
(https://en.wikipedia.org/wiki/Royal_Game_of_Ur, 16.05.2018)

## Rules
The Royal Game of Ur is played with two sets of seven tokens, and four tetrahedral dice on a board with 20 tiles.


**Tokens**  
Each player gets seven tokens. One player gets white tokens, the other player gets black tokens. The player with the white tokens starts.

**Dice**  
The Game is played with four tetrahedral dice. Each dice hast two white and two black corners. The dice result is the sum of all white corners facing upwards. Possible results are:
- 0 with likelihood 1/16
- 1 with likelihood 4/16
- 2 with likelihood 6/16
- 3 with likelihood 4/16
- 4 with likelihood 1/16

**Board**  
The board has 20 tiles. Six private tiles for both players and eight shared tiles. 5 of the tiles are double roll tiles. If your token lands on one of them you can roll the dice again. The layout can be seen here:

         _____________↓_         _↑_______ 
        |>3<| 2 | 1 | 0 |       |>13<| 12 |
        |---|---|---|---|---.---|----|----|
        | 4 | 5 | 6 |>7<| 8 | 9 | 10 | 11 |
        |---|---|---|---|---'---|----|----|
        |>3<| 2 | 1 | 0 |       |>13<| 12 |
        '---'---'---'-↑-'       '-↓--'----'
        
Double roll tiles are marked with > <.  
The tokens have to move over the tiles in ascending order. The top row can only be used by player 1, the bottom row only by player 2. The middle row is shared by both players. You need one extra move to enter the board (e.g. with a roll of 2 you can move a token outside the game board on tile number 1), and one to leave the board. You need to leave the board on an exact roll (e.g. if your token is on tile 12 you can't leave the board with a dice roll of 3 or 4, you need a 2). If you land on the same tile in the middle row as your opponent, your opponents token gets taken from the board and has to start again at the beginning. The exception is tile number 7, tokens are safe on that one.  

Winner is whoever gets all of his tokens to the end first.

## AI Competition
Goal of this project is for two or more competitors to each create an AI capable of playing this game. The Ais then compete against each other to determine the better player. The competition is repeated a couple of thousand times to take out the factor of luck. A framework is provided. Competitors only create a new class extending "Player". The class "TemplateAI" can be used as a template.

## AIs
Every competitor can present his AIs here.

**Simple AI**  
by @timkaiser (part of the framework)  
Always moves the first valid token in it's list.

**Random AI**  
by @timkaiser (part of the framework)  
Moves a random valid token every term.

**Heuristic AI**  
by @d-lowl  
Uses simple heuristic, evaluating the board for every possible move (giving points for capturing and getting roll again).

**Human (not really an AI)**  
by @timkaiser (part of the framework)  
Moves a token based on user input via the console. Using console output is highly recommended for this one. 
Used for testing purposes or fun.

## Results 
**Simple AI vs Random AI**  
SimpleAI: 92271  
RandomAI: 7729  
Winner: SimpleAI with 92,3% 

**Heuristic AI vs Random AI**  
HeuristicAI: 99563  
RandomAI: 437  
Wimmer: HeuristicAI with 99,6%

**Heuristic AI vs Simple AI**  
HeuristicAI: 88769  
SimpleAI: 11231  
Wimmer: HeuristicAI with 88.8%
