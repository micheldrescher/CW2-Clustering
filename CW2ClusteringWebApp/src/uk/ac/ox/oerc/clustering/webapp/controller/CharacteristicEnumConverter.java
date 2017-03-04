package uk.ac.ox.oerc.clustering.webapp.controller;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import uk.ac.ox.oerc.clustering.model.Characteristic;

@FacesConverter(value="MyEnumConverter")
public class CharacteristicEnumConverter extends EnumConverter {

	public CharacteristicEnumConverter() {
		super(Characteristic.class);
	}
	

}
