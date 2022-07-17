/**
 * 
 */
package com.adidas.subscription.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @author Telmo
 *
 */
@Configuration
public class MapperConfig {

	@Bean
	public MapperFactory getMapperFactoryBean() {
		return new DefaultMapperFactory.Builder().build();
	}
}
