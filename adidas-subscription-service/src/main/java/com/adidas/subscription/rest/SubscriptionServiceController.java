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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetAllResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;

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

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIResponseDTO cancelSubscription(@RequestParam Long id ) {
		
		final SubscriptionAPIResponseDTO response = new SubscriptionAPIResponseDTO();
		
		try {
			boolean success = subscriptionService.delete(id);
			response.setSuccess(success);
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			response.setSuccess(false);
		}
		
		return response;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubscriptionAPIGetResponseDTO getSubscription(@RequestParam Long id ) {
		
		final SubscriptionAPIGetResponseDTO response = new SubscriptionAPIGetResponseDTO();
		
		try {
			Optional<SubscriptionApiDTO> optDto = subscriptionService.retrieveById(id);
			
			if(optDto.isPresent()) {
				response.setSuccess(true);
				response.setDto(optDto.get());
			} else {
				response.setSuccess(false);
			}
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			response.setSuccess(false);
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
			
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			response.setSuccess(false);
		}
		
		return response;
	}
}
