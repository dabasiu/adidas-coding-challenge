/**
 * 
 */
package com.adidas.subscription.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.adidas.subscription.service.MailSenderService;

/**
 * @author Telmo
 *
 */
public abstract class AbstractController {
	
	protected MailSenderService mailSenderService;

	@Autowired
	public void setMailSenderService(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}
	
	
}
