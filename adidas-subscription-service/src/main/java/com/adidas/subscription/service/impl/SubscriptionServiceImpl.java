/**
 * 
 */
package com.adidas.subscription.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;
import com.adidas.subscription.entities.Subscription;
import com.adidas.subscription.exception.ErrorCode;
import com.adidas.subscription.exception.ServiceError;
import com.adidas.subscription.exception.SubscriptionException;
import com.adidas.subscription.exception.SubscriptionExceptionUtils;
import com.adidas.subscription.repository.SubscriptionRepository;
import com.adidas.subscription.service.SubscriptionMailProxy;
import com.adidas.subscription.service.SubscriptionService;
import com.adidas.subscription.util.StopWatchUtils;

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
	
	private SubscriptionMailProxy mailService;
	
	@Autowired
	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, MapperFactory mapperFactory, SubscriptionMailProxy mailService) {
		this.subscriptionRepository = subscriptionRepository;
		this.mapperFactory = mapperFactory;
		this.mailService = mailService;
	}

	@Override
	public Long create(SubscriptionApiDTO dto) throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		final StopWatch sw = createStopWatch("create subscription");
		
		try {
			log.debug("create Subscription");
			
			iterateStopWatchOperation(sw, "validatefields");
			errors.addAll(validatefields(dto));
			
			if(CollectionUtils.isEmpty(errors)) {
				
				iterateStopWatchOperation(sw, "findByEmail");
				List<Subscription> existingSubscriptions = subscriptionRepository.findByEmail(dto.getEmail());

				iterateStopWatchOperation(sw, "validate existing data");
				
				Subscription existingSubscription = existingSubscriptions.stream().findFirst().orElse(null);
				
				if(existingSubscription != null && Boolean.TRUE.equals(existingSubscription.getActive())) {
					log.warn("Subscription for email {} already exists", dto.getEmail());
					errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_006, dto.getEmail()));
				}
				
				if(CollectionUtils.isEmpty(errors)) {
					
					iterateStopWatchOperation(sw, "mapper dto to entity");
					mapperFactory.classMap(SubscriptionApiDTO.class, Subscription.class);
					
					if(existingSubscription != null) {
						mapperFactory.getMapperFacade().map(dto, existingSubscription);
					} else {
						existingSubscription = mapperFactory.getMapperFacade().map(dto, Subscription.class);
					}
					
					iterateStopWatchOperation(sw, "save");
					
					Subscription created = subscriptionRepository.save(existingSubscription);
					
					return created.getId();
				}
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			log.debug(logStopWatch(sw));
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
		
		return null;
	}
	
	@Async
	@Override
	public void processEmailForSubscriptionCreated(SubscriptionApiDTO dto) throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		final StopWatch sw = createStopWatch("processEmailForSubscriptionCreated");
		
		try {
			log.debug("processEmailForSubscriptionCreated");
			iterateStopWatchOperation(sw, "findByEmailAndActiveTrue");
			
			List<Subscription> existingSubscriptions = subscriptionRepository.findByEmailAndActiveTrue(dto.getEmail());
			
			Subscription entity = existingSubscriptions.stream().findFirst().orElse(null);
			
			if(entity != null) {

				iterateStopWatchOperation(sw, "sendEmail");
				
				//send email.....
				SubscriptionAPIResponseDTO response = mailService.sendEmail(dto);
				
				if(response.getSuccess()) {

					iterateStopWatchOperation(sw, "save entity");
					
					entity.setMailSent(true);
					entity.setMailSentWhen(new Date());
					subscriptionRepository.save(entity);
				} else {
					log.warn("Email to {} corresponding to subscription was not sent", dto.getEmail());
				}
			} else {
				log.warn("Subscription for email {} not found", dto.getEmail());
			}
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			log.debug(logStopWatch(sw));
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
	}

	@Override
	public boolean delete(String email) throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		
		try {
			log.debug("cancel Subscription");
			
			errors.addAll(validateEmail(email));
			
			if(CollectionUtils.isEmpty(errors)) {

				Subscription entity = subscriptionRepository.findByEmail(email).stream().findFirst().orElse(null);
				
				if(entity != null) {
					if(Boolean.TRUE.equals(entity.getActive())) {
						entity.setActive(false);
						subscriptionRepository.save(entity);
					} else {
						log.warn("Subscription for email {} already cancelled", email);
						errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_008, email));
					}
				} else {
					log.warn("Subscription for email {} not found", email);
					errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_007, email));
				}
				
				return true;
			}
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
		
		return false;
	}

	@Override
	public Optional<SubscriptionApiDTO> retrieveByEmail(String email) throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		SubscriptionApiDTO dto = null;
		
		try {
			log.debug("retrieveByEmail Subscription");
			
			errors.addAll(validateEmail(email));
			
			if(CollectionUtils.isEmpty(errors)) {

				Subscription entity = subscriptionRepository.findByEmailAndActiveTrue(email).stream().findFirst().orElse(null);
				
				if(entity != null) {
					mapperFactory.classMap(Subscription.class, SubscriptionApiDTO.class);
					dto = mapperFactory.getMapperFacade().map(entity, SubscriptionApiDTO.class);
				} else {
					log.warn("Subscription for email {} not found", email);
					errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_007, email));
				}
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
		
		return Optional.ofNullable(dto);
	}

	@Override
	public List<SubscriptionApiDTO> retrieveAll() throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		final List<SubscriptionApiDTO> dtos = new ArrayList<>();
		
		try {
			log.debug("retrieveAll");
		
			List<Subscription> entities = subscriptionRepository.findByActiveTrue();
			
			mapperFactory.classMap(Subscription.class, SubscriptionApiDTO.class);
			
			dtos.addAll(mapperFactory.getMapperFacade().mapAsList(entities, SubscriptionApiDTO.class));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
		
		return dtos;
	}

	protected List<ServiceError> validatefields(SubscriptionApiDTO dto) {
		final List<ServiceError> errors = new ArrayList<>();

		if(!StringUtils.hasLength(dto.getEmail())) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_002));
		}
		
		if(dto.getBirthDate() == null) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_009));
		}
		
		if(!dto.isConsent()) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_010));
		}
		
		return errors;
	}
	
	protected List<ServiceError> validateEmail(String email) {
		final List<ServiceError> errors = new ArrayList<>();
		

		if (!StringUtils.hasLength(email) || !email.matches("^(.+)@(.+)$")) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_011, email));
		}
		
		return errors;
	}
	
	private StopWatch createStopWatch(String str) {
		return new StopWatch(str);
	}
	
	private void iterateStopWatchOperation(final StopWatch sw, String operation) {
		if(sw != null) {
			stopStopWatch(sw);
			sw.start(operation);	
		}
	}
	
	private String logStopWatch(final StopWatch sw) {
		if(sw != null) {
			stopStopWatch(sw);
			return StopWatchUtils.prettyPrintStopWatchMilis(sw);
		}
		
		return "";
	}
	
	private void stopStopWatch(final StopWatch sw) {
		if(sw != null && sw.isRunning()) {
			sw.stop();
		}
	}
}
