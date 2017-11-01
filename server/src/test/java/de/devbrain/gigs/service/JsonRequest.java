package de.devbrain.gigs.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class JsonRequest<T> {
	
	private final URL      url;
	private final Class<T> type;
	
	public JsonRequest(String url, Class<T> type) throws MalformedURLException {
		this.url = new URL(url);
		this.type = type;
	}
	
	public T request() throws IOException {
		return new ObjectMapper().readValue(url, type);
	}
}
