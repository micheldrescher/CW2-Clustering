package uk.ac.ox.oerc.clustering.webapp.controller;

import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.webapp.service.ResponseService;

public class BackingBean {

	private MenuBackingBean menuBean;
	private ResponseService responseService;

	public BackingBean() {
		super();
	}

	/**
	 * @return the menuBean
	 */
	public MenuBackingBean getMenuBean() {
		return menuBean;
	}

	/**
	 * @param menuBean the menuBean to set
	 */
	public void setMenuBean(MenuBackingBean menuBean) {
		this.menuBean = menuBean;
	}

	/**
	 * @return the responseService
	 */
	public ResponseService getResponseService() {
		return responseService;
	}

	/**
	 * @param responseService the responseService to set
	 */
	public void setResponseService(ResponseService responseService) {
		this.responseService = responseService;
	}

	public Characteristic[] getCharacteristics() {
		return Characteristic.values();
	}

}