package k22.nhom06;

import java.util.ArrayList;

public class BnBAlgorithm {
    int sourceCity;
    Tour best;
    
    ArrayList<Integer> initialRoute, optimumRoute;
    int nodes = 0;
    double routeCost = 0;
    double optimumCost = Double.MAX_VALUE;    
    private double[][] cost;
    public long executionTime;
    
    public BnBAlgorithm() {
    	best = new Tour();
    }
    
    public Population solve (Tour source) {
    	long start = System.currentTimeMillis();
    	Population population = new Population(1, false);
        initialRoute = new ArrayList<>();
        initialRoute.add(sourceCity);
        optimumRoute = new ArrayList<>();
        cost = calculateInputArray(source);
        this.sourceCity = 0;
        nodes++;
        search(source, sourceCity, initialRoute);
        int n = best.tourSize();
        for(int i=0;i<n;i++){
			best.setCity(i,source.getCity(optimumRoute.get(i)));
		}
        population.saveTour(0, best);
        long end = System.currentTimeMillis();
		this.executionTime = end - start;
		return population;  
    }
    private double[][] calculateInputArray(Tour source) {
		int n = source.tourSize();
		double[][] inputArr = new double[n][n];
		for(int i=0; i< n;i++){
			for(int j=0;j<n;j++){
				inputArr[i][j] = source.getCity(i).distanceTo(source.getCity(j));
			}
		}
		return inputArr;
	}
    public void search (Tour source, int from, ArrayList<Integer> followedRoute) {
        
        // we've found a new solution
        if (followedRoute.size() == best.tourSize()) {
            
            followedRoute.add(sourceCity);
            nodes++;
            
            // update the route's cost
            routeCost += cost[sourceCity][from];// source.getCity(sourceCity).distanceTo(source.getCity(from));
            
            if (routeCost < optimumCost) {
                optimumCost = routeCost;
                optimumRoute = (ArrayList<Integer>)followedRoute.clone();
            }
           
            // update the route's cost (back to the previous value)
            routeCost -= cost[sourceCity][from];//source.getCity(sourceCity).distanceTo(source.getCity(from));
        }
        else {
            for (int to=0; to<best.tourSize(); to++){
                if (!followedRoute.contains(to)) {
                    
                    // update the route's cost
                    routeCost += cost[from][to];// source.getCity(from).distanceTo(source.getCity(to));
                    
                    if (routeCost < optimumCost) { 
                        ArrayList<Integer> increasedRoute = (ArrayList)followedRoute.clone();
                        increasedRoute.add(to);
                        nodes++;
                        search(source, to, increasedRoute);    
                    }
                    
                    // update the route's cost (back to the previous value)
                    routeCost -= cost[from][to];//source.getCity(from).distanceTo(source.getCity(to));
                }
            }
        }
        
    }
}
