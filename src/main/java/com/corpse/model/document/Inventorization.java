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
@Table(name = "inventorization", schema = "public")
@DynamicInsert
public class Inventorization extends Document {

	private static final long serialVersionUID = 9220202272780669805L;

	@Column(name = "sum", updatable = false, insertable = false)
	private Double sum;
	
	@Column(name = "costprice", updatable = false, insertable = false)
	private Double costprice;

	@Column(name = "details")
	@Type(type = Common.LIST_JSON_TYPE)
	private List<Map<String, Object>> details;

	@Column(name = "trade_point")
	private Long tradePoint;

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

	public List<Map<String, Object>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, Object>> details) {
		this.details = details;
	}

	public Long getTradePoint() {
		return tradePoint;
	}

	public void setTradePoint(Long tradePoint) {
		this.tradePoint = tradePoint;
	}
}
