/**
 * 
 */
package com.adidas.subscription.service;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;

/**
 * @author Telmo
 *
 */
public interface SubscriptionServiceProxy {

	public SubscriptionAPICreateResponseDTO createSubscription(SubscriptionApiDTO dto);
}
