package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.database.dao.user.model.SingleUserEntityMapper;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GetUserByLoginWithPasswordQuery extends Query<UserEntity> {
	
	public static final String P_0_LOGIN = "login";
	public static final String P_1_HASH  = "hash";
	
	private final QueryFormat query;
	
	public GetUserByLoginWithPasswordQuery(Connection connection) throws SQLException {
		super(connection, new SingleUserEntityMapper());
		//language=MySQL
		this.query = new QueryFormat("SELECT user.id user_id, user.username, user.login, user.role_fk AS role_id FROM user WHERE user.login LIKE ${login} AND password = ${hash} LIMIT 1")
				.declareParameter(0, P_0_LOGIN, Parameter.ValueTypeFormat.TEXT)
				.declareParameter(1, P_1_HASH, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}
