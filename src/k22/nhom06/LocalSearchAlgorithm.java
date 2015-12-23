package k22.nhom06;

import java.util.ArrayList;

public class LocalSearchAlgorithm {
	ArrayList<Boolean> pointactive = new ArrayList<>();
	int numCities;
	int visited, current;
	Tour best;
	public long executionTime;
	
	public LocalSearchAlgorithm() {

		best = new Tour();
		best.generateIndividual();
		numCities = best.tourSize();
	}
	
	public LocalSearchAlgorithm(Tour startTour) {

		best = startTour;
		numCities = best.tourSize();
	}

	public Population solve() {
		long start = System.currentTimeMillis();
		int count = Math.max(100, TourManager.numberOfCities());
		Population population = new Population(1, false);
		for (int k = 0; k < count; k++) {
			visited = 0;
			current = 0;
			pointactive = new ArrayList<>();
			for (int i = 0; i < numCities; i++) {
				pointactive.add(true);
			}

			while (visited < numCities) {
				City currentPoint = best.getCity(current);
				if (pointactive.get(current)) {
					double modified = findMove(current, currentPoint, numCities);
					if (modified < 0) {
						current = wrap(current - 1, numCities);
						visited = 0;
						break;
					}
					pointactive.set(current, false);
				}

				current = wrap(current + 1, numCities);
				visited++;
			}
		}
		population.saveTour(0, best);
		long end = System.currentTimeMillis();
		this.executionTime = end - start;
		return population;
	}

	int wrap(int i, int max) {
		return (max + i) % max;
	}

	double findMove(int current, City currentPoint, int numCities) {
		int prev = wrap(current - 1, numCities);
		int next = wrap(current + 1, numCities);
		City prevPoint = best.getCity(prev);
		City nextPoint = best.getCity(next);
		for (int i = wrap(current + 2, numCities), j = wrap(current + 3, numCities); j != current; i = j, j = wrap(
				j + 1, numCities)) {

			City c = best.getCity(i);
			City d = best.getCity(j);
			double delta1 = moveCost(prevPoint, currentPoint, c, d);
			if (delta1 < 0) {
				activate(prev, current, i, j);
				reverse(Math.min(prev, i) + 1, Math.max(prev, i));
				return delta1;
			}
			double delta2 = moveCost(currentPoint, nextPoint, c, d);
			if (delta2 < 0) {
				activate(current, next, i, j);
				reverse(Math.min(current, i) + 1, Math.max(current, i));
				return delta2;
			}
		}
		return 0.0;
	}

	double moveCost(City a, City b, City c, City d) {
		double _ab = a.distanceTo(b);
		double _cd = c.distanceTo(d);
		double _ac = a.distanceTo(c);
		double _bd = b.distanceTo(d);
		if (_ab < _ac && _cd < _bd)
			return 1;
		return (Math.sqrt(_ac) + Math.sqrt(_bd)) - (Math.sqrt(_ab) + Math.sqrt(_cd));
	}

	void activate(int a, int b, int c, int d) {
		pointactive.set(a, false);
		pointactive.set(b, false);
		pointactive.set(c, false);
		pointactive.set(d, false);
	}

	void reverse(int from, int to) {
		for (int i = from, j = to; i < j; i++, j--) {
			City tmp = best.getCity(i);
			best.setCity(i, best.getCity(j));
			best.setCity(j, tmp);
		}
	}
}
