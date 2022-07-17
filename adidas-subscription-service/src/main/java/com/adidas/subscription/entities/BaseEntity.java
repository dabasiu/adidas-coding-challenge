package com.adidas.subscription.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<ID> {

	@Column(name = "ctime")
	private Date creationTime;

	@Column(name = "utime")
	private Date modificationTime;

	@Version
	private Long version = 0L;

	@Column(name="active")
	private Boolean active;
	
	public abstract ID getId();

	public Date getCreationTime() {
		return creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public Long getVersion() {
		return version;
	}

	@PrePersist
	public void prePersist() {
		Date now = new Date();
		this.creationTime = now;
		this.modificationTime = now;
		
		if (this.version == null) {
			this.version = 0L;
		}
	}

	@PreUpdate
	public void preUpdate() {
		Date now = new Date();
		this.modificationTime = now;
		
		if (this.version == null) {
			this.version = 0L;
		}
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
 

}
