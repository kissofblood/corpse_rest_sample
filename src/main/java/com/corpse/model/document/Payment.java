package com.corpse.model.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "payments", schema = "public")
@DynamicInsert
public class Payment extends Document {
	
	private static final long serialVersionUID = 6006791887947056111L;

	@Column(name = "sum")
	private Double sum;
	
	@Column(name = "order")
	private Long order;

	@Column(name = "trade_point")
	private Long tradePoint;

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Long getTradePoint() {
		return tradePoint;
	}

	public void setTradePoint(Long tradePoint) {
		this.tradePoint = tradePoint;
	}
}
