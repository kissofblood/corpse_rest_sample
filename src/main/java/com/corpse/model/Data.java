package com.corpse.model;

import java.io.Serializable;
import java.util.List;

import com.corpse.model.document.Checklist;
import com.corpse.model.document.Inventorization;
import com.corpse.model.document.Order;

public class Data implements Serializable {

	private static final long serialVersionUID = -587184532779661948L;

	private List<TradePoint> tradePoints;
	private List<Contractor> contractors;
	private List<Order> orders;
	private List<Inventorization> inventorizations;
	private List<Item> items;
	private List<Collection> collections;
	private List<TradePointType> tradePointTypes;
	private List<Dealer> dealers;
	private List<Storage> storage;
	private List<Checklist> checklists;
	private List<Setting> settings;

	public List<TradePoint> getTradePoints() {
		return tradePoints;
	}

	public void setTradePoints(List<TradePoint> tradePoints) {
		this.tradePoints = tradePoints;
	}

	public List<Contractor> getContractors() {
		return contractors;
	}

	public void setContractors(List<Contractor> contractors) {
		this.contractors = contractors;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Inventorization> getInventorizations() {
		return inventorizations;
	}

	public void setInventorizations(List<Inventorization> inventorizations) {
		this.inventorizations = inventorizations;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public List<TradePointType> getTradePointTypes() {
		return tradePointTypes;
	}

	public void setTradePointTypes(List<TradePointType> tradePointTypes) {
		this.tradePointTypes = tradePointTypes;
	}

	public List<Dealer> getDealers() {
		return dealers;
	}

	public void setDealers(List<Dealer> dealers) {
		this.dealers = dealers;
	}

	public List<Storage> getStorage() {
		return storage;
	}

	public void setStorage(List<Storage> storage) {
		this.storage = storage;
	}

	public List<Checklist> getChecklists() {
		return checklists;
	}

	public void setChecklists(List<Checklist> checklists) {
		this.checklists = checklists;
	}

	public List<Setting> getSettings() {
		return settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}
}
