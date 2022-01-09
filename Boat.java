public class Boat {

    private int size; //length of the boat
    private boolean orientation; //whether boat is vertical or horizontal
    private Cell[] location; //corresponding spot of the boat on the board
    private boolean sunk;

    public Boat(int size, boolean orientation, Cell[] location){
        this.size = size;
        this.orientation = orientation;
        this.location = location;
    }
    public void setSize(int s){
        size = s;
    }
    public int getSize(){
        return size;
    }
    public void setOrientation(boolean o){
        orientation = o;
    }
    public boolean getOrientation(){
        return orientation;
    }
    public void setLocation(Cell[] l){
        location = l;
    }
    public Cell[] getLocation(){
        return location;
    }
    public boolean updateStatus(){
        if (!sunk){ //checking to see whether boat is sunk or not
            boolean status = true;
            for (int i = 0; i < location.length; i++){
                if (location[i].getStatus() != 'H'){
                    status = false;
                    break;
                }
            }
            if (status) sunk = true;
            return status;
        }
        return false;
    }
    public boolean getStatus(){
        return sunk;
    }
}
