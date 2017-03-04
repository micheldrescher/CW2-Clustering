package uk.ac.ox.oerc.clustering.persistence.jpa.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import uk.ac.ox.oerc.clustering.persistence.jpa.EMFactory;
import uk.ac.ox.oerc.clustering.webapp.service.UserService;

public class JPAUserService implements UserService {

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

	@Override
	public boolean login(String username, String password) {
		EntityManager em = emFactory.getEMF().createEntityManager();
		Query aQuery = em.createNativeQuery("select count(*) from clustering.users u where u.username=?1 and u.password=?2");
		aQuery.setParameter(1, username);
		aQuery.setParameter(2,  password);
		try {
			long numUsers = (long) aQuery.getSingleResult();
			if (numUsers == 1) return true;
		} finally {
			em.close();
		}
		return false;
	}

}
