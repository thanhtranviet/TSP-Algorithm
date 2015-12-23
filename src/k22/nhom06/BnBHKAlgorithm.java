package k22.nhom06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BnBHKAlgorithm {
	// number of cities
	private int n;
	// city locations
	private double[] x;
	private double[] y;
	// cost matrix
	private double[][] cost;
	// matrix of adjusted costs
	private double[][] costWithPi;
	Node bestNode = new Node();
	Tour best;
	public long executionTime;
	public BnBHKAlgorithm() {
    	best = new Tour();
    }
	public Population solve(Tour source) {
		long start = System.currentTimeMillis();
		Population population = new Population(1, false);
		n = source.tourSize();
		x = new double[n];
		y = new double[n];
		cost = calculateInputArray(source);
		costWithPi = new double[n][n];
		calculateCityLocation(source);
		bestNode.lowerBound = Double.MAX_VALUE;
		Node currentNode = new Node();
		currentNode.excluded = new boolean[n][n];
		costWithPi = new double[n][n];
		computeHeldKarp(currentNode);
		PriorityQueue<Node> pq = new PriorityQueue<Node>(11, new NodeComparator());
		do {
			do {
				int i = -1;
				for (int j = 0; j < n; j++) {
					if (currentNode.degree[j] > 2 && (i < 0 || currentNode.degree[j] < currentNode.degree[i]))
						i = j;
				}
				if (i < 0) {
					if (currentNode.lowerBound < bestNode.lowerBound) {
						bestNode = currentNode;
					}
					break;
				}
				//System.err.printf(".");
				PriorityQueue<Node> children = new PriorityQueue<Node>(11, new NodeComparator());
				children.add(exclude(currentNode, i, currentNode.parent[i]));
				for (int j = 0; j < n; j++) {
					if (currentNode.parent[j] == i)
						children.add(exclude(currentNode, i, j));
				}
				currentNode = children.poll();
				pq.addAll(children);
			} while (currentNode.lowerBound < bestNode.lowerBound);

			currentNode = pq.poll();
		} while (currentNode != null && currentNode.lowerBound < bestNode.lowerBound);
		// output suitable for gnuplot
		// set style data vector
		//System.out.printf("# %.0f%n", bestNode.lowerBound);
		int j = 0;
		int count = 0;
		do {
			int i = bestNode.parent[j];
			//System.out.printf("%f\t%f\t%f\t%f%n", x[j], y[j], x[i] - x[j], y[i] - y[j]);
			best.setCity(count,source.getCity(i));
			count++;
			j = i;
		} while (j != 0);
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
	
	private void calculateCityLocation(Tour source) {
		for(int i=0;i<n;i++){
			x[i] = source.getCity(i).x;
			y[i] = source.getCity(i).y;
		}
	}
	private Node exclude(Node node, int i, int j) {
		Node child = new Node();
		child.excluded = node.excluded.clone();
		child.excluded[i] = node.excluded[i].clone();
		child.excluded[j] = node.excluded[j].clone();
		child.excluded[i][j] = true;
		child.excluded[j][i] = true;
		computeHeldKarp(child);
		return child;
	}

	private void computeHeldKarp(Node node) {
		node.pi = new double[n];
		node.lowerBound = Double.MIN_VALUE;
		node.degree = new int[n];
		node.parent = new int[n];
		double lambda = 0.1;
		while (lambda > 1e-06) {
			double previousLowerBound = node.lowerBound;
			computeOneTree(node);
			if (!(node.lowerBound < bestNode.lowerBound))
				return;
			if (!(node.lowerBound < previousLowerBound))
				lambda *= 0.9;
			int denom = 0;
			for (int i = 1; i < n; i++) {
				int d = node.degree[i] - 2;
				denom += d * d;
			}
			if (denom == 0)
				return;
			double t = lambda * node.lowerBound / denom;
			for (int i = 1; i < n; i++)
				node.pi[i] += t * (node.degree[i] - 2);
		}
	}

	private void computeOneTree(Node node) {
		// compute adjusted costs
		node.lowerBound = 0.0;
		Arrays.fill(node.degree, 0);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				costWithPi[i][j] = node.excluded[i][j] ? Double.MAX_VALUE : cost[i][j] + node.pi[i] + node.pi[j];
		}
		int firstNeighbor;
		int secondNeighbor;
		// find the two cheapest edges from 0
		if (costWithPi[0][2] < costWithPi[0][1]) {
			firstNeighbor = 2;
			secondNeighbor = 1;
		} else {
			firstNeighbor = 1;
			secondNeighbor = 2;
		}
		for (int j = 3; j < n; j++) {
			if (costWithPi[0][j] < costWithPi[0][secondNeighbor]) {
				if (costWithPi[0][j] < costWithPi[0][firstNeighbor]) {
					secondNeighbor = firstNeighbor;
					firstNeighbor = j;
				} else {
					secondNeighbor = j;
				}
			}
		}
		addEdge(node, 0, firstNeighbor);
		Arrays.fill(node.parent, firstNeighbor);
		node.parent[firstNeighbor] = 0;
		// compute the minimum spanning tree on nodes 1..n-1
		double[] minCost = costWithPi[firstNeighbor].clone();
		for (int k = 2; k < n; k++) {
			int i;
			for (i = 1; i < n; i++) {
				if (node.degree[i] == 0)
					break;
			}
			for (int j = i + 1; j < n; j++) {
				if (node.degree[j] == 0 && minCost[j] < minCost[i])
					i = j;
			}
			addEdge(node, node.parent[i], i);
			for (int j = 1; j < n; j++) {
				if (node.degree[j] == 0 && costWithPi[i][j] < minCost[j]) {
					minCost[j] = costWithPi[i][j];
					node.parent[j] = i;
				}
			}
		}
		addEdge(node, 0, secondNeighbor);
		node.parent[0] = secondNeighbor;
		node.lowerBound = Math.rint(node.lowerBound);
	}

	private void addEdge(Node node, int i, int j) {
		double q = node.lowerBound;
		node.lowerBound += costWithPi[i][j];
		node.degree[i]++;
		node.degree[j]++;
	}
}

class Node {
	public boolean[][] excluded;
	// Held--Karp solution
	public double[] pi;
	public double lowerBound;
	public int[] degree;
	public int[] parent;
}

class NodeComparator implements Comparator<Node> {
	public int compare(Node a, Node b) {
		return Double.compare(a.lowerBound, b.lowerBound);
	}
}
