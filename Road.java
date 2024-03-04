import java.util.ArrayList;
import java.util.Random;

public class Road {
    private int miles;
    private int revenue;
    private ArrayList<Station> stations;
    private ArrayList<Car> cars;
    private Random random;

    public void addStations() {
        this.stations = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            stations.add(new Station(i));
        }
    }

    public Road() {
        this.stations = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.random = new Random();
        
    }

    public void addCars(int num) {
        for (int i = 0; i < num; i++) {
            int startStation = random.nextInt(32);
            int destinationStation;
            do {
                destinationStation = random.nextInt(32);
            } while (destinationStation == startStation || (destinationStation != 0 && destinationStation != 31));
            cars.add(new Car(startStation, destinationStation));
        }
    }

    public void addPassengers(int num) {
        for (int i = 0; i < num; i++) {
            int startStation = (int) (Math.random() * 32);
            int destinationStation = (int) (Math.random() * 32);
            Passenger passenger = new Passenger(startStation, destinationStation, destinationStation);
            stations.get(startStation).spawnPerson(passenger);
            passenger.setLocation(startStation); // Set the passenger's current location to the start station
        }
    }
    

    public Station stationWithID(int ID) {
        for (Station s : stations) {
            if (s.getStationNumber() == ID) {
                return s;
            }
        }
        return null;
    }

    
    public void boardTrain() {
        // Iterate through each station
        for (Station station : stations) {
            // Get the list of passengers waiting at the station
            ArrayList<Passenger> passengers = station.getPeople();
            // Separate passengers based on their destination direction
            ArrayList<Passenger> passengersUp = new ArrayList<>();
            ArrayList<Passenger> passengersDown = new ArrayList<>();
    
            // Categorize passengers as going up or down
            for (Passenger passenger : passengers) {
                if (passenger.getDestination() > station.getStationNumber()) {
                    passengersUp.add(passenger);
                } else {
                    passengersDown.add(passenger);
                }
            }
    
            // Board passengers onto cars at the current station
            for (Car car : cars) {
                // Check if the car is at the current station and going up
                if ((car.getStationNumber() == station.getStationNumber()) && car.getDirection()) {
                    // Output message indicating boarding direction
                    System.out.println("Boarding passengers onto car at station " + station.getStationNumber() + " going up:");
                    // Call method to pick up passengers going up
                    pickupPassengers(car, passengersUp);
                } 
                // Check if the car is at the current station and going down
                else if ((car.getStationNumber() == station.getStationNumber()) && !car.getDirection()) {
                    // Output message indicating boarding direction
                    System.out.println("Boarding passengers onto car at station " + station.getStationNumber() + " going down:");
                    // Call method to pick up passengers going down
                    pickupPassengers(car, passengersDown);
                }
            }
        }
    }
    
    
    private void pickupPassengers(Car car, ArrayList<Passenger> stationPassengers) {
        ArrayList<Passenger> passengersToRemove = new ArrayList<>();
        for (Passenger passenger : stationPassengers) {
            if (car.getPeople().size() < 4 && passenger.getOriginalDestination() == car.getDestination()) {
                car.pickup(passenger);
                passengersToRemove.add(passenger);
            }
        }
        stationPassengers.removeAll(passengersToRemove);
    }
    
    
    
    public boolean carIsAtDestination(Car car) {
        return car.getStationNumber() == car.getDestination();
    }

    public void deleteCar(Car car) {
        cars.remove(car);
    }

    public boolean anyPassengerInTransit() {
        for (Car car : cars) {
            for (Passenger passenger : car.getPeople()) {
                if (passenger.getDestination() != car.getStationNumber()) {
                    return true; // At least one passenger is still in transit
                }
            }
        }
        return false; // No passengers are in transit
    }

    public void moveAll(int farePerMile) {
        // Create a list to store cars to be removed
        ArrayList<Car> carsToRemove = new ArrayList<>();
        
        // Loop through all cars
        for (Car car : cars) {
            // Move the car
            car.move();
            // Update total miles traveled
            miles += car.getDistanceTraveled();
            
            // Check if the car has reached its destination
            if (car.isAtDestination()) {
                // Drop off passengers at the destination station
                dropoff(car);
                // Add the car to the removal list
                carsToRemove.add(car);
            } else {
                // Get the current station of the car
                Station currentStation = stations.get(car.getStationNumber());
                
                // Remove passengers who have reached their original destination
                ArrayList<Passenger> carPassengers = car.getPeople();
                ArrayList<Passenger> passengersToRemove = new ArrayList<>();
                for (Passenger passenger : carPassengers) {
                    passenger.setLocation(car.getStationNumber());
                    if (passenger.getOriginalDestination() == car.getStationNumber()) {
                        passengersToRemove.add(passenger);
                    }
                }
                for (Passenger passenger : passengersToRemove) {
                    car.remove(passenger);
                    currentStation.spawnPerson(passenger);
                    System.out.println("Passenger " + passenger + " dropped off at station " + currentStation.getStationNumber());
                }
                
                // Pick up passengers waiting at the current station
                ArrayList<Passenger> stationPassengers = currentStation.getPeople();
                ArrayList<Passenger> passengersToPickup = new ArrayList<>();
                for (Passenger passenger : stationPassengers) {
                    if (!passengerInCar(passenger) && car.getPeople().size() < 4 && !passengerHasBeenDroppedOff(passenger, car.getStationNumber()) && passenger.getLocation() != passenger.getDestination()) {
                        passengersToPickup.add(passenger);
                    }
                }
                for (Passenger passenger : passengersToPickup) {
                    car.pickup(passenger);
                    currentStation.removePerson(passenger);
                    System.out.println("Passenger " + passenger + " picked up by car at station " + currentStation.getStationNumber());
                }
            }
        }
        
        // Remove the cars that need to be removed
        cars.removeAll(carsToRemove);
        
        // Calculate revenue based on the fare per mile
        calculateRevenue(farePerMile);
    }

    
    
    private boolean passengerHasBeenDroppedOff(Passenger passenger, int stationNumber) {
        return passenger.getOriginalDestination() == stationNumber && passenger.getLocation() != stationNumber;
    }

    public boolean passengerInCar(Passenger passenger) {
        for (Car car : cars) {
            for (Passenger p : car.getPeople()) {
                if (p.getID() == passenger.getID()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Car findAvailableCar(int stationNumber) {
        for (Car car : cars) {
            if (car.getStationNumber() == stationNumber && car.getPeople().size() < 4) {
                return car;
            }
        }
        return null;
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
            if (passenger.getOriginalDestination() == car.getStationNumber()) {
                passenger.setLocation(passenger.getOriginalDestination()); // Update passenger's location to their original destination
                passengersToRemove.add(passenger);
            }
        }
    
        for (Passenger passenger : passengersToRemove) {
            car.remove(passenger);
            currentStation.spawnPerson(passenger); // Add the passenger back to the station
        }
    }
    
    

    public void board() {
        System.out.println("Boarding passengers...");
        for (Station s : stations) {
            boardTrain();
        }
        System.out.println("Passengers boarded.");
    }

    public Car carHoldingPassenger(Passenger p) {
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
                }
            }
        }
    }

    public void calculateRevenue(int farePerMile) {
        for (Car car : cars) {
            revenue += car.getDistanceTraveled() * farePerMile * car.getPeople().size();
        }
    }
    
    public static void main(String[] args) {
        int numCars = 20;
        int numPassengers = 50;
        int farePerMile = 10; // Set the fare per mile
    
        Road road = new Road();
        road.addStations();
        road.addCars(numCars);
        road.addPassengers(numPassengers);
    
        System.out.println("START");
    
        System.out.println("Cars added: " + road.getCars().size());
    
        System.out.println("Passengers added: " + numPassengers);
    
        System.out.println("Initial:");
        for (Station station : road.getStations()) {
            System.out.println(station + ": ");
            for (Passenger passenger : station.getPeople()) {
                System.out.println("[Passenger: " + passenger.toString() + "]");
            }
            System.out.println();
        }
    
        road.board();
        System.out.println("Moving cars:");
        while (road.getCars().isEmpty() == false) {
            road.moveAll(farePerMile);
        }

    
        System.out.println("Output:");
        System.out.println("Miles traveled: " + road.getMiles());
        road.calculateRevenue(farePerMile); // Calculate revenue after all cars have moved
        System.out.println("Revenue generated: " + road.getRevenue());
        if (road.getMiles() != 0) {
            System.out.println("Revenue per mile: " + road.getRevenue() / road.getMiles());
        } else {
            System.out.println("No miles traveled, cannot calculate revenue per mile.");
        }
    }
}