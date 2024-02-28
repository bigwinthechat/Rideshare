import java.util.ArrayList;

public class Station{
    
    //fields
    
    private ArrayList<Passenger> people;    
    private ArrayList<Car> cars;
    private int stationNumber;

    //constructor
    public Station(int myStationNumber){
        people = new ArrayList<Passenger>();
        cars = new ArrayList<Car>();
        stationNumber = myStationNumber;
    }
    public ArrayList<Car> getCars(){
        return cars;
    }
    public int getStationNumber(){
        return stationNumber;
    }

    public void boardTrain(){
        for(int i = people.size()
    }
}