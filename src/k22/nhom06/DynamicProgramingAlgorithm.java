package k22.nhom06;

import java.util.ArrayList;

public class DynamicProgramingAlgorithm {
	private ArrayList<Integer> outputArray = new ArrayList<Integer>();
	private int  p[][], npow, N;
	private double g[][],d[][];
	Tour best;
	public long executionTime;
	
	public DynamicProgramingAlgorithm() {
		best = new Tour();
	}

	public Population solve(Tour source) {
		long start = System.currentTimeMillis();
		Population population = new Population(1, false);
		double[][] inputArray = calculateInputArray(source);
		int n = source.tourSize();
		N = n;
		npow = (int) Math.pow(2, n);
		g = new double[n][npow];
		p = new int[n][npow];
		d = inputArray;
		int i, j;
		for (i = 0; i < n; i++) {
			for (j = 0; j < npow; j++) {
				g[i][j] = -1;
				p[i][j] = -1;
			}
		}
		// initialize based on distance matrix
		for (i = 0; i < n; i++) {
			g[i][0] = inputArray[i][0];
		}
		double result = tsp(0, npow - 2);
		outputArray.add(0);
		getPath(0, npow - 2);
		outputArray.add((int)result);

		for(i=0;i<n;i++){
			best.setCity(i,source.getCity(outputArray.get(i)));
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

	private double tsp(int start, int set) {
		int masked, mask;
		double result = -1;
		double temp;
		if (g[start][set] != -1) {
			return g[start][set];
		} else {
			for (int x = 0; x < N; x++) {
				mask = npow - 1 - (int) Math.pow(2, x);
				masked = set & mask;
				if (masked != set) {
					temp = d[start][x] + tsp(x, masked);
					if (result == -1 || result > temp) {
						result = temp;
						p[start][set] = x;
					}
				}
			}
			g[start][set] = result;
			return result;
		}
	}

	private void getPath(int start, int set) {
		if (p[start][set] == -1) {
			return;
		}
		int x = p[start][set];
		int mask = npow - 1 - (int) Math.pow(2, x);
		int masked = set & mask;
		outputArray.add(x);
		getPath(x, masked);
	}
}
