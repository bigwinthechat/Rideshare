import java.util.ArrayList;

public class Car {
    //fields
    private ArrayList<Passenger> people;
    private int destination;
    private int initialStation;
    private int currentStation;
    private boolean moveable;

    //constructors
    public Car(int myInitialStation, int myDestination){
        destination = myDestination;
        initialStation = myInitialStation;
    }

    //mutators
    public void addPerson(Passenger human){
        if(people.size() < 3){
            people.add(human);
        }else{
        }
    }

    public void removePerson(int index) {
        people.remove(index);
    }

    public void moveStations(){
        currentStation++;
    }

    //getters
    public ArrayList<Passenger> getPeople(){
        return people;
    }
    public int getStationNumber(){
        return currentStation;
    }
    public int getDestination(){
        return destination;
    }

}
