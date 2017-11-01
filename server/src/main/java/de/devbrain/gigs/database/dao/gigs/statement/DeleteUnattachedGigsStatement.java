package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class DeleteUnattachedGigsStatement extends Statement {
	
	private final QueryFormat query;
	
	public DeleteUnattachedGigsStatement(Connection connection) throws SQLException {
		super(connection);
		this.query = new QueryFormat("DELETE gig.* FROM gig" +
		                             " LEFT JOIN user_gig ON user_gig.gig_fk=gig.id" +
		                             " WHERE gig.deleted=1 AND user_gig.id IS NULL");
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}
