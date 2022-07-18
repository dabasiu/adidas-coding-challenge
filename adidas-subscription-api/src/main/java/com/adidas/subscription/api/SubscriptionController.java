/**
 * 
 */
package com.adidas.subscription.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetAllResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIGetResponseDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;
import com.adidas.subscription.service.SubscriptionServiceProxy;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Telmo
 *
 */
@RestController
@Validated
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
	
	private final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	private SubscriptionServiceProxy proxy;
	
	@GetMapping("/")
	public @ResponseBody Map<String, Object> getAll() {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			SubscriptionAPIGetAllResponseDTO resp = proxy.getAllSubscriptions();
			
			response.put("data", resp.getDtos());
			response.put("success", true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return response;
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Map<String, Object> getSubscription(@RequestParam Long id) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPIGetResponseDTO resp = proxy.getSubscription(id);
			
			response.put("data", resp.getDto());
			response.put("success", true);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return response;
		
	}

    @ApiOperation(value = "Create a subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created")
    })
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> createSubscription(@RequestBody @Valid SubscriptionApiDTO dto) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPICreateResponseDTO subscriptionResponse = proxy.createSubscription(dto);
			
			response.put("id", subscriptionResponse.getId());
			response.put("success", subscriptionResponse.getSuccess());
			
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody Map<String, Object> cancelSubscription(@RequestParam Long id) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPIResponseDTO resp = proxy.cancelSubscription(id);
			
			response.put("success", resp.getSuccess());
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return response;
	}

}
