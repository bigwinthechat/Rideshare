import java.util.ArrayList;

public class Station {
    private int stationNumber;
    private ArrayList<Passenger> people;

    public Station(int stationNumber) {
        this.stationNumber = stationNumber;
        this.people = new ArrayList<>();
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public ArrayList<Passenger> getPeople() {
        return people;
    }

    public void spawnPerson(Passenger passenger) {
        people.add(passenger);
    }

    // Method to remove a passenger from the station
    public void removePerson(Passenger passenger) {
        people.remove(passenger);
    }

    // Overloaded method to remove a passenger from the station by index
    public void removePerson(int index) {
        people.remove(index);
    }
}