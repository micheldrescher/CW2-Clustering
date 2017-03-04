/**
 * 
 */
package uk.ac.ox.oerc.clustering.model;

/**
 * @author Michel Drescher
 */
public interface Score extends POIDEntity, CreatedModifiedEntity, Comparable<Score> {
	
	public Characteristic getCharacteristic();
	
	public void setCharacteristic(Characteristic characteristic);

	public Integer getValue();
	
	public void setValue(Integer value);
	
}
