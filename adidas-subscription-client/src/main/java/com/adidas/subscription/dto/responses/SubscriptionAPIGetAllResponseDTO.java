/**
 * 
 */
package com.adidas.subscription.dto.responses;

import java.util.List;

import com.adidas.subscription.dto.SubscriptionApiDTO;

/**
 * @author Telmo
 *
 */
public class SubscriptionAPIGetAllResponseDTO extends SubscriptionAPIResponseDTO {

	private List<SubscriptionApiDTO> dtos;

	public List<SubscriptionApiDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<SubscriptionApiDTO> dtos) {
		this.dtos = dtos;
	}
}
