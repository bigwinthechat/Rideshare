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
    private int distanceTraveled;

    // Constructor
    public Car(int myInitialStation, int myDestination) {
        initialStation = myInitialStation;
        destination = myDestination;
        people = new ArrayList<>();
        currentStation = myInitialStation;
        
        // Set forward direction based on initial and destination stations
        forward = initialStation < destination;
    
        // Set moveability to true initially
        moveable = true;
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

    public void remove(Passenger p) {
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

    public boolean isAtDestination() {
        return currentStation == destination;
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
        if (currentStation < destination) {
            currentStation++;
        } else if (currentStation > destination) {
            currentStation--;
        }
        // Update distance traveled
        distanceTraveled++;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    // Getter for direction
    public boolean getDirection() {
        return forward;
    }

    public String toString(){
        return "Initial station: " + initialStation + "Current station: " + currentStation + "Number of People: " + people.size();
    }
}