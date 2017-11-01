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
public final class CitiesDeleteStatement extends Statement {
	
	public static final String P_0_CITY_IDS = "city_ids";
	private final QueryFormat query;
	
	public CitiesDeleteStatement(Connection connection) throws SQLException {
		super(connection);
		this.query = new QueryFormat("DELETE FROM city WHERE id IN(${city_ids})")
				.declareParameter(0, P_0_CITY_IDS, Parameter.ValueTypeFormat.LIST);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}
