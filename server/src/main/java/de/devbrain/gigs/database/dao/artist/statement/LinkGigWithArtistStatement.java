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
public final class LinkGigWithArtistStatement extends Statement {
	
	public static final String P_0_ARTIST_FK = "artist_fk";
	public static final String P_1_GIG_FK    = "gig_fk";
	public static final String P_2_GENRE_FK  = "genre_fk";
	
	private final QueryFormat query;
	
	public LinkGigWithArtistStatement(Connection connection) throws SQLException {
		super(connection);
		//language=MySQL
		this.query = new QueryFormat("INSERT INTO artist_gig(artist_fk, gig_fk, genre_fk, time) VALUES (${artist_id}, ${gig_id}, ${genre_id}, ${time})")
				.declareParameter(0, P_0_ARTIST_FK, Parameter.ValueTypeFormat.INT)
				.declareParameter(1, P_1_GIG_FK, Parameter.ValueTypeFormat.INT)
				.declareParameter(2, P_2_GENRE_FK, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return query.prepare(connection, parameters);
	}
}
