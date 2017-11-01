package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class DeleteGigStatement extends Statement {
	
	private final IsGigAttachedQuery isGigAttachedQuery;
	
	public DeleteGigStatement(Connection connection) throws SQLException {
		super(connection);
		this.isGigAttachedQuery = new IsGigAttachedQuery(connection);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		int gigId = integer(parameters[0]);
		
		QueryFormat query;
		if(!isGigAttached(gigId)) {
			//language=MySQL
			query = new QueryFormat("DELETE FROM gig WHERE id=${gig_id}");
		} else {
			//language=MySQL
			query = new QueryFormat("UPDATE gig SET deleted=1 WHERE id=${gig_id}");
		}
		query.declareParameter(0, "gig_id", Parameter.ValueTypeFormat.INT);
		
		return query.prepare(connection, parameters);
	}
	
	private boolean isGigAttached(int gigId) throws SQLException {
		return isGigAttachedQuery.execute(gigId);
	}
}
