/**
 * 
 */
package uk.ac.ox.oerc.clustering.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIForm;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import uk.ac.ox.oerc.clustering.analysis.ClusterSolution;
import uk.ac.ox.oerc.clustering.analysis.DecoratedMatrix;
import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.model.Response;
import uk.ac.ox.oerc.clustering.webapp.model.Preset;
import uk.ac.ox.oerc.clustering.webapp.model.SolutionModel;
import uk.ac.ox.oerc.clustering.webapp.service.AnalysisService;
import uk.ac.ox.oerc.clustering.webapp.service.ClusterService;

/**
 * This bean is responsible for managing the responses submitted by respondents
 * 
 * @author Michel Drescher
 */
public class ClusterAnalysisBackingBean extends BackingBean {

	//
	// managed beans
	//
	private AnalysisService analysisService;
	private ClusterService clusterService;

	//
	// form selected attributes
	//
	// analysis
	private Preset preset = Preset.All;
	private List<Characteristic> essentialSelection;
	private List<Characteristic> commonSelection;
	private boolean includeUnvalidated = false;
	private boolean overrideThreshold = false;
	private double customThreshold = 1.0;
	private boolean projectScores = true;
	
	// clustering
	private double minDistance = 0.01;
	// private int minClusterSize = 2;
	// private int minCharacteristics = 1;
	// private double minSNR = 1.1;

	// action attributes
	List<SolutionModel> solutions;
	// UI options
	private boolean showPCAResults = false;

	/*******************/
	/** Managed beans **/
	/*******************/

	/**
	 * @return the analysisService
	 */
	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	/**
	 * @param analysisService
	 *            the analysisService to set
	 */
	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	/**
	 * @return the clusterService
	 */
	public ClusterService getClusterService() {
		return clusterService;
	}

	/**
	 * @param clusterService
	 *            the clusterService to set
	 */
	public void setClusterService(ClusterService clusterService) {
		this.clusterService = clusterService;
	}

	/**************************/
	/** Form possible values **/
	/**************************/

	public Preset[] getPresets() {
		return Preset.values();
	}

	public List<Characteristic> getNistFinal() {
		return Characteristic.getFinalNIST();
	}

	public List<Characteristic> getNistDraft() {
		return Characteristic.getDraftNIST();
	}

	/***********************/
	/** Form bean members **/
	/***********************/

	public Preset getPreset() {
		return preset;
	}

	public void setPreset(Preset preset) {
		this.preset = preset;
	}

	/**
	 * @return the essentialSelection
	 */
	public List<Characteristic> getEssentialSelection() {
		return essentialSelection;
	}

	/**
	 * @param essentialSelection
	 *            the essentialSelection to set
	 */
	public void setEssentialSelection(List<Characteristic> essentialSelection) {
		this.essentialSelection = essentialSelection;
	}

	/**
	 * @return the commonSelection
	 */
	public List<Characteristic> getCommonSelection() {
		return commonSelection;
	}

	/**
	 * @param commonSelection
	 *            the commonSelection to set
	 */
	public void setCommonSelection(List<Characteristic> commonSelection) {
		this.commonSelection = commonSelection;
	}
	
	public boolean isIncludeUnvalidated() {
		return includeUnvalidated;
	}
	
	public void setIncludeUnvalidated(boolean includeUnvalidated) {
		this.includeUnvalidated = includeUnvalidated;
	}

	public boolean isOverrideThreshold() {
		return overrideThreshold;
	}

	public void setOverrideThreshold(boolean overrideThreshold) {
		this.overrideThreshold = overrideThreshold;
	}

	public double getCustomThreshold() {
		return customThreshold;
	}

	public void setCustomThreshold(double customThreshold) {
		this.customThreshold = customThreshold;
	}

	public boolean isProjectScores() {
		return projectScores;
	}

	public void setProjectScores(boolean projectScores) {
		this.projectScores = projectScores;
	}

	/**
	 * @return the minDistance
	 */
	public double getMinDistance() {
		return minDistance;
	}

	/**
	 * @param minDistance
	 *            the minDistance to set
	 */
	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	/**
	 * @return the showPCAResults
	 */
	public boolean isShowPCAResults() {
		return showPCAResults;
	}

	/**
	 * @param showPCAResults
	 *            the showPCAResults to set
	 */
	public void setShowPCAResults(boolean showPCAResults) {
		this.showPCAResults = showPCAResults;
	}

	/************************************/
	/** ManagedBean controller methods **/
	/************************************/

