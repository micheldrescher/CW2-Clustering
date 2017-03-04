package uk.ac.ox.oerc.clustering.webapp.service;

import java.util.List;

import uk.ac.ox.oerc.clustering.analysis.ClusterSolution;
import uk.ac.ox.oerc.clustering.analysis.DecoratedMatrix;

public interface ClusterService {
	
	public List<ClusterSolution> clusterProjects(DecoratedMatrix projectedScores, double minDistance, int minClusterSize,
			int minCharacteristics, double minSNR);

}
