package de.devbrain.gigs.database.dao.statement;

import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.mapper.SingleMinifiedObjectResultMapper;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.String.join;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GetMinifiedObjectByIdQuery extends Query<MinifiedObject> {
	
	public static final String P_0_ENTITY_ID = "entity_id";
	private final QueryFormat query;
	
	public GetMinifiedObjectByIdQuery(String tableName, Connection connection, String idColumnName, String labelColumnName) throws SQLException {
		super(connection, new SingleMinifiedObjectResultMapper(idColumnName, labelColumnName));
		
		String select = join(", ", idColumnName, labelColumnName);
		String sql    = String.format("SELECT %s FROM " + tableName + " WHERE id=${entity_id} LIMIT 1", select);
		
		this.query = new QueryFormat(sql)
				.declareParameter(0, P_0_ENTITY_ID, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
	
}
