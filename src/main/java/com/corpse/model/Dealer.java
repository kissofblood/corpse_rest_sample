package com.corpse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dealers", schema = "public")
public class Dealer implements Serializable {

	private static final long serialVersionUID = 1021089697014664314L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "contractor")
	private Long contractor;

	@Column(name = "storage")
	private Long storage;

	@OneToOne
	@JoinColumn(name = "account", referencedColumnName = "id", insertable = false, updatable = false)
	private Account accountObject;

	@Column(name = "account", updatable = false)
	@JsonIgnore
	private Long account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Double getAccountBalance() {
		if(accountObject == null) {
			return 0.0;
		}
		return accountObject.getBalance();
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public Long getContractor() {
		return contractor;
	}

	public Long getStorage() {
		return storage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContractor(Long contractor) {
		this.contractor = contractor;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}
}
