/**
 * 
 */
package uk.ac.ox.oerc.clustering.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.pfv.spmf.patterns.cluster.ClusterWithMean;
import ca.pfv.spmf.patterns.cluster.DoubleArray;
import flanagan.analysis.Stat;

/**
 * Cluster with Mean and SNR is designed as an immutable version of CLusterWithMean.
 * Instead of subclassing, it takes the current cluster's data and adds some decoration to it
 * 
 * @author Michel Drescher
 */
public class ClusterWithStats {
	
	private String name;
	private List<NamedDoubleArray> members;
	private NamedDoubleArray means;
	private NamedDoubleArray stdDev;
	private NamedDoubleArray sNR;

	/**
	 * @param vectorsSize
	 */
	public ClusterWithStats(ClusterWithMean aCluster) {
		if (aCluster.getVectors().size() < 1) {
			throw new IllegalArgumentException("Empty clusters are not supported!");
		}
		// add the members
		List<NamedDoubleArray> some = new ArrayList<NamedDoubleArray>(aCluster.getVectors().size());
		for (DoubleArray aMember : aCluster.getVectors()) {
			if (!(aMember instanceof NamedDoubleArray)) {
				throw new IllegalArgumentException("InjectedNamedDoubleArray was altered!");
			}
			some.add((NamedDoubleArray) aMember);
		}
		// sort the members alphabetically
		some.sort(new Comparator<NamedDoubleArray>() {
			public int compare(NamedDoubleArray o1, NamedDoubleArray o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		// make the members immutable
		members = Collections.unmodifiableList(some);
		// calculate the stats
		means = new NamedDoubleArray("means", aCluster.getMean().data);
		calcStdDevAndSNR();
	}
	
	public ClusterWithStats(List<NamedDoubleArray> members) {
		this.members = members;
	}

	private void calcStdDevAndSNR() {
		double[] stdDevData = new double[members.get(0).data.length];
		double[] sNRData = new double[members.get(0).data.length];
		for (int col = 0; col < sNRData.length; col++) {
			//assemble an array of column data
			double[] aColumn = new double[members.size()];
			for (int row = 0; row < members.size(); row++) {
				aColumn[row] = members.get(row).get(col);
			}
			stdDevData[col] = new Stat(aColumn).standardDeviation();
			sNRData[col] = Math.abs(means.get(col) / stdDevData[col]);
		}
		stdDev = new NamedDoubleArray("Std. Dev.", stdDevData);
		sNR = new NamedDoubleArray("S/N ratio", sNRData);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the members
	 */
	public List<NamedDoubleArray> getMembers() {
		return members;
	}

	/**
	 * @return the means
	 */
	public NamedDoubleArray getMeans() {
		return means;
	}

	/**
	 * @return the stdDev
	 */
	public NamedDoubleArray getStdDev() {
		return stdDev;
	}

	/**
	 * @return the sNR
	 */
	public NamedDoubleArray getSnr() {
		return sNR;
	}

	/**
	 * @return the stats
	 */
	public boolean isWithStats() {
		return means != null && stdDev != null && sNR != null;
	}
}
