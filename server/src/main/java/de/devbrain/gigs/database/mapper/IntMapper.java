package de.devbrain.gigs.database.mapper;

import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class IntMapper extends ResultSetMapper<List<Integer>> {
	
	@Override
	protected List<Integer> prepare() {
		return new ArrayList<>();
	}
	
	@Override
	protected void map(ResultSet resultSet, List<Integer> repo) throws SQLException {
		// 1 basierend!
		repo.add(resultSet.getInt(1));
	}
}
