import java.util.ArrayList;

public class Road{
    private static int miles;
    private static double totalRevenue;

    private ArrayList<Car> cars;
    private ArrayList<Passenger> people;


    public static void draw(Station[] stations) {
        System.out.println("Running the Rideshare simulation!");
    
        for (Station station : stations) {
            System.out.print("Station " + (station.getStationNumber() + 1));
            System.out.print("[Passengers: ");
    
            for (Passenger person : station.getPeople()) {
                if (person.getStart() == station.getStationNumber()) {
                    System.out.print(person.toString() + " ");
                }
            }
            System.out.print("]");
    
            for (Car car : station.getCars()) {
                if (car.getStationNumber() == station.getStationNumber()) {
                    System.out.print(" [Car " + car.getDestination() + ": ");
                    
                    for (Passenger person : car.getPeople()) {
                        System.out.print("Passenger" + person.getID() + "Destination" + person.getDestination());
                    }
                    
                    System.out.print("]");
                }
            }
    
            System.out.println();
        }
    
        System.out.println("Miles driven: " + miles);
        System.out.println("Total revenue: $" + totalRevenue);
        System.out.println("Average revenue per mile: $" + (totalRevenue / miles));
    }

    public static void moveCars(Station[] stations){
        for (int i = 0; i < stations.length; i++){
            Station station = stations[i];
            for (int n = station.getCars().size() - 1; n >= 0; n--) {
                Car car = station.getCars().get(n);
                int nextStationIndex;
                if (car.getDestination() > station.getStationNumber()) {
                    nextStationIndex = i + 1; // Move to the next station
                } else {
                    nextStationIndex = i - 1; // Move to the previous station
                }
                        Station nextStation = stations[nextStationIndex];
                        nextStation.addCar(car);
                        station.removeCar(n);
                        int distance = Math.abs(nextStation.getStationNumber() - station.getStationNumber());
                        miles += distance;
                        totalRevenue += car.getPeople().size();

                        if (nextStationIndex == 4) {
                            System.out.println("Car " + car.getID() + " has reached station 5 and is removed.");
                            totalRevenue += car.getPeople().size() * 5; // Add revenue for the full distance to station 5
                        }
                 }
            }
        }








    public static void main(String[] args){
        miles = 0;
        totalRevenue = 0;

        Station[] stations = new Station[5];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = new Station(i);

            int numPassengers = (int) (Math.random() * 3);
            for (int n = 0; n < numPassengers; n++){
                stations[i].spawnPerson((int) (Math.random() * 5));
            }

            int numCars = (int) (Math.random() * 2);
            for (int n = 0; n < numCars; n++){
                stations[i].spawnCar((int) (Math.random() * 5));
            }
        }

        while (true){
            draw(stations);
            moveCars(stations);

            for (Station station : stations){
                station.boardTrain();
                station.unloadPassengers();
                station.checkCars();
            }
        }
    }
}