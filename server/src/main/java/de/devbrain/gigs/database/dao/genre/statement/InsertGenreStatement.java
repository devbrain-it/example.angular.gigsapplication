package de.devbrain.gigs.database.dao.genre.statement;

import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class InsertGenreStatement extends InsertStatement {
	
	public static final String P_0_GENRE_NAME = "genre_name";
	
	public InsertGenreStatement(Connection connection) throws SQLException {
		super(connection,
		      new QueryFormat("INSERT INTO genre(genre_name) VALUES (${genre_name})")
				      .declareParameter(0, P_0_GENRE_NAME, Parameter.ValueTypeFormat.TEXT));
	}
}
