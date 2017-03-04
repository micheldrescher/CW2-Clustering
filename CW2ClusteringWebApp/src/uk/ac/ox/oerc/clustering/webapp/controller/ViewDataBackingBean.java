package uk.ac.ox.oerc.clustering.webapp.controller;

import java.util.List;

import uk.ac.ox.oerc.clustering.model.Response;

public class ViewDataBackingBean extends BackingBean {
	
	public ViewDataBackingBean() {
	}

	public List<Response> getResponses() {
		return getResponseService().getAllValidatedResponses();
	}
}
