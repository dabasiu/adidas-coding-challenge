/**
 * 
 */
package com.adidas.subscription.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.adidas.subscription.dto.SubscriptionApiDTO;
import com.adidas.subscription.dto.responses.SubscriptionAPICreateResponseDTO;
import com.adidas.subscription.service.SubscriptionServiceProxy;

/**
 * @author Telmo
 *
 */
@Component
public class SubscriptionServiceProxyImpl implements SubscriptionServiceProxy {

	private RestTemplate restTemplate;
	
	@Value("${subscription.domain.url}") 
	private String url;
	
	@Autowired
	public SubscriptionServiceProxyImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private String getEndPoint(String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(url).append(path);
		return builder.toString();
	}
	
	@Override
	public SubscriptionAPICreateResponseDTO createSubscription(SubscriptionApiDTO dto) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.getEndPoint("/subscriptions/create"));

		HttpEntity<SubscriptionApiDTO> httpEntity = new HttpEntity<>(dto);

		ResponseEntity<SubscriptionAPICreateResponseDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<SubscriptionAPICreateResponseDTO>() {
		});
		
		return response.getBody();
	}
}