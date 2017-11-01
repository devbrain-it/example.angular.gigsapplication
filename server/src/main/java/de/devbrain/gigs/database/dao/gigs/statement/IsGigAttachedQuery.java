package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.mapper.BooleanMapper;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class IsGigAttachedQuery extends Query<Boolean> {
	
	public static final String P_0_GIG_ID = "gig_id";
	
	private final QueryFormat query;
	
	public IsGigAttachedQuery(Connection connection) throws SQLException {
		super(connection, new BooleanMapper());
		this.query = new QueryFormat("SELECT EXISTS(SELECT 1 FROM user_gig WHERE gig_fk=${gig_id}) AS exist")
				.declareParameter(0, P_0_GIG_ID, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}