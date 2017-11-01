package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.user.Role;
import de.devbrain.gigs.database.dao.user.model.ListRoleMapper;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class GetAllUserRolesQuery extends Query<List<Role>> {
	
	private final QueryFormat query;
	
	public GetAllUserRolesQuery(Connection connection) throws SQLException {
		super(connection, new ListRoleMapper());
		this.query = new QueryFormat("SELECT * FROM role");
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}