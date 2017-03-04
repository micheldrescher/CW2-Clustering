package uk.ac.ox.oerc.clustering.analysis;

import java.util.Comparator;

import ca.pfv.spmf.patterns.cluster.DoubleArray;

/**
 * @author michel
 *
 */
public class DoubleArrayComparator implements Comparator<DoubleArray> {
	private static DoubleArrayComparator _self = null;
	
	public static int compareTo(DoubleArray o1, DoubleArray o2) {
		if (_self == null) _self = new DoubleArrayComparator();
		return _self.compare(o1, o2);
	}
	
	@Override
	// arrays are compared element by element and any result not 0 returned immediately.
	// That comparison ends at the end of the shorter of the two arrays.
	// If at this point o1 is shorter than o2, -1 will be returned, 1 otherwise.
	// If both arrays are of the same size, then 0 will be returned.
	public int compare(DoubleArray o1, DoubleArray o2) {
		int stop = Math.min(o1.size(),  o2.size());
		int result = 0;
		int i = 0;
		do {
			result = Double.compare(o1.get(i), o2.get(i));
			i++;
		} while (result == 0 && i<= stop);
		
		if (result == 0) {
			result = Integer.compare(o1.size(),  o2.size());
		}
		
		return result;
	}

}
