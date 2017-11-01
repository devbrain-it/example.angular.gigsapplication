package de.devbrain.gigs.database.dao.gigs.model;

import de.devbrain.gigs.database.dao.gigs.entity.ArtistGigEntity;
import de.devbrain.gigs.database.dao.gigs.entity.GigEntity;
import de.devbrain.gigs.database.model.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GigsMapper extends ResultSetMapper<List<Map.Entry<Integer, GigEntity>>> {
	
	@Override
	protected List<Map.Entry<Integer, GigEntity>> prepare() {
		return new ArrayList<>();
	}
	
	@Override
	protected void map(ResultSet resultSet, List<Map.Entry<Integer, GigEntity>> gigs) throws SQLException {
		int id = resultSet.getInt("gig_id");
		
		GigEntity gig = computeGig(gigs, id);
		gig.setDeleted(resultSet.getBoolean("deleted"));
		
		// neuer GigEntity
		if(gig.getDate() == null) {
			Date dt = resultSet.getDate("gig_date");
			gig.setDate(dt);
			String city = resultSet.getString("city_name");
			gig.setCityName(city);
		}
		
		// Künstler auf dem GigEntity
		ArtistGigEntity artistGig = new ArtistGigEntity();
		String          artist    = resultSet.getString("artist_name");
		artistGig.setArtistName(artist);
		String genre = resultSet.getString("genre_name");
		artistGig.setGenreName(genre);
		Date time = resultSet.getTime("gig_time");
		artistGig.setTime(time);
		artistGig.setId(resultSet.getInt("artistId"));
		
		gig.getArtistGigs().add(artistGig);
	}
	
	private static GigEntity computeGig(Collection<Map.Entry<Integer, GigEntity>> resultGigs, int gigId) {
		HashMap<Integer, GigEntity> repo = new HashMap<>();
		resultGigs.forEach(e -> repo.put(e.getKey(), e.getValue()));
		Map.Entry<Integer, GigEntity> gigEntry = resultGigs.stream().filter(e -> e.getKey() == gigId).findFirst().orElse(new AbstractMap.SimpleEntry<>(gigId, new GigEntity(gigId)));
		if(!repo.containsKey(gigId)) {
			resultGigs.add(gigEntry);
		}
		return gigEntry.getValue();
	}
}
