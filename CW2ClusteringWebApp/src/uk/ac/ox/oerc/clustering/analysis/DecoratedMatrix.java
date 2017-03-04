package uk.ac.ox.oerc.clustering.analysis;

import flanagan.math.Matrix;

public class DecoratedMatrix extends Matrix {

	private static final double EPSILON = 0.00000001;

	private String[] itemNames;
	
	private String[] personNames;
	
	public DecoratedMatrix(String[] itemNames, String[] personNames, Matrix scores) {
		super(scores);
		this.itemNames = itemNames;
		this.personNames = personNames;
	}

	public String[] getItemNames() {
		return itemNames;
	}

	public String[] getPersonNames() {
		return personNames;
	}

	public String findPersonName(double[] personScores) {
		String aName = null;
		for (int i=0; i<getNrow(); i++) {
			double[] currentRow = getRowCopy(i);
			if (equalsWithEps(personScores, currentRow)) {
				aName = personNames[i];
				break;
			}
		}
		return aName;
	}
	
	private static boolean equalsWithEps(double[] x, double[] y) {
		if (x.length != y.length) return false;
		for (int i=0; i<x.length; i++) {
			if (!equalsWithEps(x[i], y[i])) return false;
		}
		return true;
	}
	
	private static boolean equalsWithEps(double x, double y) {
		if (y == 0.0 && x == 0.0) return true; 

		return (Math.abs(x/y - 1.0) < EPSILON); 
	}

}
