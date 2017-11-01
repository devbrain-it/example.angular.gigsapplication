package de.devbrain.gigs.database.dao.city.statement;

import de.devbrain.gigs.database.dao.statement.GetMinifiedObjectByIdQuery;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GetCityByIdQuery extends GetMinifiedObjectByIdQuery {
	
	public GetCityByIdQuery(Connection connection) throws SQLException {
		super("city", connection, "id", "city_name");
	}
}
