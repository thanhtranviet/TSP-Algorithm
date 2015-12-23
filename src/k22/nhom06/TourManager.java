package k22.nhom06;

import java.util.ArrayList;
import java.util.Random;

public class TourManager {

    // Holds our cities
    private static ArrayList<City> destinationCities = new ArrayList<City>();

    // Adds a destination city
    public static void addCity(City city) {
        destinationCities.add(city);
    }
    
    // Get a city
    public static City getCity(int index){
        return destinationCities.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfCities(){
        return destinationCities.size();
    }
    
    // Get all the cities
    public static ArrayList<City> getCities(){
    	return destinationCities;
    }
    
    public static void generateCities(int n, int MAX){
    	destinationCities  = new ArrayList<>();
    	Random ran = new Random(System.currentTimeMillis());
    	for (int i =0; i < n; i++){
    		City city = new City();
    		city.x = Math.abs(ran.nextInt() % MAX);
    		city.y = Math.abs(ran.nextInt() % MAX);
    		destinationCities.add(city);
    	}
    }
}
