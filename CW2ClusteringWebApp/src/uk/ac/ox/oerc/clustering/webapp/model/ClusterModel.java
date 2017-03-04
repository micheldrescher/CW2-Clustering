package uk.ac.ox.oerc.clustering.webapp.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ox.oerc.clustering.analysis.ClusterWithStats;
import uk.ac.ox.oerc.clustering.analysis.NamedDoubleArray;
import uk.ac.ox.oerc.clustering.model.Characteristic;

public class ClusterModel {

	private String name;
	
	private List<Characteristic> selectedCharateristics;
	
	private int numSelectedCharateristics;
	
	private List<ProjectModel> members;
	
	private List<ProjectModel> stats;
	
	public ClusterModel(ClusterWithStats cluster, List<Characteristic> selectedCharacteristics) {
		name = cluster.getName();
		this.selectedCharateristics = selectedCharacteristics;
		numSelectedCharateristics = selectedCharacteristics.size();
		// initialise members
		members = new ArrayList<ProjectModel>();
		for (NamedDoubleArray member : cluster.getMembers()) {
			members.add(new ProjectModel(member.getName(), selectedCharacteristics, member));
		}
		// initialise stats, if any
		stats = new ArrayList<ProjectModel>();
		if (cluster.isWithStats()) {
			stats.add(new ProjectModel("[means]", selectedCharacteristics, cluster.getMeans()));
			stats.add(new ProjectModel("[std. dev.]", selectedCharacteristics, cluster.getStdDev()));
			stats.add(new ProjectModel("[s/n ratio]", selectedCharacteristics, cluster.getSnr()));
		}
	}
	
	public String getName() {
		return name;
	}

	/**
	 * @return the selectedCharacteristics
	 */
	public List<Characteristic> getSelectedCharacteristics() {
		return selectedCharateristics;
	}

	/**
	 * @return the selectedCharacteristics
	 */
	public int getNumSelectedCharacteristics() {
		return numSelectedCharateristics;
	}

	public List<ProjectModel> getMembers() {
		return members;
	}
	
	public List<ProjectModel> getStats() {
		return stats;
	}
}
