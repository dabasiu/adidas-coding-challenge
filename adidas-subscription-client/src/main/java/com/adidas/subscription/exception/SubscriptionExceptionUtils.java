/**
 * 
 */
package com.adidas.subscription.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;

/**
 * @author Telmo
 *
 */
public final class SubscriptionExceptionUtils {

	//utility class
	private SubscriptionExceptionUtils () {
		
	}
	
	public static void checkHasErrors(List<ServiceError> serviceErrors) throws SubscriptionException {
		if(!CollectionUtils.isEmpty(serviceErrors)) {
			throw new SubscriptionException(serviceErrors);
		}
	}
	
	public static void handleSubscriptionException(SubscriptionAPIResponseDTO response, SubscriptionException se) {
		if(!CollectionUtils.isEmpty(se.getErrorList())) {
			response.setMessages(se.getErrorList().stream().map(ServiceError::getMessage).collect(Collectors.toList()));
		}
		response.setSuccess(false);
	}
	
	public static ServiceError getServiceError(ErrorCode errorCode, String... args) {
		String message = args != null ? String.format(errorCode.getMessage(), args) : errorCode.getMessage();
				
		return new ServiceError(errorCode.getCode(), message);
	}
	
	public static void handleUnexpectedException(SubscriptionAPIResponseDTO response) {
		response.setSuccess(false);
		response.setMessages(Arrays.asList(ErrorCode.ERROR_001.getMessage()));
	}
}
