package de.devbrain.gigs.database.dao.artist.statement;

import de.devbrain.gigs.database.dao.statement.GetMinifiedObjectByIdQuery;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GetArtistByIdQuery extends GetMinifiedObjectByIdQuery {
	
	public GetArtistByIdQuery(Connection connection) throws SQLException {
		super("artist", connection, "id", "artist_name");
	}
}
