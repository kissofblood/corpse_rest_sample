package com.corpse.model;

import java.io.Serializable;

public class ResponseUDI<T> implements Serializable {

	private static final long serialVersionUID = 6937077462907706766L;

	public enum TypeOperation { INSERT, UPDATE, DELETE }

	private int count;
	private String operation;
	private T id;

	public int getCount() {
		return count;
	}

	public String getOperation() {
		return operation;
	}

	public T getId() {
		return id;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setOperation(TypeOperation operation) {
		this.operation = operation.toString();
	}

	public void setId(T id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("{count: %s, operation: %s, id: %s}", String.valueOf(count), operation, String.valueOf(id));
	}
}
