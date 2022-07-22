/**
 * 
 */
package com.adidas.subscription.service.impl;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

/**
 * @author Telmo
 *
 */
public abstract class AbstractProxyImpl {
	
	@Value("${service.username:user}") 
	private String username;

	@Value("${service.password:password}") 
	private String password;

	protected String getEndPoint(String url, String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(url).append(path);
		return builder.toString();
	}
	
	protected HttpHeaders createHeaders(){
		   return new HttpHeaders() {{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		}
}
