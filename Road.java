import java.util.ArrayList;

public class Road {
    private static int miles;
    private static int revenue;
    private static ArrayList<Station> stations;
    private static ArrayList<Car> cars;

    public static void addStations() {
        stations = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            stations.add(new Station(i));
        }
    }

    public static void addCars(int num) {
        cars = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int startStation = (int) (Math.random() * stations.size());
            int destinationStation;
            do {
                destinationStation = (int) (Math.random() * 32); // Keep it within the range of 0 to 31
            } while (destinationStation == startStation || (destinationStation != 0 && destinationStation != 31)); // Ensure destination is different from start and is either 0 or 31
            cars.add(new Car(startStation, destinationStation));
        }
    }
    
    

    public static void addPassengers(int num) {
        for (int i = 0; i < num; i++) {
            int startStation = (int) (Math.random() * 32);
            int destinationStation = (int) (Math.random() * 32);
            Passenger passenger = new Passenger(startStation, destinationStation);
            stations.get(startStation).spawnPerson(passenger);
        }
    }
    

    public static Station stationWithID(int ID) {
        for (Station s : stations) {
            if (s.getStationNumber() == ID) {
                return s;
            }
        }
        return null;
    }

    public static void boardTrain() {
        for (Station station : stations) {
            ArrayList<Passenger> passengers = station.getPeople();
            ArrayList<Passenger> passengersUp = new ArrayList<>();
            ArrayList<Passenger> passengersDown = new ArrayList<>();
    
            for (Passenger passenger : passengers) {
                if (passenger.getDestination() > station.getStationNumber()) {
                    passengersUp.add(passenger);
                } else {
                    passengersDown.add(passenger);
                }
            }
    
            for (Car car : cars) {
                if ((car.getStationNumber() == station.getStationNumber()) && car.getDirection()) {
                    ArrayList<Passenger> carPassengers = car.getPeople();
                    pickupPassengers(car, carPassengers, passengersUp);
                } else if ((car.getStationNumber() == station.getStationNumber()) && !car.getDirection()) {
                    ArrayList<Passenger> carPassengers = car.getPeople();
                    pickupPassengers(car, carPassengers, passengersDown);
                }
            }
        }
    }

    

    private static void pickupPassengers(Car car, ArrayList<Passenger> carPassengers, ArrayList<Passenger> stationPassengers) {
        for (int i = 0; i < stationPassengers.size(); i++) {
            if (carPassengers.size() < 4) {
                car.pickup(stationPassengers.get(i));
                stationPassengers.remove(i);
                i--;
            }
        }
    }

    public boolean carIsAtDestination(Car car) {
        return car.getStationNumber() == car.getDestination();
    }
    
    public void deleteCar(Car car) {
        cars.remove(car);
    }

    public static boolean anyPassengerInTransit() {
        for (Car car : cars) {
            for (Passenger passenger : car.getPeople()) {
                if (passenger.getDestination() != car.getStationNumber()) {
                    return true; // At least one passenger is still in transit
                }
            }
        }
        return false; // No passengers are in transit
    }

    public void moveAll() {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            car.move();
            
            // Update total miles traveled
            miles += car.getDistanceTraveled();
    
            if (car.isAtDestination() && car.getPeople().isEmpty()) {
                cars.remove(i);
                i--;
            } else {
                dropoff(car);
            }
        }
        putPassengersInCars();
    }
    
    private void removeCar(Car car) {
        cars.remove(car);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public int getMiles() {
        return miles;
    }

    public int getRevenue() {
        return revenue;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void dropoff(Car car) {
        Station currentStation = stations.get(car.getStationNumber());
        ArrayList<Passenger> carPassengers = car.getPeople();
        ArrayList<Passenger> passengersToRemove = new ArrayList<>();
    
        for (Passenger passenger : carPassengers) {
            if (passenger.getDestination() == car.getStationNumber()) {
                passengersToRemove.add(passenger);
            }
        }
    
        for (Passenger passenger : passengersToRemove) {
            car.remove(passenger);
            currentStation.spawnPerson(passenger); // Add the passenger back to the station
        }
    }

    public static void board() {
        System.out.println("Boarding passengers...");
        for (Station s : stations) {
            boardTrain();
        }
        System.out.println("Passengers boarded.");
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
    public void putPassengersInCars() {
    for (Station station : stations) {
        ArrayList<Passenger> passengers = station.getPeople();
        for (Passenger passenger : passengers) {
            Car car = carHoldingPassenger(passenger);
            if (car != null && car.getPeople().size() < 4 && car.getStationNumber() == station.getStationNumber()) {
                car.pickup(passenger);
                int index = station.getPeople().indexOf(passenger);
                if (index != -1) {
                    station.removePerson(index);
                }
            }
        }
    }
}
    
public static void main(String[] args) {
    int numCars = 20;
    int numPassengers = 50;
    ArrayList<Passenger> movedPassengers = new ArrayList<>();

    Road road = new Road(); // Create an instance of Road
    road.addStations(); // Call addStations on the instance
    road.addCars(numCars); // Call addCars on the instance
    road.addPassengers(numPassengers); // Call addPassengers on the instance

    System.out.println("START");

    System.out.println("Stations:");
    road.addStations(); // Print stations

    System.out.println("Cars:");
    road.addCars(numCars); // Print number of cars added
    System.out.println("Cars added: " + road.getCars().size());
    System.out.println("Passengers:");
    road.addPassengers(numPassengers); // Print number of passengers added

    System.out.println("Initial:");
    for (Station station : road.getStations()) { // Iterate through stations
        System.out.print(station);
        System.out.println(": ");
        for (Passenger passenger : station.getPeople()) { // Print passengers at each station
            System.out.println("[Passenger: " + passenger + "]");
        }
        System.out.println();
    }

    road.board(); // Board passengers onto cars

    System.out.println("Moving cars:");

    while(road.getCars().isEmpty() != false){
        road.moveAll();
    }

    for (Station station : road.getStations()) { // Iterate through stations after moving
        ArrayList<Passenger> stationPassengers = station.getPeople();
        movedPassengers.addAll(stationPassengers); // Add passengers to movedPassengers list
        stationPassengers.clear(); // Clear passengers from the station
    }

    System.out.println("moved");

    System.out.println("Final:");

    for (Station station : road.getStations()) { // Iterate through stations after moving
        System.out.print(station);
        System.out.println(": ");
        for (Passenger passenger : station.getPeople()) { // Print passengers at each station
            System.out.println("[Passenger: " + passenger + "]");
            movedPassengers.add(passenger); // Add the passenger to the moved passengers list
        }
        System.out.println(); // Add a new line after printing passengers for each station
    }

    System.out.println("Output");
    System.out.println("Miles traveled: " + road.getMiles());
    System.out.println("Revenue generated: " + road.getRevenue());
    if (road.getMiles() != 0) {
        System.out.println("Revenue per mile: " + road.getRevenue() / road.getMiles());
    } else {
        System.out.println("No miles traveled, cannot calculate revenue per mile.");
            }
        }
    }