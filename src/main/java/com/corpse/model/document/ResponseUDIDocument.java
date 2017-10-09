package com.corpse.model.document;

import com.corpse.model.ResponseUDI;

public class ResponseUDIDocument extends ResponseUDI<Long> {

	private static final long serialVersionUID = -1998345450403262165L;

	private Long number;
	
	public Long getNumber() {
		return number;
	}
	
	public void setNumber(Long number) {
		this.number = number;
	}
}
