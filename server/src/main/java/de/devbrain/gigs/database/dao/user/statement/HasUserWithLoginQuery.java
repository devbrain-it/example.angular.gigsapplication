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
public final class HasUserWithLoginQuery extends Query<Boolean> {
	
	public static final String P_0_LOGIN = "login";
	
	private final QueryFormat query;
	
	public HasUserWithLoginQuery(Connection connection) throws SQLException {
		super(connection, new BooleanMapper());
		//language=MySQL
		this.query = new QueryFormat("SELECT EXISTS(SELECT 1 FROM user WHERE login LIKE ${login})")
				.declareParameter(0, P_0_LOGIN, Parameter.ValueTypeFormat.TEXT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}