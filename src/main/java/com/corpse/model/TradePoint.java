package com.corpse.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trade_points", schema = "public")
public class TradePoint implements Serializable {

	private static final long serialVersionUID = 9080799798375341702L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "type")
	private Long type;

	@Column(name = "dealer")
	private Long dealer;

	@Column(name = "trader")
	private String trader;

	@Column(name = "contractor")
	private Long contractor;

	@Column(name = "storage")
	private Long storage;

	@Column(name = "creadit_limit_money")
	private Double creaditLimitMoney;

	@Column(name = "creadit_limit_day")
	private Double creaditLimitDay;

	@Column(name = "payment_type")
	@NotNull
	@Length(min = 1, max = 1)
	@Pattern(regexp = "f|d|i")
	private String paymentType;

	@OneToOne
	@JoinColumn(name = "account", referencedColumnName = "id", insertable = false, updatable = false)
	private Account accountObject;

	@Column(name = "account")
	@JsonIgnore
	private Long account;

	@Column(name = "phone")
	private String phone;

	@Column(name = "lastvisit", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastVisit;

	@Column(name = "hash_sync")
	private String hashSync;

	@Column(name = "phone2")
	private String phone2;

	@Column(name = "visit_period")
	private Integer visitPeriod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Double getCreaditLimitMoney() {
		return creaditLimitMoney;
	}

	public Double getCreaditLimitDay() {
		return creaditLimitDay;
	}

	public Long getDealer() {
		return dealer;
	}

	public Long getContractor() {
		return contractor;
	}

	public Long getType() {
		return type;
	}

	public String getTrader() {
		return trader;
	}

	public Long getStorage() {
		return storage;
	}

	public Double getAccountBalance() {
		if(accountObject == null) {
			return null;
		}

		return accountObject.getBalance();
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCreaditLimitMoney(Double value) {
		this.creaditLimitMoney = value;
	}

	public void setCreaditLimitDay(Double value) {
		this.creaditLimitDay = value;
	}

	public void setDealer(Long dealer) {
		this.dealer = dealer;
	}

	public void setContractor(Long contractor) {
		this.contractor = contractor;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Calendar getLastVisit() {
		return lastVisit;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getHashSync() {
		return hashSync;
	}

	public void setHashSync(String hashSync) {
		this.hashSync = hashSync;
	}

	public Integer getVisitPeriod() {
		return visitPeriod;
	}

	public void setVisitPeriod(Integer visitPeriod) {
		this.visitPeriod = visitPeriod;
	}
}
