/**
 * 
 */
package com.adidas.subscription.service;

import com.adidas.subscription.exception.SubscriptionException;

/**
 * @author Telmo
 *
 */
public interface MailSenderService {

	boolean sendEmail(String to) throws SubscriptionException;
}
