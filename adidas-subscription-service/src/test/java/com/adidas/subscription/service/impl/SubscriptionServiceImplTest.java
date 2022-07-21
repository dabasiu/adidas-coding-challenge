/**
 * 
 */
package com.adidas.subscription.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.exception.ErrorCode;
import com.adidas.subscription.exception.ServiceError;
import com.adidas.subscription.repository.SubscriptionRepository;
import com.adidas.subscription.service.SubscriptionMailProxy;
import com.adidas.subscription.util.Gender;

import ma.glasnost.orika.MapperFactory;

/**
 * @author Telmo
 *
 */
class SubscriptionServiceImplTest {
	
	@Mock
	private SubscriptionRepository subscriptionRepository;
	
	@Mock
	private MapperFactory mapperFactory;
	
	@Mock
	private SubscriptionMailProxy mailService;

	@InjectMocks
	private SubscriptionServiceImpl subscriptionService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testValidatefieldsWithNoErrors() {
		
		List<ServiceError> errors = subscriptionService.validatefields(getSubscriptionDTO());
		
		Assertions.assertEquals(0, errors.size());
	}
	
	@Test
	void testValidatefieldsWithErrors() {
		SubscriptionApiDTO dto = new SubscriptionApiDTO();
		List<ServiceError> errors = subscriptionService.validatefields(dto);
		

		Assertions.assertEquals(3, errors.size());
		Assertions.assertEquals(ErrorCode.ERROR_002.getCode(), errors.get(0).getCode());
		Assertions.assertEquals(ErrorCode.ERROR_009.getCode(), errors.get(1).getCode());
		Assertions.assertEquals(ErrorCode.ERROR_010.getCode(), errors.get(2).getCode());
	}
	
	@Test
	void testValidateEmailWithNoErrors() {
		List<ServiceError> errors = subscriptionService.validateEmail("telmo@email.com");

		Assertions.assertEquals(0, errors.size());
	}
	
	@Test
	void testValidateEmailWithErrors() {
		List<ServiceError> errors = subscriptionService.validateEmail("telmoemail.com");

		Assertions.assertEquals(1, errors.size());
		Assertions.assertEquals(ErrorCode.ERROR_011.getCode(), errors.get(0).getCode());
	}
	
	private SubscriptionApiDTO getSubscriptionDTO() {
		SubscriptionApiDTO dto = new SubscriptionApiDTO();
		dto.setBirthDate(new Date());
		dto.setConsent(true);
		dto.setEmail("name@email.com");
		dto.setFirstName("Telmo");
		dto.setGender(Gender.MALE);
		dto.setNewsletterId(1L);
		return dto;
	}
}
