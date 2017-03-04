package uk.ac.ox.oerc.clustering.analysis;

import java.util.ArrayList;
import java.util.List;

import ca.pfv.spmf.algorithms.clustering.distanceFunctions.DistanceEuclidian;
import ca.pfv.spmf.algorithms.clustering.distanceFunctions.DistanceFunction;
import ca.pfv.spmf.algorithms.clustering.hierarchical_clustering.AlgoHierarchicalClustering;
import ca.pfv.spmf.patterns.cluster.ClusterWithMean;
import ca.pfv.spmf.patterns.cluster.DoubleArray;
import uk.ac.ox.oerc.clustering.webapp.service.ClusterService;

public class HierarchicalClustererService implements ClusterService {
	
	@Override
	public List<ClusterSolution> clusterProjects(DecoratedMatrix projectedScores, double maxDistance, int minClusterSize,
			int minCharacteristics, double minSNR) {
		// initialise
		AlgoHierarchicalClustering clusterer = new AlgoHierarchicalClustering();
		DistanceFunction function = new DistanceEuclidian();
		
		// convert matrix's value rows into DoubleArrays...
		List<ClusterWithMean> clusters = new ArrayList<ClusterWithMean>();
		double[][] rows = projectedScores.getArrayCopy();
		String[] names = projectedScores.getPersonNames();
		for (int i = 0; i < rows.length; i++) {
			double[] aRow = rows[i]; // a vector
			String aName = names[i];
			DoubleArray aVector = new NamedDoubleArray(aName, aRow);
			// ... and initialise with one vector per cluster.
			ClusterWithMean cluster = new ClusterWithMean(aRow.length);
			cluster.addVector(aVector);
			cluster.setMean(aVector.clone());
			clusters.add(cluster);
		}
		
		// run the analysis
		List<ClusterSolution> result = new ArrayList<ClusterSolution>();
		// for now skip the iterations and qualifying phase
		result.add(new ClusterSolution(clusterer.runAlgorithm(clusters, maxDistance, function), maxDistance, minClusterSize, minCharacteristics, minSNR));
		
		return result;

	}
}
