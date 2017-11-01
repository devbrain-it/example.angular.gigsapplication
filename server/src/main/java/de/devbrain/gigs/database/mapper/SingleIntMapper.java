package de.devbrain.gigs.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class SingleIntMapper extends de.devbrain.gigs.database.model.ResultSetMapper<Integer> {
	
	@Override
	protected Integer prepare() {
		return 0;
	}
	
	@Override
	public Integer map(ResultSet resultSet) throws SQLException {
		if(resultSet.next()) {
			return resultSet.getInt(1);
		}
		return 0;
	}
	
	@Override
	protected void map(ResultSet resultSet, Integer repo) throws SQLException {
		// skipped
	}
}
