/**
 * 
 */
package com.adidas.subscription.service.impl;

/**
 * @author Telmo
 *
 */
public abstract class AbstractProxyImpl {

	protected String getEndPoint(String url, String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(url).append(path);
		return builder.toString();
	}
}
