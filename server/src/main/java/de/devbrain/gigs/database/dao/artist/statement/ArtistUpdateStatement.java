package de.devbrain.gigs.database.dao.artist.statement;

import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public final class ArtistUpdateStatement extends Statement {
	
	private final QueryFormat query;
	
	public ArtistUpdateStatement(Connection connection) throws SQLException {
		super(connection);
		this.query = new QueryFormat("UPDATE artist SET artist_name = ${artist_name} WHERE id = ${artist_id}")
				.declareParameter(0, "artist_id", Parameter.ValueTypeFormat.INT)
				.declareParameter(1, "artist_name", Parameter.ValueTypeFormat.TEXT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}