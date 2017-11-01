package de.devbrain.gigs.database.dao.utils;

import org.junit.Test;

import static de.devbrain.gigs.database.model.Query.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class QueryFormatTest {
	
	@Test
	public void testOK() {
		// given
		String      sql    = "SELECT * FROM x WHERE id=${id} OR name LIKE ${name} OR dt=${date} OR t=${time} OR dtime = ${datetime}";
		QueryFormat format = new QueryFormat(sql);
		format.declareParameter(0, "id", Parameter.ValueTypeFormat.INT);
		format.declareParameter(1, "name", Parameter.ValueTypeFormat.TEXT);
		format.declareParameter(2, "date", Parameter.ValueTypeFormat.DATE);
		format.declareParameter(3, "time", Parameter.ValueTypeFormat.TIME);
		format.declareParameter(4, "datetime", Parameter.ValueTypeFormat.DATE_TIME);
		
		// when
		Object[] args = {1,
		                 "Hello World",
		                 "20.12.1999",
		                 "23:00",
		                 "21.12.2003 12:54:12"};
		String result = format.format(args);
		
		// then
		String formatted = sql
				.replace("${id}", "%d")
				.replace("${name}", "%s")
				.replace("${date}", "%s")
				.replace("${time}", "%s")
				.replace("${datetime}", "%s");
		
		String expectation = String.format(formatted,
		                                   integer(args[0]),
		                                   text(args[1]),
		                                   date(args[2]),
		                                   time(args[3]),
		                                   datetime(args[4])
		                                  );
		
		assertThat(result, is(equalTo(expectation)));
	}
}