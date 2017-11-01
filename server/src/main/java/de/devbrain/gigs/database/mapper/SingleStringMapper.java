package de.devbrain.gigs.database.mapper;

import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class SingleStringMapper extends ResultSetMapper<String> {
	
	@Override
	protected String prepare() {
		return null;
	}
	
	@Override
	public String map(ResultSet resultSet) throws SQLException {
		if(resultSet.next()) {
			return resultSet.getString(1);
		}
		return null;
	}
	
	@Override
	protected void map(ResultSet resultSet, String repo) throws SQLException {
		// skipped
	}
}
