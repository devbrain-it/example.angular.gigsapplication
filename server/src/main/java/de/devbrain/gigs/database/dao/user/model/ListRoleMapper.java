package de.devbrain.gigs.database.dao.user.model;

import de.devbrain.gigs.database.dao.user.Role;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class ListRoleMapper extends ResultSetMapper<List<Role>> {
	@Override
	protected List<Role> prepare() {
		return new ArrayList<>();
	}
	
	@Override
	protected void map(ResultSet resultSet, List<Role> repo) throws SQLException {
		repo.add(newRole(resultSet));
	}
	
	private static Role newRole(ResultSet resultSet) throws SQLException {
		Role result = new Role();
		result.setId(resultSet.getInt("id"));
		result.setRoleName(resultSet.getString("name"));
		result.setDefault(resultSet.getBoolean("is_default"));
		result.setDescription(resultSet.getString("description"));
		return result;
	}
}
