package uk.ac.ox.oerc.clustering.webapp.controller;

import java.util.List;

import javax.faces.context.FacesContext;

import uk.ac.ox.oerc.clustering.model.Response;

public class ManageDataBackingBean extends BackingBean {

	private List<Response> responses; 
	private int numResponses = 0;
	private boolean allowValidated;

	/**
	 * @return the responses
	 */
	public List<Response> getResponses() {
		return responses;
	}
	
	public boolean isHasResponses() {
		return numResponses > 0;
	}
	
	/**
	 * @return the allowValidated
	 */
	public boolean isAllowValidated() {
		return allowValidated;
	}

	/**
	 * @param allowValidated the allowValidated to set
	 */
	public void setAllowValidated(boolean allowValidated) {
		this.allowValidated = allowValidated;
	}

	public void deleteResponse() {
		Long id = Long.decode(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("responseID"));
		getResponseService().deleteResponse(id);
		fetchResponses();
	}
	
	public void validateResponse() {
		Long id = Long.decode(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("responseID"));
		getResponseService().validateResponse(id);
		fetchResponses();
	}
	
	public void fetchResponses() {
		System.out.println("Fetching all responses? " + allowValidated);
		if (allowValidated) {
			responses = getResponseService().getAllResponses();
		} else {
			responses= getResponseService().getUnvalidatedResponses();
		}
		numResponses = responses.size();
	}
}
