package uk.ac.ox.oerc.clustering.model;

/**
 * Respondents enter the scores for a given project.
 * This interface models the access interface for respondents, leaving
 * the persistence options to the underlying implementation.
 * 
 * @author Michel Drescher
 */
public interface Respondent extends POIDEntity, CreatedModifiedEntity {

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public String getEmail();

	public void setEmail(String email);

	public String getAffiliation();
	
	public void setAffiliation(String affiliation);
	
}