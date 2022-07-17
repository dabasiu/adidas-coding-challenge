/**
 * 
 */
package com.adidas.subscription.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.adidas.subscription.service.SubscriptionService;

/**
 * @author Telmo
 *
 */
public class AbstractController {
	
	protected SubscriptionService subscriptionService;

	@Autowired
	public void setSubscriptionService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}
	
	
}
