package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.database.mapper.SingleIntMapper;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class UpdateUserStatement extends Query<Integer> {
	
	private UserEntity user;
	private String     login;
	
	public UpdateUserStatement(Connection connection) throws SQLException {
		super(connection, new SingleIntMapper());
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		String username = text(parameters[0]);
		this.login = (String) parameters[1];
		int password     = integer(parameters[2]);
		int user_role_id = integer(parameters[3]);
		
		user = new GetUserByLoginWithPasswordQuery(connection).execute(login, password);
		if(user == null) {
			throw new IllegalArgumentException("User was not found '" + login + "'");
		}
		
		//language=MySQL
		String sql = "UPDATE user SET username=${userName}, password=${passwordHash}, role_fk=${role_id} WHERE id=${user_id}";
		String formatted = sql.replace("${login}", "%s")
		                      .replace("${passwordHash}", "%s")
		                      .replace("${userName}", "%s")
		                      .replace("${role_id}", "%d")
		                      .replace("${user_id}", "%d");
		
		String query = String.format(formatted, username, password, user_role_id, user.getId());
		return connection.prepareStatement(query);
	}
	
	@Override
	protected Integer performStatement(PreparedStatement statement, ResultSetMapper<Integer> intMapper) throws SQLException {
		if(user == null) {
			throw new IllegalArgumentException("User was not found '" + login + "'");
		}
		return super.performStatement(statement, intMapper);
	}
}
