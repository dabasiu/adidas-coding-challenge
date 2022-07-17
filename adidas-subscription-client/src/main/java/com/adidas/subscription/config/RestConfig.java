/**
 * 
 */
package com.adidas.subscription.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Telmo
 *
 */
@Configuration
public class RestConfig {
	
	@Bean
    public RestTemplate getRestTemplate() {
		final RestTemplate restTemplate = new RestTemplate();
		
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectionRequestTimeout(5 * 60 * 1000);
		factory.setConnectTimeout(5 * 60 * 1000);
        factory.setReadTimeout(5 * 60 * 1000);
        
		final HttpClient httpClient = HttpClientBuilder.create()
		                                               .setRedirectStrategy(new LaxRedirectStrategy())
		                                               .build();
		factory.setHttpClient(httpClient);
		restTemplate.setRequestFactory(factory);
		
		return restTemplate;
    }
}
