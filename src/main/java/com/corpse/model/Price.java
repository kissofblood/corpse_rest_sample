package com.corpse.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.corpse.util.Common;

@Entity
@Table(name = "prices", schema = "public")
public class Price implements Serializable {

	private static final long serialVersionUID = -1245271589624395633L;

	@Id
	@Column(name = "dealer")
	private Long dealer;

	@Column(name = "price")
	@Type(type = Common.LIST_JSON_TYPE)
	private List<Map<String, Object>> price;

	public Long getDealer() {
		return dealer;
	}

	public void setDealer(Long dealer) {
		this.dealer = dealer;
	}

	public List<Map<String, Object>> getPrice() {
		return price;
	}

	public void setPrice(List<Map<String, Object>> price) {
		this.price = price;
	}
}
