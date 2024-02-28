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
}
