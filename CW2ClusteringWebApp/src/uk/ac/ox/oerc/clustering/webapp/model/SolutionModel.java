package uk.ac.ox.oerc.clustering.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.ac.ox.oerc.clustering.analysis.ClusterSolution;
import uk.ac.ox.oerc.clustering.analysis.ClusterWithStats;
import uk.ac.ox.oerc.clustering.model.Characteristic;

public class SolutionModel {
	
	private UUID id;
	
	// metadata
	private double maxDistance;
//	private int minClusterSize;
//	private int minCharacteristics;
//	private double minSNR;
//	private boolean matchesCriteria;
	
	private List<ClusterModel> clusters;	
	
	public SolutionModel(ClusterSolution aSolution, List<Characteristic> selectedCharacteristics) {
		id = UUID.randomUUID();
		// copy metadata
		setMaxDistance(aSolution.getMaxDistance());
//		setMinClusterSize(aSolution.getMinClusterSize());
//		setMinCharacteristics(aSolution.getMinCharacteristics());
//		setMinSNR(aSolution.getMinSNR());
//		matchesCriteria = aSolution.isMatchesCriteria();
		// generate the clusters map
		clusters = new ArrayList<ClusterModel>();
		for (ClusterWithStats cluster : aSolution.getClusters()) {
			clusters.add(new ClusterModel(cluster, selectedCharacteristics));
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id.toString();
	}

	/**
	 * @return the maxDistance
	 */
	public double getMaxDistance() {
		return maxDistance;
	}

	/**
	 * @param maxDistance the maxDistance to set
	 */
	private void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
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
//	 * @param minClusterSize the minClusterSize to set
//	 */
//	private void setMinClusterSize(int minClusterSize) {
//		this.minClusterSize = minClusterSize;
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
//	 * @param minCharacteristics the minCharacteristics to set
//	 */
//	private void setMinCharacteristics(int minCharacteristics) {
//		this.minCharacteristics = minCharacteristics;
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
//	 * @param minSNR the minSNR to set
//	 */
//	private void setMinSNR(double minSNR) {
//		this.minSNR = minSNR;
//	}
//
//	/**
//	 * @return the matchesCriteria
//	 */
//	public boolean isMatchesCriteria() {
//		return matchesCriteria;
//	}

	/**
	 * @return the clusters
	 */
	public List<ClusterModel> getClusters() {
		return clusters;
	}
	
}
