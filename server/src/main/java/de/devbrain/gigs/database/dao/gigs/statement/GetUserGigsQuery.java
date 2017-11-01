package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.gigs.model.UserGigInfo;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GetUserGigsQuery extends Query<List<UserGigInfo>> {
	
	public static final String P_0_USER_ID = "user_id";
	private final QueryFormat query;
	
	public GetUserGigsQuery(Connection connection) throws SQLException {
		super(connection, new ResultSetMapper<List<UserGigInfo>>() {
			@Override
			protected List<UserGigInfo> prepare() {
				return new ArrayList<>();
			}
			
			@Override
			protected void map(ResultSet resultSet, List<UserGigInfo> repo) throws SQLException {
				int gigId    = resultSet.getInt("gig_fk");
				int artistId = resultSet.getInt("artist_fk");
				// 1 basierend!
				UserGigInfo info = repo.stream().filter(u -> u.getGigId() == gigId).findFirst().orElse(null);
				if(info == null) {
					info = new UserGigInfo();
					info.setGigId(gigId);
					info.setArtistIds(new ArrayList<>());
					repo.add(info);
				}
				info.getArtistIds().add(artistId);
			}
		});
		//language=MySQL
		this.query = new QueryFormat("SELECT user_gig.gig_fk, artist_gig.artist_fk FROM user_gig" +
		                             " LEFT JOIN artist_gig ON artist_gig.gig_fk = user_gig.gig_fk" +
		                             " WHERE user_fk=${user_id}" +
		                             " GROUP BY user_gig.gig_fk, artist_gig.artist_fk")
				.declareParameter(0, P_0_USER_ID, Parameter.ValueTypeFormat.INT);
	}
	
	@Override
	protected PreparedStatement init(Connection connection, Object... parameters) throws SQLException {
		return this.query.prepare(connection, parameters);
	}
}