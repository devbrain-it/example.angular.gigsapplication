package de.devbrain.gigs.model;

import de.devbrain.gigs.database.dao.artist.Dev2ArtistDAO;
import de.devbrain.gigs.database.dao.gigs.Dev2GigsDAO;
import de.devbrain.gigs.database.dao.gigs.IGigsDao;
import de.devbrain.gigs.database.dao.gigs.entity.ArtistGigEntity;
import de.devbrain.gigs.database.dao.gigs.entity.GigEntity;
import de.devbrain.gigs.database.dao.gigs.model.UserGigInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static de.devbrain.gigs.database.model.Query.parseTime;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class GigRepository {
	
	private final IGigsDao         gigsDao;
	private final SimpleDateFormat dateFormat;
	private final SimpleDateFormat timeFormat;
	private final Dev2ArtistDAO    artistDao;
	
	public GigRepository() throws SQLException, IOException, ClassNotFoundException {
		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		timeFormat = new SimpleDateFormat("HH:mm");
		
		gigsDao = new Dev2GigsDAO("localhost");
		artistDao = new Dev2ArtistDAO("localhost");
	}
	
	public List<UserGigInfo> getUserGigs(int userId) {
		return gigsDao.getUserGigsByUserId(userId);
	}
	
	public List<GigData> getAllGigs() throws SQLException {
		List<GigData>         result = new ArrayList<>();
		Collection<GigEntity> gigs   = gigsDao.getAllGigs();
		for (GigEntity gig : gigs) {
			for (ArtistGigEntity artistGig : gig.getArtistGigs()) {
				result.add(convertToGigData(gig, artistGig));
			}
		}
		return result;
	}
	
	private GigData convertToGigData(GigEntity gig, ArtistGigEntity artistGig) {
		GigData gigData = new GigData();
		gigData.setArtist(artistGig.getArtistName());
		gigData.setCity(gig.getCityName());
		gigData.setDate(dateFormat.format(gig.getDate()));
		gigData.setGenre(artistGig.getGenreName());
		gigData.setId(gig.getId());
		gigData.setTime(timeFormat.format(artistGig.getTime()));
		gigData.setDeleted(gig.isDeleted());
		gigData.setArtistId(artistGig.getId());
		return gigData;
	}
	
	public void storeForUser(int userid, int gigid) {
		gigsDao.attendUserForGig(userid, gigid);
	}
	
	public void deleteFromUser(int userid, int gigid) {
		gigsDao.unattendUserFromGig(userid, gigid);
	}
	
	public int size() {
		try {
			return getAllGigs().size();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteGig(int gigId) {
		this.gigsDao.deleteGigById(gigId);
	}
	
	public int registerGig(Date date, int cityId) {
		return gigsDao.registerGig(date, cityId);
	}
	
	public void attendArtistToGig(int gigId, int artistId, String time, int genreId) throws ParseException {
		artistDao.linkToGig(artistId, gigId, genreId, parseTime(time));
	}
}
