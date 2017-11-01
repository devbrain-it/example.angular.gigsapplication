package de.devbrain.gigs.database.dao.genre.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GenreUpdateStatement extends Statement {
	
	public static final String P_0_GENRE_ID   = "genre_id";
	public static final String P_1_GENRE_NAME = "genre_name";
	
	private final QueryFormat query;
	
	public GenreUpdateStatement(Connection connection) throws SQLException {
		super(connection);
		this.query = new QueryFormat("UPDATE genre SET genre_name = ${genre_name} WHERE id = ${genre_id}")
				.declareParameter(0, P_0_GENRE_ID, Parameter.ValueTypeFormat.INT)
				.declareParameter(1, P_1_GENRE_NAME, Parameter.ValueTypeFormat.TEXT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}