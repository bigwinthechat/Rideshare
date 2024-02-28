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
        for (int n =people.size() - 1; n >= 0; n--){
            Passenger person = people.get(n);
            if (person.getDestination() > stationNumber)
            {
                int carIndex = -1;
                for (int i = 0; i < cars.size(); i++)
                {
                    Car car = cars.get(i);
                    if (car.getDestination() > stationNumber)
                    {
                        carIndex = i;
                    }
                }
        if (carIndex != -1 && cars.get(carIndex).getPersons().size() != 3)
                {
                    cars.get(carIndex).addPerson(person);
                    people.remove(j);
                }
            }
            else if (person.getDestination() < stationNumber)
            {
                int carIndex = -1;
                for (int i = 0; i < cars.size(); i++)
                {
                    Car car = cars.get(i);
                    if (car.getDestination() < stationNumber)
                    {
                        carIndex = i;
                    }
                }
                if (carIndex != -1 && cars.get(carIndex).getPersons().size() != 3)
                {
                    cars.get(carIndex).addPerson(person);
                    people.remove(j);
                }
            }
        }
    }
    public void unloadPassengers()
    {
        for (int i = 0; i < cars.size(); i++)
        {
            Car car = cars.get(i);
            for (int n = car.getPersons().size() - 1; n > -1; n--)
            {
                if (car.getPersons().get(n).getDestination() == getStationNumber())
                {
                    car.removePerson(n);
                }
            }
        }
    }
    public void checkCars()
    {
        for (int i = cars.size() - 1; i > -1; i--)
        {
            if (cars.get(i).getDestination() == getStationNumber())
            {
                despawnCar(i);
            }
        }
    }
    public void spawnPerson(int destination)
    {
        people.add(new Passenger(destination));
    }
    public void addPerson(Passenger person)
    {
        people.add(person);
    }
    public void removePerson(int index)
    {
        people.remove(index);
    }
    public ArrayList<Passenger> getPersons()
    {
        return people;
    }
    public void spawnCar(int destination)
    {
        cars.add(new Car(stationNumber, destination));
    }
    public void addCar(Car car)
    {
        cars.add(car);
    }    
    public void despawnCar(int index)
    {
        Car car = cars.get(index);
        for (int i = car.getPersons().size() - 1; i > -1; i--)
        {
            addPerson(car.getPersons().get(i));
            car.removePerson(i);
        }
        cars.remove(index);
    }
    public void removeCar(int index)
    {
        cars.remove(index);
    }
}
