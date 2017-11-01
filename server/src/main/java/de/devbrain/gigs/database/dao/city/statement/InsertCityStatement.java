package de.devbrain.gigs.database.dao.city.statement;

import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class InsertCityStatement extends InsertStatement {
	
	public static final String P_0_CITY_NAME = "city_name";
	
	public InsertCityStatement(Connection connection) throws SQLException {
		super(connection,
		      new QueryFormat("INSERT INTO city(city_name) VALUES (${city_name})")
				      .declareParameter(0, P_0_CITY_NAME, Parameter.ValueTypeFormat.TEXT));
	}
}
