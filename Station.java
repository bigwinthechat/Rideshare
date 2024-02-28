import java.util.ArrayList;

public class Station{
    
    //fields
    
    private ArrayList<Passenger> people;    
    private ArrayList<Car> cars;
    private int stationNumber;

    //
    public Station(int myStationNumber)
    {
        people = new ArrayList<Passenger>();
        cars = new ArrayList<Car>();
        stationNumber = myStationNumber;
    }
}