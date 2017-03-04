/**
 * 
 */
package uk.ac.ox.oerc.clustering.persistence.jpa.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import uk.ac.ox.oerc.clustering.model.Response;
import uk.ac.ox.oerc.clustering.persistence.jpa.EMFactory;
import uk.ac.ox.oerc.clustering.persistence.jpa.entities.JPAResponse;
import uk.ac.ox.oerc.clustering.webapp.service.ResponseService;

/**
 * Implementation for Response Service interface using JPA.
 * 
 * @author Michel Drescher
 *
 */
public class JPAResponseService implements ResponseService {

	// keep an application-wide cache of the JPA entity manager for this app
	private EMFactory emFactory;

	/**
	 * @return the emFactory
	 */
	public EMFactory getEmFactory() {
		return emFactory;
	}

	/**
	 * @param emFactory
	 *            the emFactory to set
	 */
	public void setEmFactory(EMFactory emFactory) {
		this.emFactory = emFactory;
	}

	/*
	 * @see uk.ac.ox.oerc.clustering.service.ResponseService#getAllResponses()
	 */
	@Override
	public List<Response> getAllResponses() {
		EntityManager em = emFactory.getEMF().createEntityManager();
		Query query = em.createNamedQuery("Response.findAll");
		try {
			return getResponses(query);
		} finally {
			em.close();
		}
	}

	/*
	 * @see uk.ac.ox.oerc.clustering.service.ResponseService#getAllResponses()
	 */
	@Override
	public List<Response> getAllValidatedResponses() {
		EntityManager em = emFactory.getEMF().createEntityManager();
		Query query = em.createNamedQuery("Response.findAllValidated");
		try {
			return getResponses(query);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Response> getUnvalidatedResponses() {
		EntityManager em = emFactory.getEMF().createEntityManager();
		Query query = em.createNamedQuery("Response.findUnvalidated");
		try {
			return getResponses(query);
		} finally {
			em.close();
		}
	}
	
	private List<Response> getResponses(Query aQuery) {
		List<?> tempList = aQuery.getResultList();
		// convert to type-safe list
		List<Response> responses = new ArrayList<Response>();
		for (Object response : tempList) {
			responses.add((Response)response);
		}
		return responses;
	}

	@Override
	public Response createNewResponse() {
		return new JPAResponse();
	}

	@Override
	public void saveResponse(Response response) {
		EntityManager em = emFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		try {
			em.persist(response);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void validateResponse(long id) {
		EntityManager em = emFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		try {
	       JPAResponse r = em.find(JPAResponse.class, id);
	       r.setValidated(true);
	       em.merge(r);
	       em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteResponse(long id) {
		EntityManager em = emFactory.getEMF().createEntityManager();
		em.getTransaction().begin();
		try {
	       JPAResponse r = em.find(JPAResponse.class, id);
	       em.remove(r);
	        em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

}
