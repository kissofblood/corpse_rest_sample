package com.corpse.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "images", schema = "public")
public class Image implements Serializable {

	private static final long serialVersionUID = 7659904715045367183L;

	@Id
	@Column(name = "unique_name")
	private String uniqueName;

	@Column(name = "created", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar created;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "fullpath")
	private String fullPath;

	@Column(name = "mime_type")
	private String mimeType;

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public Calendar getCreated() {
		return created;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFullPath() {
		return this.fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
