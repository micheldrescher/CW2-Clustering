package uk.ac.ox.oerc.clustering.analysis;

import flanagan.analysis.Stat;
import flanagan.math.Matrix;

public class Util {

	private static final double EPSILON = 0.00000001;

	public static double sumUp(double[] anArray) {
		double aResult = 0;
		for (double aValue : anArray) {
			aResult += aValue;
		}
		return aResult;
	}

	public static boolean equalsWithEps(double x, double y) {
		if (y == 0.0 && x == 0.0) return true; 

		return (Math.abs(x/y - 1.0) < EPSILON); 
	}
	
	public static double[] calcSQRTs(final double[] anArray) {
		double[] aResult = new double[anArray.length];
		for (int i=0; i<anArray.length; i++) {
			aResult[i] = Math.sqrt(anArray[i]);
		}
		return aResult;
	}
	
	public static double[] calcColumnStdDevs(Matrix aMatrix) {
		double[] aResult = new double[aMatrix.getNumberOfColumns()];
		for (int i=0; i<aMatrix.getNumberOfColumns(); i++) {
			Stat aStat = new Stat(aMatrix.getColumnCopy(i));
			aResult[i] = aStat.standardDeviation();
		}
		return aResult;
	}

	public static boolean equals(double[] x, double[] y) {
		if (x == y) return true; // same reference
		if (x.length != y.length) return false; // different length!
		
		for (int i=0; i<x.length; i++) {
			if (!equalsWithEps(x[i], y[i])) return false;
		}
		
		return true;
	}
	
	public static int calculateNumEV(double[] eigenValues, double threshold) {
		int aResult = 0;
		for(double d : eigenValues) {
			if (d > threshold) aResult++;
		}
		return aResult;
	}
	
	public static Matrix projectScores(Matrix scores, Matrix eigenVectors, int numVectors) {
		Matrix aResult = new Matrix(scores.getNumberOfRows(), scores.getNumberOfColumns());
		
		for (int i=0; i<scores.getNumberOfRows(); i++) {
			double[] subScores = scores.getSubMatrix(i, 0, i, numVectors-1).getRowCopy(0);
			for (int j=0; j<scores.getNumberOfColumns(); j++) {
				double[] subVector = eigenVectors.getSubMatrix(j, 0, j, numVectors-1).getRowCopy(0);
				double[] temp = scalarDivision(subVector, Math.sqrt(vectorMult(subVector,subVector)));
				aResult.setElement(i, j, vectorMult(subScores, temp));
			}
		}
		return aResult;
	}

	
	private static double vectorMult(double[] x, double[] y) {
		double aResult = 0;
		for (int i=0; i<x.length; i++) {
			aResult += x[i] * y[i];
		}
		return aResult;
	}
	
	private static double[] scalarDivision(double[] x, double d) {
		double[] aResult = new double[x.length];
		for (int i=0; i<x.length; i++) {
			aResult[i] = x[i]/d;
		}
		return aResult;
	}

}
