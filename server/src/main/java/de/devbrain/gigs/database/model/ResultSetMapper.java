package de.devbrain.gigs.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public abstract class ResultSetMapper<TResult> {
	
	protected abstract TResult prepare();
	
	protected abstract void map(ResultSet resultSet, TResult repo) throws SQLException;
	
	public TResult map(ResultSet resultSet) throws SQLException {
		TResult repo = prepare();
		if(resultSet.next()) {
			do {
				map(resultSet, repo);
			} while (resultSet.next());
			return repo;
		}
		return null;
	}
	
	protected <T> T handleSingleValue(ResultSet resultSet, Mapper<ResultSet, T> valueGetter, T defaultValue) {
		try {
			if(!resultSet.isBeforeFirst() || resultSet.next()) {
				return valueGetter.map(resultSet);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return defaultValue;
	}
}
