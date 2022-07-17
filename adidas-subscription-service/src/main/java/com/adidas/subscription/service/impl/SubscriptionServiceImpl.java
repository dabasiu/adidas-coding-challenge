/**
 * 
 */
package com.adidas.subscription.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.entities.Subscription;
import com.adidas.subscription.repository.SubscriptionRepository;
import com.adidas.subscription.service.SubscriptionService;

import ma.glasnost.orika.MapperFactory;

/**
 * @author Telmo
 *
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	private final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	private SubscriptionRepository subscriptionRepository;
	
	private MapperFactory mapperFactory;
	
	@Autowired
	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, MapperFactory mapperFactory) {
		this.subscriptionRepository = subscriptionRepository;
		this.mapperFactory = mapperFactory;
	}

	@Override
	public Long create(SubscriptionApiDTO dto) {
		
		log.debug("create Subscription");
		
		List<Subscription> existingSubscription = subscriptionRepository.findByEmail(dto.getEmail());
		
		if(!CollectionUtils.isEmpty(existingSubscription)) {
			for(Subscription entity : existingSubscription) {
				if(Boolean.TRUE.equals(entity.getActive())) {
					log.warn("Subscription for email {} already exists!", dto.getEmail());
				}
			}
		}
		
		mapperFactory.classMap(SubscriptionApiDTO.class, Subscription.class);
		Subscription entity = mapperFactory.getMapperFacade().map(dto, Subscription.class);
		
		Subscription created = subscriptionRepository.save(entity);

		return created.getId();
	}
}
