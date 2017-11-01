package de.devbrain.gigs.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class GigServiceTest extends RestServerTest {
	
	@Test
	public void fetchGigsSummary() throws Exception {
		String               url   = "http://localhost:" + PORT + "/gigs/rest/gigs";
		JsonRequest<Object> json  = new JsonRequest<>(url, Object.class);
		Object value = json.request();
		System.out.println(value);
		assertThat(value, notNullValue());
	}
}