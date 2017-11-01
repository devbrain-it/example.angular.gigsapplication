package de.devbrain.gigs.database.dao.user.statement;

import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.mapper.SingleIntMapper;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class InsertStatement extends Query<Integer> {
	
	private static final SingleIntMapper ID_MAPPER = new SingleIntMapper();
	private final QueryFormat query;
	
	public InsertStatement(Connection connection, QueryFormat queryFormat) throws SQLException {
		super(connection, null);
		this.query = queryFormat;
	}
	
	@Override
	protected final PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepareInsert(connection, parameters);
	}
	
	@Override
	protected final Integer performStatement(PreparedStatement statement, ResultSetMapper<Integer> null_mapper) throws SQLException {
		super.performStatement(statement, null_mapper);
		return getInsertedLastId(statement);
	}
	
	protected final Integer getInsertedLastId(PreparedStatement statement) throws SQLException {
		ResultSet rs = statement.getGeneratedKeys();
		if(rs.next()) {
			return ID_MAPPER.map(rs);
		}
		return null;
	}
}
