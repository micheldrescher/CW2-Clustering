package uk.ac.ox.oerc.clustering.analysis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.io.plots.DrawableWriterFactory;
import de.erichseifert.gral.io.plots.VectorWriter;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.util.Insets2D;
import flanagan.analysis.PCA;
import flanagan.math.Matrix;
import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.model.Response;
import uk.ac.ox.oerc.clustering.webapp.service.AnalysisService;

public class PCAService implements AnalysisService {

	// actionResult attributes
	private int numResponses;
	private double[] eigenValues;
	private Matrix eigenVectors;
	private Matrix normalisedData;
	private Matrix scores;
	private int numSignificantEigenvalues;
	private double eigenvaluesDataCoverage;
	private String chartData;
	
	private DecoratedMatrix result;

	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics) {
		return performAnalysis(responses, characteristics, PCAService.KAISER_GUTTMANN, true);
	}

	@Override
	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics, double threshold) {
		return performAnalysis(responses, characteristics, threshold, true);
	}

	@Override
	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics, boolean projectScores) {
		return performAnalysis(responses, characteristics, PCAService.KAISER_GUTTMANN, projectScores);
	}
	
	@Override
	public DecoratedMatrix performAnalysis(List<Response> responses, List<Characteristic> characteristics,
										   double threshold, boolean projectScores) {
		//
		// transform the responses into something the PCA class can digest
		//
		// init variables
		double[][] data = new double[responses.size()][characteristics.size()];
		List<String> selectedCharNames = new ArrayList<String>(characteristics.size());
		String[] projectNames = new String[responses.size()];
		// fill the variables - selectedCharNames
		for (Characteristic aChar : characteristics) {
			selectedCharNames.add(aChar.getName());
		}
		// fill the variables - data & project names
		for (int i = 0; i < responses.size(); i++) {
			Response r = responses.get(i);
			projectNames[i] = r.getProject();
			for (int j = 0; j < characteristics.size(); j++) {
				data[i][j] = r.getScoreByCharacteristic(characteristics.get(j));
			}
		}
		//
		// initialise the PCA
		//
		PCA pca = new PCA();
		pca.enterScoresAsRowPerPerson(data);
		pca.enterItemNames(selectedCharNames.toArray(new String[0]));
		pca.enterPersonNames(projectNames);
		// initialise all the attributes
		numResponses = responses.size();
		eigenValues = pca.orderedEigenValues();
		eigenVectors = new Matrix(pca.orderedEigenVectors());
		normalisedData = new Matrix(pca.standardisedScoresAsRowPerPerson());
		scores = getNormalisedData().times(getEigenVectors());
		numSignificantEigenvalues = Util.calculateNumEV(getEigenvalues(), threshold);
		eigenvaluesDataCoverage = calcCoverage();
		chartData = createChart(threshold);
		
		result = projectScores ?
				new DecoratedMatrix(pca.itemNames(), pca.personNames(),
						Util.projectScores(getScores(), getEigenVectors(), getNumSignificantEigenvalues())) :
				new DecoratedMatrix(pca.itemNames(), pca.personNames(), scores);
	
		return result;
	}
	
	private double calcCoverage() {
		double result  = 0;
		for (int i = 0; i < getNumSignificantEigenvalues(); i++) {
			result += getEigenvalues()[i];
		}
		result = result / Util.sumUp(getEigenvalues()) * 100;
		return result;
	}

	private String createChart(double threshold) {
		// prepare data
		@SuppressWarnings("unchecked")
		DataTable barData = new DataTable(Integer.class, Double.class, Double.class);
		for (int i = 0; i < getEigenvalues().length; i++) {
			barData.add(i + 1, getEigenvalues()[i], threshold);
		}
		DataSeries barSeries = new DataSeries(barData, 0, 1);
		DataSeries lineSeries = new DataSeries(barData, 0, 2);

		// Create new bar plot
		BarPlot plot = new BarPlot(barSeries, lineSeries);
		plot.getAxisRenderer(XYPlot.AXIS_X).setMinorTicksVisible(false);
		plot.getAxisRenderer(XYPlot.AXIS_X).setTickStroke(new BasicStroke(0.0f));

		// Format bar appearances
		plot.setInsets(new Insets2D.Double(0, 40.0, 30.0, 0));
		plot.setBarWidth(0.75);
		BarRenderer barRenderer = (BarRenderer) plot.getPointRenderer(barSeries);
		barRenderer.setColor(new Color(0, 33, 71));
		// Format line appearances
		LineRenderer lineRenderer = new DefaultLineRenderer2D();
		lineRenderer.setColor(Color.RED);
		plot.setLineRenderer(lineSeries, lineRenderer);
		// The default point renderer (BarRenderer) needs to be deactivated (or changed)
		plot.setPointRenderer(lineSeries, null);

		// write the image
		DrawableWriterFactory f = DrawableWriterFactory.getInstance();
		VectorWriter writer = (VectorWriter) f.get("image/svg+xml");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Base64.Encoder enc = Base64.getEncoder();
		OutputStream base64os = enc.wrap(baos);
		try {
			writer.write(plot, base64os, 320, 200);
			base64os.flush();
			baos.flush();
			base64os.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("data:image/svg+xml;base64,");
		sb.append(baos.toString());
		return sb.toString();
	}

	@Override
	public int getNumResponses() {
		return numResponses;
	}

	public double[] getEigenvalues() {
		return eigenValues;
	}

	public Matrix getEigenVectors() {
		return eigenVectors;
	}

	public Matrix getNormalisedData() {
		return normalisedData;
	}

	public Matrix getScores() {
		return scores;
	}

	@Override
	public int getNumSignificantEigenvalues() {
		return numSignificantEigenvalues;
	}
	
	public double getEigenValueCoverage() {
		return eigenvaluesDataCoverage;
	}
	
	public String getChartData() {
		return chartData;
	}

	@Override
	public DecoratedMatrix getResult() {
		return result;
	}
	
	@Override
	public boolean isPassingPCAQualityChecks() {
		return 
				// check 0: null or zero eigenvalues means either no analysis, or a faulty one
				getEigenvalues() != null && getEigenvalues().length > 0 
				&&
				// check 1: sum of eigenvalues = number of columns
				Util.equalsWithEps(Util.sumUp(getEigenvalues()), getScores().getNumberOfColumns())
				&&
				// check 2: Square root of eigenvalues matches standard deviation of score columns
				Util.equals(Util.calcSQRTs(getEigenvalues()), Util.calcColumnStdDevs(getScores()));
	}

	private static void printMatrix(String header, Matrix matrix, int digits) {
		System.out.println(header + " = [");
		for (int i = 0; i < matrix.getNrow(); i++) {
			if (matrix instanceof DecoratedMatrix) {
				System.out.print(((DecoratedMatrix) matrix).getPersonNames()[i] + " --> ");
			}
			for (int j = 0; j < matrix.getNcol(); j++) {
				System.out.print(round(matrix.getElement(i, j), digits) + ", ");
			}
			System.out.println();
		}
		System.out.println("]");
	}

	private static void printArray(String header, double[] arr, int digits) {
		System.out.print(header + " = [");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(round(arr[i], digits) + ", ");
		}
		System.out.println("]");
	}

	private static double round(double value, int digits) {
		double factor = Math.pow(10, digits);
		return Math.round(value * factor) / factor;
	}

}
