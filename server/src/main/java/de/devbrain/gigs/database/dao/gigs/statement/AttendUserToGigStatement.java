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
public final class AttendUserToGigStatement extends Statement {
	
	public static final String P_0_USER_ID = "user_id";
	public static final String P_1_GIG_ID  = "gig_id";
	
	private final QueryFormat query;
	
	public AttendUserToGigStatement(Connection connection) throws SQLException {
		super(connection);
		//language=MySQL
		this.query = new QueryFormat("INSERT INTO user_gig(user_fk, gig_fk) VALUES (${user_id}, ${gig_id})")
				.declareParameter(0, P_0_USER_ID, Parameter.ValueTypeFormat.INT)
				.declareParameter(1, P_1_GIG_ID, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}
