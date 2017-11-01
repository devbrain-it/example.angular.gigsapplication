package de.devbrain.gigs.database.mapper;

import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class SingleMinifiedObjectResultMapper extends ResultSetMapper<MinifiedObject> {
	
	private final String idColumnName;
	private final String labelColumnName;
	
	public SingleMinifiedObjectResultMapper(String idColumnName, String labelColumnName) {
		this.idColumnName = idColumnName;
		this.labelColumnName = labelColumnName;
	}
	
	@Override
	protected MinifiedObject prepare() {
		return new MinifiedObject();
	}
	
	@Override
	protected void map(ResultSet resultSet, MinifiedObject repo) throws SQLException {
		repo.setId(resultSet.getInt(idColumnName));
		repo.setName(resultSet.getString(labelColumnName));
	}
}
