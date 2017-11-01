package de.devbrain.gigs.database.model;

import de.devbrain.gigs.model.Null;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public abstract class Statement extends Query<Null> {
	
	public Statement(Connection connection) throws SQLException {
		super(connection, null);
	}
}
