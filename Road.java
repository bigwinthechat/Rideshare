import java.util.Scanner;
import java.util.ArrayList;

public class Road{

    private int numofcars;
    private int numofpassengers;
    private int miles;
    private double totalRevenue;

    private ArrayList<Car> cars;
    private ArrayList<Passenger> people;

    public Road(int myNumofcars, int myNumofpassengers){
        numofcars = myNumofcars;
        numofpassengers = myNumofpassengers;
    }
}