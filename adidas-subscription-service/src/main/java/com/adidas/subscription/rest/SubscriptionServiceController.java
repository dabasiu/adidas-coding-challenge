/**
 * 
 */
package com.adidas.subscription.rest;

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
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;

/**
 * @author Telmo
 *
 */
@Controller
@RequestMapping("/subscriptions")
public class SubscriptionServiceController extends AbstractController {
	
	private final Logger log = LoggerFactory.getLogger(SubscriptionServiceController.class);

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPICreateResponseDTO createSubscription(@RequestBody @Valid SubscriptionApiDTO dto) {
		
		final SubscriptionAPICreateResponseDTO response = new SubscriptionAPICreateResponseDTO();
		
		try {
			Long id = subscriptionService.create(dto);
			response.setId(id);
			response.setSuccess(true);
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			response.setSuccess(false);
		}
		
		return response;
	}
}
