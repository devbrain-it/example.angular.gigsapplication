package de.devbrain.gigs.database.dao.artist.statement;

import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class InsertArtistStatement extends InsertStatement {
	
	public static final String P_0_ARTIST_NAME = "artist_name";
	
	public InsertArtistStatement(Connection connection) throws SQLException {
		super(connection,
		      new QueryFormat("INSERT INTO artist(artist_name) VALUES (${artist_name})")
				      .declareParameter(0, P_0_ARTIST_NAME, Parameter.ValueTypeFormat.TEXT));
	}
}
