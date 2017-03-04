/**
 * 
 */
package uk.ac.ox.oerc.clustering.persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Simple global entity management factory bean that caches the entity manager
 * factory
 * 
 * @author Michel Drescher
 *
 */
public class EMFactory {

	private String persistenceUnit;

	private EntityManagerFactory emFactory;

	/*
	 * Lazily acquire the EntityManagerFactory and cache it.
	 */
	public EntityManagerFactory getEMF() {
		if (emFactory != null) return emFactory;
		// executed only once from here on
		emFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		return emFactory;
	}

	/**
	 * @return the persistenceUnit
	 */
	public String getPersistenceUnit() {
		return persistenceUnit;
	}

	/**
	 * @param persistenceUnit
	 *            the persistenceUnit to set
	 */
	public void setPersistenceUnit(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}

}
