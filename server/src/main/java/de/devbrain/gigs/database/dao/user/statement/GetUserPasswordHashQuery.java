package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.mapper.SingleStringMapper;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GetUserPasswordHashQuery extends Query<String> {
	
	public static final String P_0_USERNAME = "userName";
	private final QueryFormat query;
	
	public GetUserPasswordHashQuery(Connection connection) throws SQLException {
		super(connection, new SingleStringMapper());
		//language=MySQL
		this.query = new QueryFormat("SELECT user.password FROM user WHERE login LIKE ${userName}")
				.declareParameter(0, P_0_USERNAME, Parameter.ValueTypeFormat.TEXT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}