package de.devbrain.gigs.database.dao.user.statement;

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
public final class IsUserAModeratorQuery extends Query<Boolean> {
	
	public static final String P_0_USER_ID = "user_id";
	
	private final QueryFormat query;
	
	public IsUserAModeratorQuery(Connection connection) throws SQLException {
		super(connection, new BooleanMapper());
		//language=MySQL
		this.query = new QueryFormat("SELECT EXISTS(SELECT * FROM user WHERE user.id=${user_id} AND user.role_fk = 2)")
				.declareParameter(0, P_0_USER_ID, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}
