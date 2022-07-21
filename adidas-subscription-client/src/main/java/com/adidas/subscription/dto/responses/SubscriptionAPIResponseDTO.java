/**
 * 
 */
package com.adidas.subscription.dto.responses;

import java.util.Collections;
import java.util.List;

/**
 * @author Telmo
 *
 */
public class SubscriptionAPIResponseDTO {

	private boolean success;
	private List<String> messages = Collections.emptyList();
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
