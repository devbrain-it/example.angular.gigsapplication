package de.devbrain.gigs.database.model;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class QueryTest {
	@Test
	public void convertingStringToString() {
		assertThat(Query.text("a"), equalTo("'a'"));
	}
	
	@Test
	public void convertingIntegerToInt32() {
		assertThat(Query.integer("123"), equalTo(123));
		assertThat(Query.integer(123), equalTo(123));
		assertThat(Query.integer("a"), equalTo(0));
	}
}