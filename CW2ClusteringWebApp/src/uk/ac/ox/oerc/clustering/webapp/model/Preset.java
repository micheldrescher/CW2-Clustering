package uk.ac.ox.oerc.clustering.webapp.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.ac.ox.oerc.clustering.model.Characteristic;

public enum Preset {
	All(Arrays.asList(Characteristic.values())),
	
	Essential(Characteristic.getFinalNIST()), 
	
	Common(Characteristic.getDraftNIST()), 

	Custom(Collections.emptyList());
	
	private List<Characteristic> mappedChars;

	private Preset(List<Characteristic> theChars) {
		mappedChars = theChars;
	}
	
	public List<Characteristic> getMappedCharacteristics() {
		return mappedChars;
	}
}
