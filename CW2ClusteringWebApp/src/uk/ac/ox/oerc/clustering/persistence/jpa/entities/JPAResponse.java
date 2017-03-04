/**
 * 
 */
package uk.ac.ox.oerc.clustering.persistence.jpa.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.ac.ox.oerc.clustering.model.Characteristic;
import uk.ac.ox.oerc.clustering.model.Respondent;
import uk.ac.ox.oerc.clustering.model.Response;
import uk.ac.ox.oerc.clustering.model.Score;

/**
 * A JPAResponse models the answers given by a respondent to the CW2 survey on cloud characteristics importance.
 * 
 * The model hardcodes the thirteen NIST characteristics into this class - a more flexible implementation should
 * normalise this using dimensions and mapping tables. This implementation, however, is a quick hack.
 * 
 * @author Michel Drescher
 */
@Entity
@Table(name="Response")
@NamedQueries({
	@NamedQuery(name="Response.findAll", query="Select r from JPAResponse r order by r.project"),
	@NamedQuery(name="Response.findAllValidated", query="Select r from JPAResponse r where r.validated = true order by r.project"),
	@NamedQuery(name="Response.findUnvalidated", query="Select r from JPAResponse r where r.validated = false order by r.project")
})
public class JPAResponse extends JPAPOIDCreatedModified implements Response {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A response at first is "tainted" until its entry has been manually validated.
	 */
	private boolean validated = false;
	
	//the project
	private String project;
	
	//the project's website
	private String website;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private JPARespondent respondent;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<JPAScore> scores;
	@Transient
	private List<Characteristic> characteristics;

	public JPAResponse() {
		setValidated(false);
		setProject("");
		setWebsite("");
		setRespondent(new JPARespondent());
		// initialise scores list
		List<JPAScore> l = new ArrayList<JPAScore>();
		for (Characteristic c : Characteristic.values()) {
			JPAScore s = new JPAScore();
			s.setCharacteristic(c);
			s.setValue(0);
			l.add(s);
		}
		Collections.sort(l);
		setScores(l);
	}
	
	/**
	 * @return the validated
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * @param validated the validated to set
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param url the website to set
	 */
	public void setWebsite(String url) {
		// make sure it is prefixed with http://
		if (url != null && !url.startsWith("http://")) {
			url = "http://" + url;
		}
		this.website = url;
	}

	/**
	 * @return the respondent
	 */
	public Respondent getRespondent() {
		return respondent;
	}

	/**
	 * @param respondent the respondent to set
	 */
	public void setRespondent(Respondent respondent) {
		this.respondent = (JPARespondent)respondent;
	}

	@Override
	public List<? extends Score> getScores() {
		return scores;
	}

	@Override
	public void setScores(List<? extends Score> scores) {
		this.scores = (List<JPAScore>)scores;
		Collections.sort(this.scores);
		characteristics = new ArrayList<Characteristic>();
		for (Score s : this.scores) {
			characteristics.add(s.getCharacteristic());
		}
	}

	@Override
	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	@Override
	public String getScoreByCharacteristic(String name) {
		Characteristic c = Characteristic.getByName(name);
		return getScoreByCharacteristic(c).toString();
	}

	public Integer getScoreByCharacteristic(Characteristic c) {
		for (Score s : scores) {
			if (s.getCharacteristic().equals(c)) return s.getValue();
		}
		return null;
	}

}
