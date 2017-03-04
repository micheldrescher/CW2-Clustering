package uk.ac.ox.oerc.clustering.analysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ca.pfv.spmf.patterns.cluster.ClusterWithMean;

public class ClusterSolution {
	
	// quality criteria
	private double maxDistance;
//	private int minClusterSize;
//	private int minCharacteristics;
//	private double minSNR;
//	private boolean matchesCriteria;
	
	// converted from lists of ClusterWithMean (also with converted NamedDoubleArrays!)
	List<ClusterWithStats> clusters;
	
	public ClusterSolution(List<ClusterWithMean> aClustering, 
			               double maxDistance, int minClusterSize, int minCharacteristics, double minSNR) {
		// 
		// convert SPMF cluster structure to what we need
		//
		// 1. order the given clusters by number of members, ascending
		aClustering.sort(new Comparator<ClusterWithMean>() {
			public int compare(ClusterWithMean o1, ClusterWithMean o2) {
				return Integer.compare(o1.getVectors().size(), o2.getVectors().size());
			}
		});
		// 2. collect all unclustered projects in a separate list
		List<NamedDoubleArray> unclustered = new ArrayList<NamedDoubleArray>();
		// 3. iterate over all existing clusters, filtering out all unclustered ones to be inserted later
		int counter = 0;
		clusters = new ArrayList<ClusterWithStats>(aClustering.size());
		for (ClusterWithMean old : aClustering) {
			// filter all unclustered projects
			if (old.getVectors().size() == 1) {
				unclustered.add((NamedDoubleArray)old.getVectors().get(0));
				continue;
			} 
			// 3. create a new ClusterWithStats conversion
			counter++;
			ClusterWithStats aNew = new ClusterWithStats(old);
			aNew.setName("Cluster " + counter);
			clusters.add(aNew);
		}
		// 4. finally, insert all unclustered projects at the first position
		//    But only if there actually ARE unclustered projects!
		if (unclustered.size() > 0) {
			unclustered.sort(new Comparator<NamedDoubleArray>() {
				public int compare(NamedDoubleArray o1, NamedDoubleArray o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			ClusterWithStats residual = new ClusterWithStats(unclustered);
			residual.setName("UNCLUSTERED");
			clusters.add(0, residual);
		}
		
//		setQualityCriteria(maxDistance, minClusterSize, minCharacteristics, minSNR);
	}

//	private void setQualityCriteria(double maxDistance, int minClusterSize, int minCharacteristics, double minSNR) {
//		this.maxDistance = maxDistance;
//		this.minClusterSize = minClusterSize;
//		this.minCharacteristics = minCharacteristics;
//		this.minSNR = minSNR;
//		matchesCriteria = isMatchingCriteria();
//	}
	
	/**
	 * @return the maxDistance
	 */
	public double getMaxDistance() {
		return maxDistance;
	}
//	
//	/**
//	 * @return the minClusterSize
//	 */
//	public int getMinClusterSize() {
//		return minClusterSize;
//	}
//	
//	/**
//	 * @return the minCharacteristics
//	 */
//	public int getMinCharacteristics() {
//		return minCharacteristics;
//	}
//	
//	/**
//	 * @return the minSNR
//	 */
//	public double getMinSNR() {
//		return minSNR;
//	}
//	
//	/**
//	 * @return the matchesCriteria
//	 */
//	public boolean isMatchesCriteria() {
//		return matchesCriteria;
//	}
	
	public List<ClusterWithStats> getClusters() {
		return clusters;
	}

//	private boolean isMatchingCriteria() {
//		for (ClusterWithStats aCluster : clusters) {
//			// disregard the unclustered projects
//			if (!aCluster.isWithStats()) continue;
//			// if any cluster is smaller than minimum size, return false
//			if (aCluster.getMembers().size() < minClusterSize) return false;
//
//			// create a temporary DoubleArray of means and snr 
//			List<DoubleArray> meanAndSNR= new ArrayList<DoubleArray>(aCluster.getMeans().size());
//			for (int i = 0; i < aCluster.getMeans().size(); i++) {
//				meanAndSNR.add(new DoubleArray(new double[] {aCluster.getMeans().get(i), aCluster.getSnr().get(i)}));
//			}
//			meanAndSNR.sort(new DoubleArrayComparator());
//			Collections.reverse(meanAndSNR);
//			
//			// assess numTops
//			for (int i = 0; i < minCharacteristics; i++) {
//				if (meanAndSNR.get(i).data[1] < minSNR) return false;
//			}
//		}
//		
//		return true;
//	}

}
