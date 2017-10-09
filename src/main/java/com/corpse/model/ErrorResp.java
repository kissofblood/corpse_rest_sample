package com.corpse.model;

import java.io.Serializable;

public class ErrorResp implements Serializable {

	private static final long serialVersionUID = 1074619249361801611L;

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
