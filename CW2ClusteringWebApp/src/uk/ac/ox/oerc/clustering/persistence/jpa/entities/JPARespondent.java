/**
 * 
 */
package uk.ac.ox.oerc.clustering.persistence.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import uk.ac.ox.oerc.clustering.model.Respondent;

/**
 * JPARespondent is a POJO providing JPA persistence for people providing responses to a clustering survey
 *  
 * @author Michel Drescher
 */
@Entity
@Table(name="Respondent")
@NamedQueries(
		@NamedQuery(name="JPARespondent.findAll", query="Select r from JPARespondent r")
)
public class JPARespondent extends JPAPOIDCreatedModified implements Respondent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The first name of a respondent
	 */
	private String firstName;
	
	/**
	 * THe last name of a respondent
	 */
	private String lastName;

	/**
	 * THe email address of a respondent
	 */
	private String email;
	
	/**
	 * THe affiliation/organisation of a respondent
	 */
	private String affiliation;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the affiliation
	 */
	public String getAffiliation() {
		return affiliation;
	}

	/**
	 * @param affiliation the affiliation to set
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
}
