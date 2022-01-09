import java.util.Random;

public class Board {
    private Cell [][] gameBoard;
    private Boat[] boats;
    private int totalShots,totalTurns, boatsRemaining;

    public Board(int mode){
        gameBoard = new Cell[mode][mode];

        for (int i=0; i < gameBoard.length; i++){
            for (int j=0; j < gameBoard[0].length; j++){
                Cell spot = new Cell(i,j);
                gameBoard[i][j] = spot;
            }
        }
        if (gameBoard[0].length == 3) boats = new Boat[1];
        else if (gameBoard[0].length == 6) boats = new Boat[3];
        else if (gameBoard[0].length == 9) boats = new Boat[5];

        totalShots = 0;
        totalTurns = 0;
        boatsRemaining = boats.length;
    }

    public Cell[][] getGameBoard(){
        return gameBoard;
    }

    public void placeBoats(){
        int boatLength = 0;
        boolean boatOrientation;
        boolean boatLocation;
        int rowStart;
        int colStart;
        Random rand = new Random();

        for (int i = 0; i < boats.length; i++){ //creates boat objects depending on size
//            System.out.println("this is the boat length: " + boats.length);
            if (i == 0) boatLength = 2; //this if-else statement block creates boat lengths
            else if (i == 1) boatLength = 3;
            else if (i == 2) boatLength = 4;
            else if (i == 3) boatLength = 3;
            else if (i == 4) boatLength = 5;

            boatLocation = false;

            while (!boatLocation){
                boatOrientation = rand.nextBoolean(); //random boat orientation

                rowStart = rand.nextInt(gameBoard.length); //random starting point
                colStart = rand.nextInt(gameBoard[0].length);

                try { //referenced website for try except blocks
                    for (int j = 0; j<boatLength; j++){
                        if (boatOrientation){
                            if (gameBoard[rowStart + j][colStart].getStatus() != '-') break;
                        } else{
                            if (gameBoard[rowStart][colStart+j].getStatus() != '-') break;
                        }
                        if (j == boatLength -1){
                            boatLocation = true;
                            break;
                        }
                    }
                } catch(Exception e){
                    continue;
                }
                if (boatLocation) {
                    Cell[] boatCells = new Cell[boatLength];
                    for (int k = 0; k < boatLength; k++){
                        if (boatOrientation){
                            boatCells[k] = gameBoard[rowStart + k][colStart];
                            boatCells[k].setStatus('B');
                        } else{
                            boatCells[k] = gameBoard[rowStart][colStart + k];
                            boatCells[k].setStatus('B');
                        }
                    }
                    Boat boat = new Boat(boatLength, boatOrientation, boatCells);

                    boats[i] = boat;
                }
            }
        }
    }

    public int fire(int row, int col){
        char status = gameBoard[row][col].getStatus();
        int numPenalty = 0;

        if (0 <= row && row <= gameBoard.length && 0 <= col && col <= gameBoard.length){
            if (gameBoard[row][col].getStatus() == '-') {
                gameBoard[row][col].setStatus('M');
            }
            else if (gameBoard[row][col].getStatus() == 'B') {
                gameBoard[row][col].setStatus('H');
                System.out.println("Hit!");
            }
            else numPenalty++;

            if (gameBoard[row][col].getStatus() == 'M' && status == '-') System.out.println("Miss!");
//            if (gameBoard[row][col].getStatus() == 'B' && status != 'M') System.out.println("Hit!"); //changed from double H, B and M
            if (gameBoard[row][col].getStatus() == 'H' && status == 'H' || gameBoard[row][col].getStatus() == 'M' && status == 'M') System.out.println("Penalty");

            boatStatus();
        } else System.out.println("Invalid selection. Turn lost");
        return numPenalty;
    }

    public void boatStatus(){ //checks whether the boat has been sunk or not
        boolean reconstruct = false;

        for (int i =0; i<boats.length; i++){
            if (boats[i].updateStatus()){
                System.out.println("Boat is sunk");
                reconstruct = true;
            }
        }
        if (reconstruct){
            int numBoats = 0;
            for (int i = 0; i < boats.length; i++){
                if (!boats[i].getStatus()) numBoats++;
            }
            Boat[] newBoatArray = new Boat[numBoats];

            for (int i = 0; i < boats.length; i++){
                if (!boats[i].getStatus()){
                    for (int j = 0; j < newBoatArray.length; j++){
                        if (newBoatArray[j] == null) newBoatArray[j] = boats[i];
                    }
                }
            }
            boats = newBoatArray;
            boatsRemaining = boats.length;
        }
    }

