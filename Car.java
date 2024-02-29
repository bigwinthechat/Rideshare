import java.util.ArrayList;

public class Car {
    //fields
    private ArrayList<Passenger> people;
    private int destination;
    private int initialStation;
    private int currentStation;
    private boolean moveable;
    private int ID;
    private boolean directionforward;
    private static int globalID = 0;
    private int revenue;

    //constructors
    public Car(){
        ID = globalID++;
        people = new ArrayList<Passenger>();
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
        revenue+=people.size();
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
    public int getID(){
        return ID;
    }

}
