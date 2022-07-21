/**
 * 
 */
package com.adidas.subscription.exception;

import java.util.Collections;
import java.util.List;

/**
 * @author Telmo
 *
 */
public class SubscriptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ServiceError> errorList = Collections.emptyList();
	
	public SubscriptionException() {
		super();
	}
	
	public SubscriptionException(List<ServiceError> errorList) {
		super();
		this.setErrorList(errorList);
	}

	public List<ServiceError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ServiceError> errorList) {
		this.errorList = errorList;
	}
	
}
