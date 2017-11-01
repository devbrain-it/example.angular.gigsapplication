package de.devbrain.gigs.database.dao.city.statement;

import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.mapper.MinifiedObjectMapper;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class FetchAllCitiesQuery extends Query<List<MinifiedObject>> {
	
	private final QueryFormat query;
	
	public FetchAllCitiesQuery(Connection connection) throws SQLException {
		super(connection, new MinifiedObjectMapper<>(MinifiedObject::new, "city_name"));
		//language=MySQL
		this.query = new QueryFormat("SELECT id, city_name FROM city ORDER BY id");
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}