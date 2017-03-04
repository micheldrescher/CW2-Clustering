/**
 * 
 */
package uk.ac.ox.oerc.clustering.persistence.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.model.Score;

/**
 * @author oerc0098
 *
 */
@Entity
@Table(name="Score")
public class JPAScore extends JPAPOIDCreatedModified implements Score {

	@Enumerated(EnumType.STRING)
	private Characteristic characteristic;
	
	private Integer value;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see uk.ac.ox.oerc.clustering.model.Score#getCharacteristic()
	 */
	@Override
	public Characteristic getCharacteristic() {
		return characteristic;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ox.oerc.clustering.model.Score#setCharacteristic(uk.ac.ox.oerc.clustering.model.Characteristic)
	 */
	@Override
	public void setCharacteristic(Characteristic characteristic) {
		this.characteristic = characteristic;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ox.oerc.clustering.model.Score#getValue()
	 */
	@Override
	public Integer getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see uk.ac.ox.oerc.clustering.model.Score#setValue(java.lang.Integer)
	 */
	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int compareTo(Score o) {
		return getCharacteristic().compareTo(o.getCharacteristic());
	}

}
