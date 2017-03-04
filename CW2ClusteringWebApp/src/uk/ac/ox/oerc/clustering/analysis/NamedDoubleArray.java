package uk.ac.ox.oerc.clustering.analysis;

import java.util.ArrayList;
import java.util.List;

import ca.pfv.spmf.patterns.cluster.DoubleArray;

public class NamedDoubleArray extends DoubleArray {
	
	private String name;

	public NamedDoubleArray(double[] data) {
		super(data);
	}

	public NamedDoubleArray(String name, double[] data) {
		this(data);
		this.name = name;
	}

	public List<Double> getData() {
		List<Double> result = new ArrayList<Double>(data.length);
		for (double d : data) {
			result.add(new Double(d));
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
