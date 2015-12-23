package k22.nhom06;

import java.util.ArrayList;

import javax.crypto.spec.GCMParameterSpec;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create and add our cities
		/*
        City city = new City(60, 200);
        TourManager.addCity(city);
        City city2 = new City(180, 200);
        TourManager.addCity(city2);
        City city3 = new City(80, 180);
        TourManager.addCity(city3);
        City city4 = new City(140, 180);
        TourManager.addCity(city4);
        City city5 = new City(20, 160);
        TourManager.addCity(city5);
        City city6 = new City(100, 160);
        TourManager.addCity(city6);
        City city7 = new City(200, 160);
        TourManager.addCity(city7);
        City city8 = new City(140, 140);
        TourManager.addCity(city8);
        City city9 = new City(40, 120);
        TourManager.addCity(city9);
        City city10 = new City(100, 120);
        TourManager.addCity(city10);
        
        City city11 = new City(180, 100);
        TourManager.addCity(city11);
        City city12 = new City(60, 80);
        TourManager.addCity(city12);
        City city13 = new City(120, 80);
        TourManager.addCity(city13);
        City city14 = new City(180, 60);
        TourManager.addCity(city14);
        City city15 = new City(20, 40);
        TourManager.addCity(city15);
        
        City city16 = new City(100, 40);
        TourManager.addCity(city16);
        City city17 = new City(200, 40);
        TourManager.addCity(city17);
        
        City city18 = new City(20, 20);
        TourManager.addCity(city18);
        City city19 = new City(60, 20);
        TourManager.addCity(city19);
        City city20 = new City(160, 20);
        TourManager.addCity(city20);
         */
		
        /*
        // Initialize population
        Population popGA = new Population(1000, true);
        System.out.println("Initial distance: " + popGA.getFittest().getDistance());

        // Evolve population for 100 generations
        popGA = GeneticAlgorithm.evolvePopulation(popGA);
        int generation = 100;
        for (int i = 0; i < generation; i++) {
            popGA = GeneticAlgorithm.evolvePopulation(popGA);
            //System.out.println("Generation: " + i + " - Current distance: " + popGA.getFittest().getDistance());
        }

        // Print final results
        System.out.println("Genetic Algorithm Finished");
        System.out.println("Final distance: " + popGA.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(popGA.getFittest());
        
        Population popGreedy = GreedyAlgorithm.solve(new Tour(TourManager.getCities()));
        
        // Print final results
        System.out.println("Greedy Algorithm Finished");
        System.out.println("Final distance: " + popGreedy.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(popGreedy.getFittest());
        
        
        
        Population popFLS = (new LocalSearchAlgorithm()).solve(100);
        
        // Print final results
        System.out.println("LocalSearch Algorithm Finished");
        System.out.println("Final distance: " + popFLS.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(popFLS.getFittest());
        
        Population popDP = (new DynamicProgramingAlgorithm()).solve(new Tour(TourManager.getCities()));
        
        // Print final results
        System.out.println("Dynamic Algorithm Finished");
        System.out.println("Final distance: " + popDP.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(popDP.getFittest());
        
        Population popBnB = (new BnBAlgorithm()).solve(new Tour(TourManager.getCities()));
        
        // Print final results
        System.out.println("Branch and Bound Finished");
        System.out.println("Final distance: " + popBnB.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(popBnB.getFittest());
        
        BnBHKAlgorithm bnBHKAlgorithm = new BnBHKAlgorithm();
        Population popBnBHK = bnBHKAlgorithm.solve(new Tour(TourManager.getCities()));
        
        // Print final results
        System.out.println("Branch and Bound Held Karp Finished");
        System.out.println("Final distance: " + popBnBHK.getFittest().getDistance());
        System.out.println("Execution time: " + bnBHKAlgorithm.executionTime);
        System.out.println("Solution:");
        System.out.println(popBnBHK.getFittest());
        */
		
		/*
		testCities(10);
		testCities(20);
		testCities(30);
		testCities(50);
		testCities(100);
		testCities(200);
		testCities(500);
		testCities(1000);
		*/
		
		//testBnB();
		//testDP();
		//testBnBHK();
		testGreedy();
		//testLS();
		//testGen();
	}

	
	private static void testBnB() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 3; i<= 15; i++){
			TourManager.generateCities(i, 200);
			Summary s = new Summary();
			s.city = i;
			for (int j = 0; j <5; j++){
				BnBAlgorithm al = new BnBAlgorithm();
				Population pop = new Population(1, false);
				pop = al.solve(new Tour(TourManager.getCities()));
				s.times.add(al.executionTime);
			}
			summary.add(s);
		}
		
		printresult(summary);
	
	}
	
	private static void testBnBHK() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 2; i<= 25; i++){
			TourManager.generateCities(i*2, 200);
			Summary s = new Summary();
			s.city = i*2;
			for (int j = 0; j <5; j++){
				BnBHKAlgorithm al = new BnBHKAlgorithm();
				Population pop = new Population(1, false);
				pop = al.solve(new Tour(TourManager.getCities()));
				//s.times.add((long) pop.getFittest().getDistance());
				s.times.add(al.executionTime);
				System.gc();
			}
			summary.add(s);
		}
		
		printresult(summary);
	
	}
	
	private static void testDP() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 2; i<=11; i++){
			TourManager.generateCities(i*2, 200);
			Summary s = new Summary();
			s.city = i*2;
			for (int j = 0; j <5; j++){
				DynamicProgramingAlgorithm al = new DynamicProgramingAlgorithm();
				Population pop = new Population(1, false);
				pop = al.solve(new Tour(TourManager.getCities()));
				s.times.add(al.executionTime);
				
			}
			summary.add(s);
		}
		
		printresult(summary);
	
	}
	
	private static void testGreedy() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 1; i<=10; i++){
			TourManager.generateCities(i*50, 200);
			Summary s = new Summary();
			s.city = i*50;
			for (int j = 0; j <5; j++){
				GreedyAlgorithm al = new GreedyAlgorithm();
				Population pop = new Population(1, false);
				pop = al.solve(new Tour(TourManager.getCities()));
				s.times.add(al.executionTime);
				
			}
			summary.add(s);
			printresult(summary);
		}
		
		printresult(summary);
	
	}
	
	private static void testLS() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 1; i<=10; i++){
			TourManager.generateCities(i*50, 200);
			Summary s = new Summary();
			s.city = i*50;
			for (int j = 0; j <5; j++){
				GreedyAlgorithm al0 = new GreedyAlgorithm();
				Population pop0 = new Population(1, false);
				pop0 = al0.solve(new Tour(TourManager.getCities()));
				LocalSearchAlgorithm al = new LocalSearchAlgorithm(new Tour(pop0.getFittest().getCities()));
				Population pop = new Population(1, false);
				pop = al.solve();
				s.times.add(al.executionTime);
				
			}
			summary.add(s);
			printresult(summary);
		}
		
		printresult(summary);
	
	}
	
	private static void testGen() {
		//System.out.println("Cities; Time; Value");
		ArrayList<Summary> summary = new ArrayList<>();
		for(int i = 1; i<=10; i++){
			TourManager.generateCities(i*50, 200);
			Summary s = new Summary();
			s.city = i*50;
			for (int j = 0; j <5; j++){
				GreedyAlgorithm al0 = new GreedyAlgorithm();
				Population pop0 = new Population(1, false);
				pop0 = al0.solve(new Tour(TourManager.getCities()));
				
				Population pop = GeneticAlgorithm.solve(new Tour(pop0.getFittest().getCities()));

				s.times.add(GeneticAlgorithm.executionTime);
				
			}
			summary.add(s);
			printresult(summary);
		}
		
		printresult(summary);
	
	}

	private static void printresult(ArrayList<Summary> summary) {
		for(int i = 0; i < summary.size(); i++){
			System.out.print(summary.get(i).city);
			for(int j=0;j<summary.get(i).times.size();j++){
				System.out.print("; " + summary.get(i).times.get(j));
			}
			System.out.println("");
		}
		
	}


	private static void testCities(int numberOfCities) {
		System.out.println("===== Test with " + numberOfCities + " cities =====");
		TourManager.generateCities(numberOfCities, 500);
		BnBAlgorithm bnBAlgorithm = new BnBAlgorithm();
		Population popBnB = new Population(1, false);
		if(numberOfCities < 20){
			popBnB = bnBAlgorithm.solve(new Tour(TourManager.getCities()));
		}
		
		DynamicProgramingAlgorithm dynamicProgramingAlgorithm = new DynamicProgramingAlgorithm();
		Population popDP = new Population(1, false);	
		if(numberOfCities < 30){
			popDP = dynamicProgramingAlgorithm.solve(new Tour(TourManager.getCities()));
		}
		
        BnBHKAlgorithm bnBHKAlgorithm = new BnBHKAlgorithm();
        Population popBnBHK = new Population(1, false);
        if(numberOfCities < 60){
        popBnBHK = bnBHKAlgorithm.solve(new Tour(TourManager.getCities()));
        }
        Population popGreedy = GreedyAlgorithm.solve(new Tour(TourManager.getCities()));
        
        LocalSearchAlgorithm localSearchAlgorithm = new LocalSearchAlgorithm(new Tour(popGreedy.getFittest().getCities()));
		Population popFLS = localSearchAlgorithm.solve();
        
        
                
        Population popGA = GeneticAlgorithm.solve(new Tour(popGreedy.getFittest().getCities()));

        System.out.println("BnB, DP, BnBHK, Greedy, FLS, Genetic ");
        System.out.println(
        				(popBnB.getFittest()!=null?popBnB.getFittest().getDistance():0) + ", " 
        				+ (popDP.getFittest() != null?popDP.getFittest().getDistance():0) + ", " 
        				+ (popBnBHK.getFittest() !=null ?popBnBHK.getFittest().getDistance():0) + ", " 
        				+ popGreedy.getFittest().getDistance() + ", " 
        				+ popFLS.getFittest().getDistance() + ", " 
        				+ popGA.getFittest().getDistance());
        System.out.println(bnBAlgorithm.executionTime + ", " + dynamicProgramingAlgorithm.executionTime + ", " + bnBHKAlgorithm.executionTime + ", " + GreedyAlgorithm.executionTime + ", " +  localSearchAlgorithm.executionTime + ", " + GeneticAlgorithm.executionTime);
	}

}
