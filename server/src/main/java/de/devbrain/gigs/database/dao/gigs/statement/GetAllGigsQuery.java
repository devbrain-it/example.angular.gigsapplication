package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.gigs.entity.GigEntity;
import de.devbrain.gigs.database.dao.gigs.model.GigsMapper;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class GetAllGigsQuery extends Query<List<Map.Entry<Integer, GigEntity>>> {
	
	private final QueryFormat query;
	
	public GetAllGigsQuery(Connection connection) throws SQLException {
		super(connection, new GigsMapper());
		this.query = new QueryFormat("SELECT gig.id gig_id, gig.date gig_date, artist_gig.time gig_time, city.city_name, artist.artist_name, artist.id AS artistId, genre.genre_name, gig.deleted FROM gig" +
		                             " INNER JOIN city ON city.id = gig.city_fk" +
		                             " INNER JOIN artist_gig ON artist_gig.gig_fk = gig.id" +
		                             " INNER JOIN genre ON genre.id = artist_gig.genre_fk" +
		                             " INNER JOIN artist ON artist.id = artist_gig.artist_fk" +
		                             " ORDER BY date, time, city.city_name, artist.artist_name");
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}