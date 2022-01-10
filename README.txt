The files are: Board.java, Game.java, Cell.java, Game.java, Boat.java
Game class contains main method

In order to run the BattleBoats program via terminal compile each file by:
"javac <fname.java>"
Ex: javac Game.java

Then run the game class with the "java Game" command

Once command is entered you will be asked to enter an integer 1 (beginner), 2 (intermediate), or 3 (expert) to indicate the difficulty of the game
which is determined by the size of the board: 3x3, 6x6, and 9x9

You will then be asked to type "debug" to see a visual of where the boats are placed on the board or type anything else to play the game as you would conventionally

Then you are given the option to either:
fire, which allows you to select a coordinate to fire at
drone, which scans a row or column and return the number of boats detected (only allowed one drone per game)
missile, which fires a 3x3 attack depending on the center of coordinates you choose (only allowed one missile per game)

The game will either end when you quit or have sunk all the boats
Program will return the total number of shots and turn taken during the game

Enjoy!








