package uk.ac.ox.oerc.clustering.webapp.model;

import java.awt.Color;

import uk.ac.ox.oerc.clustering.model.Characteristic;

public class DecoratedScore {
	
	private double value;
	protected Color colour;
	private Characteristic.Status status;
	
	public DecoratedScore(Double aDouble, Color aColour, Characteristic aCharacteristic) {
		value = aDouble;
		colour = aColour;
		status = aCharacteristic.getStatus();
	}
	
	public double getValue() {
		return value;
	}
	
	public String getColour() {
		return String.format("background-color: #%2h%2h%2h;", colour.getRed(), colour.getGreen(), colour.getBlue());
	}
	
	public Characteristic.Status getStatus() {
		return status;
	}
}

