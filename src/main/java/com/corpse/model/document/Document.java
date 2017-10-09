package com.corpse.model.document;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Document implements Serializable {

	private static final long serialVersionUID = 3173725867370106581L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "created", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar created;

	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updated;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "number")
	private Long number;

	@Column(name = "moment")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar moment;

	@Column(name = "applicable")
	private Boolean applicable;

	@Column(name = "owner")
	private String owner;

	@Column(name = "hash_sync")
	private String hashSync;

	@Column(name = "note")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Calendar getMoment() {
		return moment;
	}

	public void setMoment(Calendar moment) {
		this.moment = moment;
	}

	public Boolean getApplicable() {
		return applicable;
	}

	public void setApplicable(Boolean applicable) {
		this.applicable = applicable;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getHashSync() {
		return hashSync;
	}

	public void setHashSync(String hashSync) {
		this.hashSync = hashSync;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

