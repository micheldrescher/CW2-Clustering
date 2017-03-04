package uk.ac.ox.oerc.clustering.persistence.jpa.entities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uk.ac.ox.oerc.clustering.model.CreatedModifiedEntity;

@MappedSuperclass
public class JPAPOIDCreatedModified extends JPAPOIDEntity implements CreatedModifiedEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar modifiedDate;

    @PrePersist
    protected void creationTimeStamp(){
        setCreationDate(new GregorianCalendar());
        this.updateTimeStamp();
    }

	@PreUpdate
    protected void updateTimeStamp(){
    	setModificationDate(new GregorianCalendar());
    }

	@Override
	public Calendar getCreationDate() {
		return createdDate;
	}

    private void setCreationDate(GregorianCalendar gregorianCalendar) {
    	createdDate = gregorianCalendar;
	}

	@Override
	public Calendar getModificationDate() {
		return modifiedDate;
	}

	private void setModificationDate(GregorianCalendar gregorianCalendar) {
    	modifiedDate = gregorianCalendar;
	}

}
