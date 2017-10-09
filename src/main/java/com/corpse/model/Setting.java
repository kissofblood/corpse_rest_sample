package com.corpse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings", schema = "public")
public class Setting implements Serializable {

	private static final long serialVersionUID = -7805968938914567008L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "value")
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
