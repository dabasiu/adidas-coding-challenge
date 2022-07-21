package com.adidas.subscription.exception;

import java.io.Serializable;

public final class ServiceError implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2241039430918365801L;

	private String code = null;
	private String message = null;

	public ServiceError() {
	}
	
	public ServiceError(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
