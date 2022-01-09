import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        int mode = 1;
        System.out.println("Are you ready to play Battle Boats?! \nPlease type '1' for Beginner mode, '2' for Intermediate, or '3' for Expert to create the game board: ");

        Scanner scan1 = new Scanner(System.in);
        String input = scan1.nextLine();


        while (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
            System.out.println("Invalid input. Please either choose 1 (Beginner), 2 (Intermediate) or 3 (Expert) to continue");
            input = scan1.nextLine();
        }
        if (input.equals("1")) mode = 3;
        if (input.equals("2")) mode = 6;
        if (input.equals("3")) mode = 9;
        Board board = new Board(mode);
        board.placeBoats();


        String userInput;
        int attackRow = 0;
        int attackColumn = 0;
        boolean missileUse = false;
        boolean droneUse = false;
        boolean debugMode = false;

        Scanner scan2 = new Scanner(System.in);
        System.out.println("Type 'debug' to play in debug mode \nType anything else to play like in standard mode");
        String temp = scan2.nextLine();
        if (temp.equals("debug")) {
            debugMode = true;
        }
        while (true){
            if ( board.getBoatsRemaining() == 0 ) break;
            if ( debugMode ) board.print();
            System.out.println("\nChoose move: (fire, missile, or drone)\nType quit to end the game"); //took out submarine, didn't have time to implement

            // read user input
            userInput = scan2.next();

            if ( userInput.equals("fire") ) {
                System.out.println("Choose coordinates to attack. First coordinate is row(starting with 0) and second is column(starting with zero)\nPlease type each coordinate on separate lines: ");
                boolean valid = false;
                while (!valid) {
                    // read user input
                    attackRow = scan2.nextInt();
                    attackColumn = scan2.nextInt();
                    if ( 0 <= attackRow && attackRow < board.getGameBoard().length && 0 <= attackColumn && attackColumn < board.getGameBoard()[0].length )
                        valid = true;
                    else System.out.println("Invalid coordinates. Please try again.");
                }

                    char status = board.getGameBoard()[attackRow][attackColumn].getStatus();

                    // call fire method
                    int penalties = board.fire(attackRow, attackColumn);

                    if (board.getGameBoard()[attackRow][attackColumn].getStatus() == 'H' && status == 'H' || board.getGameBoard()[attackRow][attackColumn].getStatus() == 'M' && status == 'M') System.out.println("penalty");

                    // here we implement the penalty
                    for ( int i = 0; i < penalties; i++ ) board.addTurn();

                    board.addShots();

                } else if ( userInput.equals("missile") ) {

                    if ( !missileUse ) {

                        System.out.println("Choose center coordinates of missile attack (type each coordinate on separate lines):");

                        boolean valid = false;

                        while ( !valid ) {
                            // read user input
                            attackRow = scan2.nextInt();
                            attackColumn = scan2.nextInt();

                            // check for valid coordinates
                            if ( 0 <= attackRow && attackRow < board.getGameBoard().length && 0 <= attackColumn && attackColumn < board.getGameBoard()[0].length )
                                valid = true;
                            else System.out.println("Invalid coordinates. Please try again.");
                        }
                        // call missile method
                        board.missile(attackRow, attackColumn);

                        missileUse = true;
                        board.addShots();

                    } else {
                        System.out.println("You can't do that! Your missile has already been used!");
                        continue;
                    }

                } else if ( userInput.equals("drone") ) {

                    if ( !droneUse ) {

                        System.out.println("Choose to scan through a row or column:");

                        String choice = null;
                        boolean direction = false;
                        int index = 0;
                        boolean valid = false;

                        while ( !valid ) {

                            // read input
                            choice = scan2.next();

                            if ( choice.equals("row") ) {
                                direction = true;
                                valid = true;
                            } else if ( choice.equals("column") ) {
                                direction = false;
                                valid = true;
                            } else {
                                System.out.println("Invalid input. Try again.");
                            }
                        }

                        System.out.println("Choose which index you want to scan through");

                        valid = false;

                        while ( !valid ) {

                            // read user input
                            index = scan2.nextInt();

                            // check for valid
                            if ( 0 <= index ) {
                                if ( choice.equals("row") && index < board.getGameBoard().length ) valid = true;
                                else if ( choice.equals("column") && index < board.getGameBoard()[0].length ) valid = true;
                                else System.out.println("Invalid index. Try again.");
                            }
                        }

                        // if we get valid input for direction and index
                        board.drone(direction, index);
                        droneUse = true;

                    } else {
                        System.out.println("You can't do that! Your drone has already been used!");
                        continue;
                    }

                } else if ( userInput.equals("quit") ) break;
                else {
                    System.out.println("Invalid move. Please try again");
                    continue;
                }

                // display player game board
                if ( !debugMode ) board.display();

                // add turn
                board.addTurn();

            } // end game while loop

            if (board.getBoatsRemaining() == 0) System.out.println("All boats sunk!\nGame Results:\nTotal shots = " + board.getTotalShots() + "\nTurns = " + board.getTurns());
            else System.out.println("\nGame Results:\nTotal shots = " + board.getTotalShots() + "\nTurns = " + board.getTurns());


        }
    }







