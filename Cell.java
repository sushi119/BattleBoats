public class Cell {

    private int row, col;
    private char status; //'-': not been guess and no boat, 'B': boat is there but not guessed, 'H': boat has been hit, 'M': boat has been missed

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        status = '-'; //"has not been guessed and no boat present"
    }
    public void setStatus(char s){
        status = s;
    }
    public char getStatus(){
        return status;
    }
    public void setRow(int r){
        row = r;
    }
    public int getRow(){
        return row;
    }
    public void setCol(int c){
        col = c;
    }
    public int getCol(){
        return col;
    }
}


