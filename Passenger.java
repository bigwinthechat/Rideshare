public class Passenger {
    //fields
    private int start;
    private int destination;
    private boolean direction;
    private int ID;
    private static int globalID = 0;

    public Passenger(int myDestination){
        destination = myDestination;
    }
    
    public Passenger(int myStart, int myDestination){
        start = myStart;
        destination = myDestination;
        ID = globalID++;
    }
    
    
    public int getStart(){
            return start;
        }
    
    public int getDestination(){
            return destination;
        }
    
    public int getID(){
            return ID;
        }
    
    public boolean getDirection(){
            return direction;
        }
    public String toString() {
            return "Dest: " + destination + " Loc: " + start;
    }

    }
