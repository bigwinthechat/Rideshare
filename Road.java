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
            int destinationStation = (int) (Math.random() * stations.size());
            cars.add(new Car(startStation, destinationStation));
        }
    }

    public static void addPassengers(int num) {
        for (int i = 0; i < num; i++) {
            int s = (int) (Math.random() * 32);
            stations.get(s).spawnPerson((int) (Math.random() * 32));
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

    public static void moveAll() {
        ArrayList<Car> toMoveTrue = new ArrayList<>();
        ArrayList<Car> toMoveFalse = new ArrayList<>();

        for (Station station : stations) {
            ArrayList<Car> stationCars = new ArrayList<>();
            for (Car currentCar : cars) {
                if (currentCar.getStationNumber() == station.getStationNumber() && currentCar.getMoveable()) {
                    currentCar.move();
                    currentCar.changeMoveability(false);
                    if (currentCar.getDirection()) {
                        toMoveTrue.add(currentCar);
                    } else {
                        toMoveFalse.add(currentCar);
                    }
                }
            }
        }

        for (Car c : toMoveTrue) {
            int previousStationIndex = c.getStationNumber() - 1;
            if (previousStationIndex >= 0 && previousStationIndex < stations.size()) {
                miles++;
                revenue += c.getPeople().size();
                c.setStationNumber(previousStationIndex);
            } else {
                System.out.println("Invalid previous station index");
                System.out.println(c.getDestination());
            }
        }

        for (Car c : toMoveFalse) {
            int nextStationIndex = c.getStationNumber() + 1;
            if (nextStationIndex >= 0 && nextStationIndex < stations.size()) {
                miles++;
                revenue += c.getPeople().size();
                c.setStationNumber(nextStationIndex);
            } else {
                System.out.println("Invalid next station index");
                System.out.println(c.getDestination());
            }
        }

        for (Station s : stations) {
            boardTrain();
        }

        for (Car c : cars) {
            c.changeMoveability(true);
        }
    }

    public static void board() {
        for (Station s : stations) {
            boardTrain();
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
        System.out.println("Running first simulation");
        System.out.println("Stations:");
        addStations();
        addCars(numcars);
        addPassengers(numpassengers);
        board();
        System.out.println(stations.toString());
        System.out.println("Moving cars:");
        for (int i = 0; i < 33; i++) {
            moveAll();
        }
        System.out.println(stations.toString());
        System.out.println("Miles: " + miles + " revenue: " + revenue);
        System.out.println("Revenue per mile: " + (double) revenue / miles);
    }
}
