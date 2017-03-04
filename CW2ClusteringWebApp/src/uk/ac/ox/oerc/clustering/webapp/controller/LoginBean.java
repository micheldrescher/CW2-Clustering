package uk.ac.ox.oerc.clustering.webapp.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import uk.ac.ox.oerc.clustering.webapp.Util;
import uk.ac.ox.oerc.clustering.webapp.service.UserService;

public class LoginBean {

	private UserService userService;
	
	private String password;
	private String username;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public void login() {
		
		boolean result = userService.login(username, password);
		if (result) {
			// get Http Session and store username
			HttpSession session = Util.getSession();
			session.setAttribute("username", username);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
			logout();
		}
	}

	public void logout() {
		System.out.println("logging out!");
		HttpSession session = Util.getSession();
		session.invalidate();
		username = null;
		password = null;
	}
}
