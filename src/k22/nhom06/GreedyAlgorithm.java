package k22.nhom06;

public class GreedyAlgorithm {
	public static long executionTime;
	public static Population solve(Tour source){
		long start = System.currentTimeMillis();
		Population population = new Population(source.tourSize(), false);
		
		for(int i = 0; i< source.tourSize(); i++){
			Tour tour = new Tour();
			int position = 0;
			City currentCity = source.getCity(i);
			tour.setCity(position, currentCity);
			
			for(int k = 1; k < source.tourSize(); k++){
				double min_distance = Double.MAX_VALUE;
				int next_position = 0;
				currentCity = tour.getCity(position);
				for(int j = 0; j < source.tourSize(); j++){
					City city = source.getCity(j);
					if(!tour.containsCity(city)){
						double distance = currentCity.distanceTo(city);
						if(distance < min_distance){
							min_distance = distance;
							next_position = j;
						}
					}
					
				}
				position++;
				tour.setCity(position,source.getCity(next_position));
			}
			population.saveTour(i, tour);
		}
		long end = System.currentTimeMillis();
		executionTime = end - start;
		return population;
	}
	
}
