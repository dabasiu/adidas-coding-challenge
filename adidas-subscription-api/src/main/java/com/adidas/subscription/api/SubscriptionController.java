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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @ApiOperation(value = "Get all subscriptions")
	@GetMapping("/")
	public @ResponseBody ResponseEntity<Map<String, Object>> getAll() {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			SubscriptionAPIGetAllResponseDTO resp = proxy.getAllSubscriptions();
			
			response.put("data", resp.getDtos());
			handleResponse(resp, response);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @ApiOperation(value = "Get the details of a subscription")
	@GetMapping("/{email}")
	public @ResponseBody ResponseEntity<Map<String, Object>> getSubscription(@PathVariable String email) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPIGetResponseDTO resp = proxy.getSubscription(email);
			
			response.put("data", resp.getDto());
			handleResponse(resp, response);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @ApiOperation(value = "Create a subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created")
    })
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, Object>> createSubscription(@RequestBody @Valid SubscriptionApiDTO dto) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPICreateResponseDTO resp = proxy.createSubscription(dto);
			
			response.put("id", resp.getId());
			handleResponse(resp, response);
			
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @ApiOperation(value = "Cancel a subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully updated")
    })
	@DeleteMapping("/{email}")
	public @ResponseBody ResponseEntity<Map<String, Object>> cancelSubscription(@PathVariable String email) {
		
		final Map<String, Object> response = new HashMap<>();
		
		try {
			
			SubscriptionAPIResponseDTO resp = proxy.cancelSubscription(email);

			handleResponse(resp, response);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
    
    private void handleResponse(SubscriptionAPIResponseDTO dto, Map<String, Object> response) {
		response.put("success", dto.getSuccess());
		response.put("messages", dto.getMessages());
    }

}
