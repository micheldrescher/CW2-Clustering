/**
 * 
 */
package uk.ac.ox.oerc.clustering.model;

import java.util.List;

/**
 * @author oerc0098
 *
 */
public interface Response extends POIDEntity, CreatedModifiedEntity {
	
	public String getProject();
	
	public void setProject(String project);
	
	public String getWebsite();
	
	public void setWebsite(String website);
	
	public boolean isValidated();
	
	public void setValidated(boolean validated);

	public Respondent getRespondent();
	
	public void setRespondent(Respondent respondent);
	
	public List<? extends Score> getScores();
	
	public void setScores(List<? extends Score> scores);

	public List<Characteristic> getCharacteristics();
	
	public String getScoreByCharacteristic(String name);
	
	public Integer getScoreByCharacteristic(Characteristic c);
}
