/**
 * 
 */
package com.adidas.subscription.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.adidas.subscription.exception.ErrorCode;
import com.adidas.subscription.exception.ServiceError;
import com.adidas.subscription.exception.SubscriptionException;
import com.adidas.subscription.exception.SubscriptionExceptionUtils;
import com.adidas.subscription.service.MailSenderService;

/**
 * @author Telmo
 *
 */
@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	private final Logger log = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Value("${email.from}")
	private String from;
	
	@Value("${email.from}")
	private String subject;
	
	@Override
	public boolean sendEmail(String to) throws SubscriptionException {

		final List<ServiceError> errors = new ArrayList<>();
		
		try {
			//probably will be better something with i18n
			String text = "Your subscription has been completed!";
			
			errors.addAll(validatefields(from, to, subject, text));
			
			if(CollectionUtils.isEmpty(errors)) {
				
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(from);
				message.setTo(to);
				message.setSubject(subject);
				message.setText(text);
				
				//here the mail should be sent.. no configuration of email server
//				mailSender.send(message);
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_001));
		} finally {
			SubscriptionExceptionUtils.checkHasErrors(errors);
		}
		
		return true;
	}

	private List<ServiceError> validatefields(String from, String to, String subject, String text) {
		final List<ServiceError> errors = new ArrayList<>();

		if(!StringUtils.hasLength(from)) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_002));
		}
		
		if(!StringUtils.hasLength(to)) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_002));
		}
		
		if(!StringUtils.hasLength(subject)) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_004));
		}
		
		if(!StringUtils.hasLength(text)) {
			errors.add(SubscriptionExceptionUtils.getServiceError(ErrorCode.ERROR_005));
		}
		
		return errors;
	}

}
