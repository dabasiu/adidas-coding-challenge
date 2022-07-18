/**
 * 
 */
package com.adidas.subscription.service;

import java.util.List;
import java.util.Optional;

import com.adidas.subscription.dto.SubscriptionApiDTO;

/**
 * @author Telmo
 *
 */
public interface SubscriptionService {

	Long create(SubscriptionApiDTO dto);
	boolean delete(Long id);
	Optional<SubscriptionApiDTO> retrieveById(Long id);
	List<SubscriptionApiDTO> retrieveAll();

}
