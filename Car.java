import java.util.ArrayList;

public class Car {
    //fields
    private ArrayList<Passenger> people;
    private int destination;
    private int initialStation;
    private boolean forward;
    private int currentStation;
    private boolean moveable;
    private int revenue;

    //constructors
    public Car(int myInitialStation, int myDestination){
        destination = myDestination;
        initialStation = myInitialStation;
        people = new ArrayList<Passenger>();
        
        int Destination = (int) (Math.random()*32);
        destination = Destination;

        int Start = (int) (Math.random()*32);
        currentStation = Start;
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

	public void move(){
        if(destination != currentStation){
            if(forward){
                currentStation++;
            } else {
                currentStation--;
            }
        }

        //calculate revenue
        revenue+=people.size();
    }


}
