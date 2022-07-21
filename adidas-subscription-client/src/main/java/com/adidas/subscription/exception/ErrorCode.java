/**
 * 
 */
package com.adidas.subscription.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Telmo
 *
 */
@Getter
@ToString
@AllArgsConstructor
public enum ErrorCode {
	
	ERROR_001("S001", "An unexpected error occurred"),
	ERROR_002("S002", "Email is not present"),
	ERROR_003("S003", "Email is not valid"),
	ERROR_004("S004", "Subject is not present"),
	ERROR_005("S005", "Message is not present"),
	ERROR_006("S006", "Subscription for email %s already exists"),
	ERROR_007("S007", "Subscription for email %s not found"),
	ERROR_008("S008", "Subscription for email %s already cancelled"),
	ERROR_009("S009", "Birth Date is not present"),
	ERROR_010("S010", "Consent must be true"),
	ERROR_011("S011", "Email %s don't have a valid format");

	private String code;
	private String message;
}
