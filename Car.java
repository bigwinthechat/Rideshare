import java.util.ArrayList;

public class Car {
    // Fields
    private ArrayList<Passenger> people;
    private int destination;
    private int initialStation;
    private boolean forward;
    private int currentStation;
    private boolean moveable;
    private int revenue;

    // Constructor
    public Car(int myInitialStation, int myDestination) {
        destination = myDestination;
        initialStation = myInitialStation;
        people = new ArrayList<Passenger>();
        currentStation = myInitialStation;
        
        // Randomize destination and current station
        destination = (int) (Math.random() * 32);
        currentStation = (int) (Math.random() * 32);

        // Set forward direction based on initial and destination stations
        forward = initialStation < destination;
    }

    // Mutator methods
    public void changeMoveability(boolean status) {
        moveable = status;
    }

    public Passenger dropoff(int pos) {
        if (pos != -1) {
            Passenger temp = people.get(pos);
            people.remove(pos);
            return temp;
        }
        return null;
    }

    public void remove(int p) {
        people.remove(p);
    }

    public void pickup(Passenger p) {
        if (people.size() <= 3) {
            people.add(p);
        }
    }

    // Getter methods
    public int getInitialStation() {
        return initialStation;
    }

    public boolean getMoveable() {
        return moveable;
    }

    public ArrayList<Passenger> getPeople() {
        return people;
    }

    public int getStationNumber() {
        return currentStation;
    }

    public void setStationNumber(int stationNumber) {
        currentStation = stationNumber;
    }

    public int getDestination() {
        return destination;
    }

    // Move method
    public void move() {
        if (destination != currentStation) {
            if (forward) {
                currentStation++;
            } else {
                currentStation--;
            }
        }

        // Calculate revenue
        revenue += people.size();
    }

    // Getter for direction
    public boolean getDirection() {
        return forward;
    }
}
