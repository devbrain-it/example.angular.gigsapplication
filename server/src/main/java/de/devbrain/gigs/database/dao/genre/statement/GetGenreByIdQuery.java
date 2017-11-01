package de.devbrain.gigs.database.dao.genre.statement;

import de.devbrain.gigs.database.dao.statement.GetMinifiedObjectByIdQuery;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GetGenreByIdQuery extends GetMinifiedObjectByIdQuery {
	
	public GetGenreByIdQuery(Connection connection) throws SQLException {
		super("genre", connection, "id", "genre_name");
	}
}
