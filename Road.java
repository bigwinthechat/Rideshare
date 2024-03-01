import java.util.ArrayList;
public class Road{
    private static int miles;
    private static int revenue;
    private static ArrayList<Station> stations;
    private static ArrayList<Car> cars;


    public static void addStations(){
        for(int i = 0; i < 32; i++){
            stations.add(new Station(i));
        }
    }
    public static void addCars(int num) {
        for (int i = 0; i < num; i++) {
            int startStation = (int) (Math.random() * stations.size()); // Generate random start station index
            int destinationStation = (int) (Math.random() * stations.size()); // Generate random destination station index
            cars.add(new Car(startStation, destinationStation));
        }
        for (int j = 0; j < cars.size(); j++) {
            for (int k = 0; k < stations.size(); k++) {
                if (stations.get(k).getStationNumber() == cars.get(j).getInitialStation()) {
                    stations.get(k).addCar(cars.get(j));
                }
            }
        }
    }
    public static void addPassengers(int num){
        for(int i = 0; i < num; i++){
            int s = (int)(Math.random()*32);
            stations.get(s).boardTrain(new Passenger(s, (int)(Math.random()*32)));
         }
    }
    public static Station stationWithID(int ID){
        for(Station s : stations){
        if(s.getStationNumber()==ID){
            return s;
            }
        }
        return null;
    }

    public static void moveAll() {
        ArrayList<Car> toMoveTrue = new ArrayList<Car>();
        ArrayList<Car> toMoveFalse = new ArrayList<Car>();
        for (int i = 0; i < stations.size(); i++) {
            for (int j = 0; j < stations.get(i).getCars().size(); j++) {
                Car currentCar = stations.get(i).getCars().get(j);
                if (currentCar.getMoveable()) {
                    currentCar.move();
                    currentCar.changeMovebility(false);
                    if (currentCar.getDirection()) {
                        toMoveTrue.add(currentCar);
                    } else {
                        toMoveFalse.add(currentCar);
                    }
                }
            }
        }
        // Handle moving cars to previous stations
        for (Car c : toMoveTrue) {
            int previousStationIndex = c.getStationNumber() - 1;
            if (previousStationIndex >= 0 && previousStationIndex < stations.size()) {
                stations.get(previousStationIndex).removeCar(c);
                miles++;
                revenue += c.pickup().size();
                stations.get(previousStationIndex).addCar(c); // Move the car to the previous station
            } else {
                System.out.println("Invalid previous station index");
                System.out.println(c.getDestination());
            }
        }
        // Handle moving cars to next stations
        for (Car c : toMoveFalse) {
            int nextStationIndex = c.getStationNumber() + 1;
            if (nextStationIndex >= 0 && nextStationIndex < stations.size()) {
                stations.get(nextStationIndex).removeCar(c);
                miles++;
                revenue += c.pickup().size();
                stations.get(nextStationIndex).addCar(c); // Move the car to the next station
            } else {
                System.out.println("Invalid next station index");
                System.out.println(c.getDestination());
            }
        }

        ArrayList<Passenger> aboutToDropOffCompleted = new ArrayList<Passenger>();
        for(Station s : stations){
            for(Car c : s.getCompletedCars()){
                for(Passenger p : c.getPeople()){
                aboutToDropOffCompleted.add(p);
            }
        }
        }
        for(Passenger p : aboutToDropOffCompleted){
        stationWithID(carHoldingPassenger(p).getStationNumber()).unloadPassengers(carHoldingPassenger(p),p);
    }

        //dropoff passengers at their destination
        ArrayList<Passenger> aboutToDropOff = new ArrayList<Passenger>();
        for(Station s : stations){
            for(Car c : s.getCars()){
                for(Passenger p : c.getPeople())
                if(p.getDestination()==s.getStationNumber()){
                    aboutToDropOff.add(p);
                }

            }
        }
        for(Passenger p : aboutToDropOff){
            stationWithID(carHoldingPassenger(p).getStationNumber()).unloadPassengers(carHoldingPassenger(p),p);
        }
         //boarding new passengers
        board();
         //get all cars ready to move again
        for(Station s : stations){
            for(Car c : s.getCars()){
                c.changeMovebility(true);
            }
        }

    }
    public static void board(){
        for(Station s : stations){
        s.boardTrain(null);
        }
    }

    public static Car carHoldingPassenger(Passenger p) {
        for (Car c : cars) {
            for (Passenger person : c.getPeople()) {
                if (person.getID() == p.getID()) {
                    return c; 
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        miles = 0;
        revenue = 0;
        int numcars = 20;
        int numpassengers = 50;
        stations = new ArrayList<Station>();
        cars = new ArrayList<Car>();
        System.out.println("Running first simulation");
        System.out.println("Adding stations:");
        addStations();
        addCars(numcars);
        addPassengers(numpassengers);
        board();
        System.out.println(stations.toString());
        System.out.println("Moving cars:");
        for(int i = 0; i < 33; i++){
            moveAll();
        }
        System.out.println(stations.toString());
        System.out.println("Miles: " + miles + " revenue: " + revenue);
        System.out.println("Revenue per mile: " + (double)revenue/miles);
    }
}