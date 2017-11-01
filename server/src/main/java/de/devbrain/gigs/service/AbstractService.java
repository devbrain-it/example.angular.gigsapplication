package de.devbrain.gigs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractService {
	
	@Context
	protected HttpServletRequest request;
	
	protected static Response postResponseValue(Object value) throws IOException {
		return postResponseValue(value, "POST");
	}
	
	protected static Response postResponseValue(Object value, String allowedMethodsString) throws IOException {
		Response.ResponseBuilder builder = Response.ok(toJson(value));
		crossDomainHeader(builder, allowedMethodsString);
		return builder.build();
	}
	
	protected static Response.ResponseBuilder crossDomainHeader(Response.ResponseBuilder builder, String allowedMethodsString) {
		builder.header("Access-Control-Allow-Methods", allowedMethodsString)
		       .header("Access-Control-Allow-Headers", "X-Custom-Header");
		return builder;
	}
	
	protected static String toJson(Object value) throws IOException {
		ByteArrayOutputStream out  = new ByteArrayOutputStream();
		ObjectMapper          json = new ObjectMapper();
		json.writeValue(out, value);
		final byte[] data = out.toByteArray();
		return new String(data);
	}
	
	protected Request getRequestTyped() {
		Request requestTyped = (Request) this.request;
		if(requestTyped.getSessionManager() == null) {
			HashSessionManager sessionManager = new HashSessionManager();
			requestTyped.setSessionManager(sessionManager);
			HashSessionIdManager idManager = new HashSessionIdManager();
			idManager.setRandom(new SecureRandom());
			sessionManager.setSessionIdManager(idManager);
		}
		return requestTyped;
	}
}