	/**
	 * Validates cross UIComponent for analysis options
	 * 
	 * @param e
	 */
	public void validateAnalysisOptions(ComponentSystemEvent e) {
		// get the necessary form elements
		UIForm form 					 = (UIForm) e.getComponent();
		UISelectOne presetInput     	 = (UISelectOne) form.findComponent("presetsID");
		UISelectMany essentialsInput 	 = (UISelectMany) form.findComponent("essentialsID");
		UISelectMany commonsInput		 = (UISelectMany) form.findComponent("commonsID");
		
		//
		// 1. Preset validation
		//
		// Obtain the user's selections in the form elements
		Preset selectedPreset = (Preset) presetInput.getValue();
		int numEssential = ((List<?>)essentialsInput.getValue()).size();
		int numCommons = ((List<?>)commonsInput.getValue()).size();
		// validate
		if (Preset.Custom.equals(selectedPreset) && (numEssential + numCommons) < 2) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("analysisFormID", new FacesMessage("At least two Characteristics must be selected for a custom analysis."));
			fc.renderResponse();
		}
	}

	public void performAnalysis() {
		solutions = null;
		//
		// run the PCA
		//
		DecoratedMatrix projectedScores = null;
		List<Characteristic> selectedCharacteristics = preset.getMappedCharacteristics();
		// preset "custom" requires filling from the UI
		if (preset.equals(Preset.Custom)) {
			selectedCharacteristics = new ArrayList<>();
			selectedCharacteristics.addAll(essentialSelection);
			selectedCharacteristics.addAll(commonSelection);
		}
		
		// include all responses, or only validated ones?
		List<Response> responses = isIncludeUnvalidated() ?  
				getResponseService().getAllResponses() :
				getResponseService().getAllValidatedResponses();
				
		if (isOverrideThreshold()) {
			projectedScores = getAnalysisService().performAnalysis(
					responses, selectedCharacteristics, getCustomThreshold(), isProjectScores());
		} else { 
			projectedScores = getAnalysisService().performAnalysis(
					responses, selectedCharacteristics, isProjectScores());
		}
		//
		// cluster the projected scores
		//
		if (isPassingPCAQA()) {
			List<ClusterSolution> result = getClusterService().clusterProjects(projectedScores, getMinDistance(), 1, 1,
					1.1);
			solutions = new ArrayList<SolutionModel>();
			for (ClusterSolution cs : result) {
				solutions.add(new SolutionModel(cs, selectedCharacteristics));
			}
			// decorate the solution(s) with colour gradients etc
			// solutions = new ArrayList<SolutionModel>(results.size());
			// for (List<ClusterWithMean> result : results) {
			// SolutionModel aModel = new SolutionModel(result);
			// solutions.add(aModel);
			// }
		}
	}

	public boolean isShowPCA() {
		return isShowPCAResults() && isPassingPCAQA();
//		return "performAnalysis".equals(getMenuBean().getRequestedAction()) && isShowPCAResults() && isPassingPCAQA();
	}

	public boolean isShowPCAError() {
		return !isPassingPCAQA();
//		return "performAnalysis".equals(getMenuBean().getRequestedAction()) && !isPassingPCAQA();
	}

	public boolean isShowClustering() {
		return isPassingPCAQA();
//		return "performAnalysis".equals(getMenuBean().getRequestedAction()) && isPassingPCAQA();
	}

	public boolean isPassingPCAQA() {
		// calling this method will cause the PCA analysis to perform!
		return getAnalysisService().isPassingPCAQualityChecks();
	}

	public int getNumResponses() {
		return getAnalysisService().getNumResponses();
	}

	public int getNumSignificantEigenvalues() {
		return getAnalysisService().getNumSignificantEigenvalues();
	}

	public double getEigenValueCoverage() {
		return getAnalysisService().getEigenValueCoverage();
	}

	public String getChartData() {
		return getAnalysisService().getChartData();

	}

	public List<SolutionModel> getSolutions() {
		return solutions;
	}

	/***********/
	/** Other **/
	/***********/

	/**
	 * Handles exceptions and displays them to the user
	 * 
	 * @param ex
	 */
	protected void handleException(RuntimeException ex) {
		StringBuffer details = new StringBuffer();
		Throwable causes = ex;
		while (causes.getCause() != null) {
			details.append(ex.getMessage());
			details.append("    Caused by:");
			details.append(causes.getCause().getMessage());
			causes = causes.getCause();
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), details.toString());
		FacesContext.getCurrentInstance().addMessage("errorTag", message);
	}

}
