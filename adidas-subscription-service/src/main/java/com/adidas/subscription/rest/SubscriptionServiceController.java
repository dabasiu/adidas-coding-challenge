/**
 * 
 */
package com.adidas.subscription.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetAllResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;
import com.adidas.subscription.exception.SubscriptionException;
import com.adidas.subscription.exception.SubscriptionExceptionUtils;

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
			
			//send email async
			subscriptionService.processEmailForSubscriptionCreated(dto);
			
			response.setId(id);
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

	@DeleteMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIResponseDTO cancelSubscription(@PathVariable String email) {
		
		final SubscriptionAPIResponseDTO response = new SubscriptionAPIResponseDTO();
		
		try {
			boolean success = subscriptionService.delete(email);
			response.setSuccess(success);
			
		} catch (SubscriptionException se) {
			log.error(se.getMessage(), se);
			SubscriptionExceptionUtils.handleSubscriptionException(response, se);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			SubscriptionExceptionUtils.handleUnexpectedException(response);
		}
		
		return response;
	}

	@GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIGetResponseDTO getSubscription(@PathVariable String email) {
		
		final SubscriptionAPIGetResponseDTO response = new SubscriptionAPIGetResponseDTO();
		
		try {
			Optional<SubscriptionApiDTO> optDto = subscriptionService.retrieveByEmail(email);
			
			if(optDto.isPresent()) {
				response.setSuccess(true);
				response.setDto(optDto.get());
			}
			
		} catch (SubscriptionException se) {
			log.error(se.getMessage(), se);
			SubscriptionExceptionUtils.handleSubscriptionException(response, se);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			SubscriptionExceptionUtils.handleUnexpectedException(response);
		}
		
		return response;
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIGetAllResponseDTO getSubscription() {
		
		final SubscriptionAPIGetAllResponseDTO response = new SubscriptionAPIGetAllResponseDTO();
		
		try {
			List<SubscriptionApiDTO> dtos = subscriptionService.retrieveAll();
			
			response.setSuccess(true);
			response.setDtos(dtos);
			
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
