package uk.ac.ox.oerc.clustering.webapp.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ac.ox.oerc.clustering.analysis.NamedDoubleArray;
import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.webapp.Util;

public class ProjectModel {

	private String name;
	
	private List<DecoratedScore> scores;
	
	public ProjectModel(String name, List<Characteristic> selectedCharacteristics, NamedDoubleArray members) {
		this.name = name;
		scores = decorateScores(selectedCharacteristics, members);
	}

	public String getName() {
		return name;
	}
	
	public List<DecoratedScore> getScores() {
		return scores;
	}
	

	private List<DecoratedScore> decorateScores(List<Characteristic> selectedCharacteristics, NamedDoubleArray data) {
		// create list in original order
		List<DecoratedScore> orig = new ArrayList<DecoratedScore>(data.data.length);
		List<DecoratedScore> ranked = new ArrayList<DecoratedScore>(data.data.length);
		for (int i=0; i< selectedCharacteristics.size(); i++) {
			DecoratedScore dwc = new DecoratedScore(data.data[i], null, selectedCharacteristics.get(i));
			orig.add(dwc);
			ranked.add(dwc);
		}
		// duplicate that list
		Collections.copy(ranked, orig);
		// sort ranked ascending
		ranked.sort(new Comparator<DecoratedScore>() {
			public int compare(DecoratedScore o1, DecoratedScore o2) {
				return Double.compare(o1.getValue(), o2.getValue());
			}
		});
		// ... and linearly assign colour values.
		List<Color> spectrum = Util.createSpectrum(selectedCharacteristics.size());
		for (int i = 0; i < ranked.size(); i++) {
			ranked.get(i).colour = spectrum.get(i);
		}
		return orig;
	}

}
