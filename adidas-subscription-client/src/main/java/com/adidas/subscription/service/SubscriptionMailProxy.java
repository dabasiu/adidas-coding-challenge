/**
 * 
 */
package com.adidas.subscription.service;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;

/**
 * @author Telmo
 *
 */
public interface SubscriptionMailProxy {

	SubscriptionAPIResponseDTO sendEmail(SubscriptionApiDTO dto);

}
