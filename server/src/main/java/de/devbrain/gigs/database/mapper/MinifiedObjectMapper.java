package de.devbrain.gigs.database.mapper;

import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.model.ResultSetMapper;
import javafx.util.Builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class MinifiedObjectMapper<T extends MinifiedObject> extends ResultSetMapper<List<T>> {
	
	private final Builder<T> builder;
	private final String     nameColumnName;
	
	public MinifiedObjectMapper(Builder<T> builder, String nameColumnName) {
		this.builder = builder;
		this.nameColumnName = nameColumnName;
	}
	
	@Override
	protected List<T> prepare() {
		return new ArrayList<>();
	}
	
	private T declareEntity() {
		return builder.build();
	}
	
	@Override
	protected void map(ResultSet resultSet, List<T> repo) throws SQLException {
		T enttity = declareEntity();
		enttity.setId(resultSet.getInt("id"));
		enttity.setName(resultSet.getString(this.nameColumnName));
		repo.add(enttity);
	}
}
