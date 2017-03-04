package uk.ac.ox.oerc.clustering.persistence.jpa.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import uk.ac.ox.oerc.clustering.model.POIDEntity;

/**
 * Entity implementation class for Entity: JPAPOIDEntity
 *
 */
@MappedSuperclass
public class JPAPOIDEntity implements Serializable, POIDEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private static final long serialVersionUID = 1L;

	public JPAPOIDEntity() {
		super();
	}

	@Override
	public long getPOID() {
		return id;
	}

	public void setPOID(long id) {
		this.id = id;
	}
	
}
