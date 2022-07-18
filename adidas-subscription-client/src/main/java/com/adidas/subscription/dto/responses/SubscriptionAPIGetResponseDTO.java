/**
 * 
 */
package com.adidas.subscription.dto.responses;

import com.adidas.subscription.dto.SubscriptionApiDTO;

/**
 * @author Telmo
 *
 */
public class SubscriptionAPIGetResponseDTO extends SubscriptionAPIResponseDTO {

	private SubscriptionApiDTO dto;

	public SubscriptionApiDTO getDto() {
		return dto;
	}

	public void setDto(SubscriptionApiDTO dto) {
		this.dto = dto;
	}
}
