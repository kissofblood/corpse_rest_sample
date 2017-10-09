package com.corpse.model.document;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import com.corpse.util.Common;

@Entity
@Table(name = "demand_dealer", schema = "public")
@DynamicInsert
public class DemandDealer extends Document {
	
	private static final long serialVersionUID = 8147381060177746437L;

	@Column(name = "storage")
	private Long storage;
	
	@Column(name = "sum", updatable = false, insertable = false)
	private Double sum;

	@Column(name = "details")
	@Type(type = Common.LIST_JSON_TYPE)
	private List<Map<String, Object>> details;

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public List<Map<String, Object>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, Object>> details) {
		this.details = details;
	}
}
