package de.devbrain.gigs.database.dao.user.model;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class SingleUserEntityMapper extends ResultSetMapper<UserEntity> {
	
	@Override
	protected UserEntity prepare() {
		return new UserEntity();
	}
	
	@Override
	protected void map(ResultSet resultSet, UserEntity repo) throws SQLException {
		repo.setId(resultSet.getInt("user_id"));
		repo.setVisibleName(resultSet.getString("username"));
		repo.setLogin(resultSet.getString("login"));
		repo.setRoleId(resultSet.getInt("role_id"));
	}
}
