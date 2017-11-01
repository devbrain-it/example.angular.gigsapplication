package de.devbrain.gigs.database.dao.city.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class CityUpdateStatement extends Statement {
	
	public static final String P_0_CITY_ID   = "city_id";
	public static final String P_1_CITY_NAME = "city_name";
	
	private final QueryFormat query;
	
	public CityUpdateStatement(Connection connection) throws SQLException {
		super(connection);
		this.query = new QueryFormat("UPDATE city SET city_name = ${city_name} WHERE id = ${city_id}")
				.declareParameter(0, P_0_CITY_ID, Parameter.ValueTypeFormat.INT)
				.declareParameter(1, P_1_CITY_NAME, Parameter.ValueTypeFormat.TEXT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}