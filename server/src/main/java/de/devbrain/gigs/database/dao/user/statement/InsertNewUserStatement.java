package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class InsertNewUserStatement extends InsertStatement {
	
	public static final String P_0_USERNAME      = "username";
	public static final String P_1_LOGIN         = "login";
	public static final String P_2_PASSWORD_HASH = "passwordHash";
	public static final String P_3_ROLE_ID       = "role_id";
	
	public InsertNewUserStatement(Connection connection) throws SQLException {
		//language=MySQL
		super(connection, new QueryFormat("INSERT INTO user(username, login, password, role_fk) VALUES (${username}, ${login}, ${passwordHash}, ${role_id})")
				.declareParameter(0, P_0_USERNAME, Parameter.ValueTypeFormat.TEXT)
				.declareParameter(1, P_1_LOGIN, Parameter.ValueTypeFormat.TEXT)
				.declareParameter(2, P_2_PASSWORD_HASH, Parameter.ValueTypeFormat.TEXT)
				.declareParameter(3, P_3_ROLE_ID, Parameter.ValueTypeFormat.INT));
	}
}