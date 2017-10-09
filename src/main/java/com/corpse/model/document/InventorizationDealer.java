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
@Table(name = "inventorization_dealer", schema = "public")
@DynamicInsert
public class InventorizationDealer extends Document {

	private static final long serialVersionUID = 4543858137348708996L;

	@Column(name = "sum", updatable = false, insertable = false)
	private Double sum;
	
	@Column(name = "costprice", updatable = false, insertable = false)
	private Double costprice;
	
	@Column(name = "storage")
	private Long storage;

	@Column(name = "details")
	@Type(type = Common.LIST_JSON_TYPE)
	private List<Map<String, Object>> details;

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getCostprice() {
		return costprice;
	}

	public void setCostprice(Double costprice) {
		this.costprice = costprice;
	}

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public List<Map<String, Object>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, Object>> details) {
		this.details = details;
	}
}
