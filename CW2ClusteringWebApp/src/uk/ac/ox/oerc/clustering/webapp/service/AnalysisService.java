package uk.ac.ox.oerc.clustering.webapp.service;

import java.util.List;

import uk.ac.ox.oerc.clustering.analysis.DecoratedMatrix;
import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.model.Response;

public interface AnalysisService {

	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics);
	
	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics, double threshold);

	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics, boolean projectScores);
	
	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics, 
										   double threshold, boolean projectScores);

	public int getNumResponses();
	
	boolean isPassingPCAQualityChecks();

	public int getNumSignificantEigenvalues();
	
	public double[] getEigenvalues();

	public double getEigenValueCoverage();

	public String getChartData();

	public DecoratedMatrix getResult();
	
	public static final double KAISER_GUTTMANN = 1.0;
	
}
