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
import com.adidas.subscription.dto.responses.SubscriptionAPIResponseDTO;
import com.adidas.subscription.service.SubscriptionMailProxy;

/**
 * @author Telmo
 *
 */
@Component
public class SubscriptionMailProxyImpl extends AbstractProxyImpl implements SubscriptionMailProxy {

	private RestTemplate restTemplate;
	
	@Value("${emailservice.domain.url}") 
	private String url;
	
	@Autowired
	public SubscriptionMailProxyImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public SubscriptionAPIResponseDTO sendEmail(SubscriptionApiDTO dto) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getEndPoint(url, "/email/send"));

		HttpEntity<SubscriptionApiDTO> httpEntity = new HttpEntity<>(dto);

		ResponseEntity<SubscriptionAPIResponseDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<SubscriptionAPIResponseDTO>() {
		});
		
		return response.getBody();
	}

}
