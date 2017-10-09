package com.corpse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storage_balance", schema = "public")
public class StorageBalance implements Serializable {

	private static final long serialVersionUID = -7340981781315205545L;

	@Id
	@Column(name = "storage")
	private Long storage;

	@Column(name = "item")
	private Long item;

	@Column(name = "qty")
	private Double qty;

	@Column(name = "cost")
	private Double cost;

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
