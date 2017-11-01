package de.devbrain.gigs.database.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
@SuppressWarnings("unused")
public final class QueryFormat {
	
	private final List<Parameter> params;
	private       String          sql;
	
	public QueryFormat(String sql) {
		this.sql = sql;
		this.params = new ArrayList<>();
	}
	
	public String getName(int argumentIndex) {
		return params.stream().filter(p -> p.getIndex() == argumentIndex).map(AbstractMap.SimpleEntry::getKey).findFirst().orElse(null);
	}
	
	public QueryFormat declareParameter(int argumentIndex, String name, Parameter.ValueTypeFormat type) {
		String key = String.format("${%s}", name);
		params.add(new Parameter(key, argumentIndex, type));
		return this;
	}
	
	String format(Object... args) {
		String sql = this.sql;
		for (Parameter param : params) {
			param.setValue(args[param.getIndex()]);
			String format = sql.replace(param.getKey(), param.getType().getStringFormatHolder());
			Object value    = param.getType().map(param);
			sql = String.format(format, value);
		}
		return sql;
	}
	
	public PreparedStatement prepare(Connection connection, Object... args) throws SQLException {
		return connection.prepareStatement(format(args));
	}
	
	public PreparedStatement prepareInsert(Connection connection, Object... args) throws SQLException {
		return connection.prepareStatement(format(args), Statement.RETURN_GENERATED_KEYS);
	}
}
