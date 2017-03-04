package uk.ac.ox.oerc.clustering.webapp.controller;

public class MenuBackingBean {

	private String requestedAction;

	public MenuBackingBean() {
	}
	
	/**
	 * @return the requestedAction
	 */
	public String getRequestedAction() {
		return requestedAction;
	}
	
	public void setRequestedAction(String requestedAction) {
		this.requestedAction = requestedAction;
	}

	public String handleClick(String action) {
		requestedAction = action;
		return getRequestedAction();
	}
	
	public String selection(String current) {
		if (requestedAction == null) return "";
		return requestedAction.contains(current) ? " selected" : "";
	}

}
