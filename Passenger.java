public class Passenger {
    //fields
    private int location;
    private int destination;
    private boolean direction;
    private int ID;
    private static int globalID = 0;
    private int originalDestination;
    private int startStation;

    public Passenger(int startStation, int destination, int originalDestination) {
        this.startStation = startStation;
        this.destination = destination;
        this.originalDestination = originalDestination;
        ID = globalID++;
    }
    
    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }
    
    public int getStart(){
            return startStation;
        }
    
    public int getStartStation() {
            return startStation;
        }
    
    public int getDestination() {
            return destination;
        }
    
    public int getOriginalDestination() {
            return originalDestination;
        }
    
    public int getID(){
            return ID;
        }
    
    public boolean getDirection(){
            return direction;
        }

    public String toString() {
            return "Dest: " + destination + " Loc: " + location;
    }

    }
