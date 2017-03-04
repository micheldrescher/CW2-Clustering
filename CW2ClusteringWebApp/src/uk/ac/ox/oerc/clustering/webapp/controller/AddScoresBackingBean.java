package uk.ac.ox.oerc.clustering.webapp.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import uk.ac.ox.oerc.clustering.model.Response;
import uk.ac.ox.oerc.clustering.webapp.Util;
import uk.ac.ox.oerc.clustering.webapp.servlets.Captchas;

public class AddScoresBackingBean extends BackingBean {
	
	private String captchaUserInput;
	
	private Response response;

	/**
	 * @return the currentResponse
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * @param currentResponse the currentResponse to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}

	/**
	 * @return the captchaUserInput
	 */
	public String getCaptchaUserInput() {
		return captchaUserInput;
	}

	/**
	 * @param captchaUserInput the captchaUserInput to set
	 */
	public void setCaptchaUserInput(String captchaUserInput) {
		this.captchaUserInput = captchaUserInput;
	}

	public void newResponse() {
		response = getResponseService().createNewResponse();
	}

	public void validateCaptcha(FacesContext context, UIComponent comp, Object value) {
		System.out.println("Validatind Captcha!");
	    String captchaInput = (String) value;
	    String captchaValue = (String) Util.getSession().getAttribute(Captchas.CAPTCHA_KEY);
	    System.out.println("param = " + captchaInput);
	    System.out.println("captchaValue = " + captchaValue);
	    UIInput captchaInputField = (UIInput)comp;
	    
	    if (!captchaInput.equals(captchaValue)) {
	    	captchaInputField.setValid(false);
	    	FacesMessage message = new FacesMessage(
	    			FacesMessage.SEVERITY_ERROR,
	    			"Wrong input", "Your input does not match the captcha");
	    	context.addMessage(captchaInputField.getClientId(context), message);
	    }
	}
	 
	public void saveCurrentResponse() {
		getResponseService().saveResponse(response);
	}

}
