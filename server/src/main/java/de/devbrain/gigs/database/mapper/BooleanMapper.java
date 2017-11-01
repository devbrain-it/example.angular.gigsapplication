package de.devbrain.gigs.database.mapper;

import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class BooleanMapper extends ResultSetMapper<Boolean> {
	
	private final int                        columnIndex;
	
	public BooleanMapper() {
		this(0);
	}
	
	public BooleanMapper(int columnIndex) {
		// 1-basierend!
		this.columnIndex = columnIndex + 1;
	}
	
	Boolean getValue(ResultSet r) {
		try {
			return r.getBoolean(columnIndex);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected Boolean prepare() {
		return null;
	}
	
	@Override
	protected void map(ResultSet resultSet, Boolean ignored) throws SQLException {
		// ignore!
	}
	
	@Override
	public Boolean map(ResultSet resultSet) throws SQLException {
		return handleSingleValue(resultSet, this::getValue, false);
	}
	
}
