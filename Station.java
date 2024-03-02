import java.util.ArrayList;

public class Station {
    private ArrayList<Passenger> people;
    private int stationNumber;

    public Station(int myStationNumber) {
        stationNumber = myStationNumber;
        people = new ArrayList<>();
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void spawnPerson(Passenger passenger) {
        people.add(passenger);
    }

    public ArrayList<Passenger> getPeople() {
        return people;
    }

    public void addPerson(Passenger person) {
        people.add(person);
    }

    public void removePerson(int index) {
        people.remove(index);
    }

}