    public void display(){ //displays the board after every turn
        String str = "";

        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard[0].length; j++){
                if (j == 0) str += "\n";
                if (gameBoard[i][j].getStatus() == 'H' || gameBoard[i][j].getStatus() == 'M') str += "[" + gameBoard[i][j].getStatus() + "]";
                else str += "[ ]";
            }
        }
        System.out.println(str);
    }

    public void print(){ //prints out the fully revealed board if a player types in the print command (This would be used for debugging purposes)
        String str = "";

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (j == 0) str += "\n";
                str += "[" + gameBoard[i][j].getStatus() + "]";
            }
        }
        System.out.println(str);
    }

    public void missile(int row, int col){ //given idea on how to start missile method at office hours
        Cell[] missileArray = new Cell[9];
        for (int i = 0; i < missileArray.length; i++){
            missileArray[i] = gameBoard[row][col];
        }
        if (row != 0) {
            missileArray[1] = gameBoard[row - 1][col];
            if (col != 0) {
                missileArray[3] = gameBoard[row][col - 1];
                missileArray[0] = gameBoard[row - 1][col - 1];
                if (row != gameBoard.length - 1) {
                    missileArray[7] = gameBoard[row + 1][col];
                    missileArray[6] = gameBoard[row + 1][col - 1];
                    if (col != gameBoard[0].length - 1) {
                        missileArray[5] = gameBoard[row][col + 1];
                        missileArray[2] = gameBoard[row - 1][col + 1];
                        missileArray[8] = gameBoard[row + 1][col + 1];
                    }
                } else if (col != gameBoard[0].length - 1) {
                    missileArray[5] = gameBoard[row][col + 1];
                    missileArray[2] = gameBoard[row - 1][col + 1];
                }
            } else if (row != gameBoard.length - 1) {
                missileArray[7] = gameBoard[row + 1][col];
                if (col != gameBoard[0].length - 1) {
                    missileArray[5] = gameBoard[row][col + 1];
                    missileArray[2] = gameBoard[row - 1][col + 1];
                    missileArray[8] = gameBoard[row + 1][col + 1];
                }
            } else if (col != gameBoard[0].length - 1) {
                missileArray[5] = gameBoard[row][col + 1];
                missileArray[2] = gameBoard[row - 1][col + 1];
            }
        } else if (col != 0) {
            missileArray[3] = gameBoard[row][col - 1];
            if (row != gameBoard.length - 1) {
                missileArray[7] = gameBoard[row + 1][col];
                if (col != gameBoard[0].length - 1) {
                    missileArray[5] = gameBoard[row][col + 1];
                    missileArray[8] = gameBoard[row + 1][col + 1];
                }
            } else if (col != gameBoard[0].length - 1) {
                missileArray[5] = gameBoard[row][col + 1];
            }
        } else if (row != gameBoard.length - 1) {
            missileArray[7] = gameBoard[row + 1][col];
            if (col != gameBoard[0].length - 1) {
                missileArray[5] = gameBoard[row][col + 1];
                missileArray[8] = gameBoard[row + 1][col + 1];
            }
        } else if (col != gameBoard[0].length - 1) {
            missileArray[5] = gameBoard[row][col + 1];
        }

        // call fire method for each target cell of the 3x3 missile
        for (int i = 0; i < missileArray.length; i++) {
            fire(missileArray[i].getRow(), missileArray[i].getCol());
        }
    }
    public void drone(boolean direction, int index) {

        int boatsDetected = 0;

        if (direction) {

            for (int i = 0; i < gameBoard[0].length; i++) {
                if (gameBoard[index][i].getStatus() == 'b') boatsDetected++;
            }
        } else {
            for (int i = 0; i < gameBoard.length; i++) {
                if (gameBoard[i][index].getStatus() == 'b') boatsDetected++;
            }
        }
        System.out.println("Drone has scanned " + boatsDetected + " targets in the chosen area.");
    }
    public void addTurn(){
        totalTurns++;
    }
    public int getTurns(){
        return totalTurns;
    }
    public int getBoatsRemaining(){
        return boatsRemaining;
    }
    public void addShots(){
        totalShots++;
    }
    public int getTotalShots(){
        return totalShots;
    }
}
