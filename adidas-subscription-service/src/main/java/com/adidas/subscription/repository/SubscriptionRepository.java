/**
 * 
 */
package com.adidas.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adidas.subscription.entities.Subscription;

/**
 * @author Telmo
 *
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByEmail(String email);
	
	List<Subscription> findByActiveTrue();
}
