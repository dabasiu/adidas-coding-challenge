/**
 * 
 */
package com.adidas.subscription.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;
import com.adidas.subscription.exception.SubscriptionException;
import com.adidas.subscription.exception.SubscriptionExceptionUtils;

/**
 * @author Telmo
 *
 */
@Controller
@RequestMapping("/email")
public class EmailController extends AbstractController {
	
	private final Logger log = LoggerFactory.getLogger(EmailController.class);

	@PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIResponseDTO sendMailSubscriptionCreated(@RequestBody @Valid SubscriptionApiDTO dto) {
		
		final SubscriptionAPIResponseDTO response = new SubscriptionAPIResponseDTO();
		
		try {
			String to = dto.getEmail();
			
			mailSenderService.sendEmail(to);
			response.setSuccess(true);
			
		} catch (SubscriptionException se) {
			log.error(se.getMessage(), se);
			SubscriptionExceptionUtils.handleSubscriptionException(response, se);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			SubscriptionExceptionUtils.handleUnexpectedException(response);
		}
		
		return response;
	}

}
