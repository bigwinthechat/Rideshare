import java.util.ArrayList;

public class Station{
    
    //fields
    
    private ArrayList<Passenger> people;    
    private ArrayList<Car> cars;
    private int stationNumber;

    //
    public Station(int stationNumber)
    {
        people = new ArrayList<Passenger>();
        cars = new ArrayList<Car>();
        this.stationNumber = stationNumber;
    }
}