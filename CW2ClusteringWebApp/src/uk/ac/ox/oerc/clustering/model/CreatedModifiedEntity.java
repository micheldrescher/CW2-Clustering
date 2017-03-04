/**
 * 
 */
package uk.ac.ox.oerc.clustering.model;

import java.util.Calendar;

/**
 * Provides access to creation date and modification date.
 * 
 * @author Michel Drescher
 */
public interface CreatedModifiedEntity {

	public Calendar getCreationDate();
	
	public Calendar getModificationDate();
	
}
