package com.corpse.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.corpse.util.Common;

@Entity
@Table(name = "storage_history", schema = "public")
public class StorageHistory implements Serializable {

	private static final long serialVersionUID = -6386839510805372191L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "moment")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar moment;

	@Column(name = "storage")
	private Long storage;

	@Column(name = "item")
	private Long item;

	@Column(name = "document")
	@Type(type = Common.MAP_JSON_TYPE)
	private Map<String, Object> document;

	@Column(name = "qty")
	private Double qty;

	@Column(name = "cost")
	private Double cost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getMoment() {
		return moment;
	}

	public void setMoment(Calendar moment) {
		this.moment = moment;
	}

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public Map<String, Object> getDocument() {
		return document;
	}

	public void setDocument(Map<String, Object> document) {
		this.document = document;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
}
