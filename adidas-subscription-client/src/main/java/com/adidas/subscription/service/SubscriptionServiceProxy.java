/**
 * 
 */
package com.adidas.subscription.service;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetAllResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;

/**
 * @author Telmo
 *
 */
public interface SubscriptionServiceProxy {

	public SubscriptionAPICreateResponseDTO createSubscription(SubscriptionApiDTO dto);
	public SubscriptionAPIResponseDTO cancelSubscription(String email);
	public SubscriptionAPIGetResponseDTO getSubscription(String email);
	public SubscriptionAPIGetAllResponseDTO getAllSubscriptions();
}
