package com.corpse.model.document;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import com.corpse.util.Common;

@Entity
@Table(name = "orders", schema = "public")
@DynamicInsert
public class Order extends Document {

	private static final long serialVersionUID = -5705225242535096988L;

	@Column(name = "is_deferment_payment")
	private Boolean isDefermentPayment;
	
	@Column(name = "maturity")
	@Temporal(TemporalType.DATE)
	private Calendar maturity;

	@Column(name = "sum")
	private Double sum;
	
	@Column(name = "paid")
	private Double paid;

	@Column(name = "details")
	@Type(type = Common.LIST_JSON_TYPE)
	private List<Map<String, Object>> details;

	@Column(name = "trade_point")
	private Long tradePoint;

	public Boolean getIsDefermentPayment() {
		return isDefermentPayment;
	}

	public void setIsDefermentPayment(Boolean isDefermentPayment) {
		this.isDefermentPayment = isDefermentPayment;
	}

	public Calendar getMaturity() {
		return maturity;
	}

	public void setMaturity(Calendar maturity) {
		this.maturity = maturity;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Long getTradePoint() {
		return tradePoint;
	}

	public void setTradePoint(Long tradePoint) {
		this.tradePoint = tradePoint;
	}

	public List<Map<String, Object>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, Object>> details) {
		this.details = details;
	}
}
