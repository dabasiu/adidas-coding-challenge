/**
 * 
 */
package com.adidas.subscription.service;

import java.util.List;
import java.util.Optional;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.exception.SubscriptionException;

/**
 * @author Telmo
 *
 */
public interface SubscriptionService {

	Long create(SubscriptionApiDTO dto) throws SubscriptionException;
	boolean delete(String email) throws SubscriptionException;
	Optional<SubscriptionApiDTO> retrieveByEmail(String email) throws SubscriptionException;
	List<SubscriptionApiDTO> retrieveAll() throws SubscriptionException;
	
	void processEmailForSubscriptionCreated(SubscriptionApiDTO dto) throws SubscriptionException;

}
