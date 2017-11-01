package de.devbrain.gigs.service;

import com.sun.org.glassfish.external.probe.provider.annotations.ProbeParam;
import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.artist.Dev2ArtistDAO;
import de.devbrain.gigs.database.dao.artist.IArtistDao;
import de.devbrain.gigs.database.dao.city.Dev2CityDAO;
import de.devbrain.gigs.database.dao.city.ICityDao;
import de.devbrain.gigs.database.dao.genre.Dev2GenreDAO;
import de.devbrain.gigs.database.dao.genre.IGenreDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
@Path("gigs/rest")
public class UnitService extends AbstractService {
	
	private final IArtistDao       artistDao;
	private final ICityDao         cityDao;
	private final IGenreDao        genreDao;
	private final SimpleDateFormat timeFormat1;
	private final SimpleDateFormat timeFormat2;
	
	public UnitService() throws SQLException, IOException, ClassNotFoundException {
		String host = "localhost";
		this.artistDao = new Dev2ArtistDAO(host);
		this.cityDao = new Dev2CityDAO(host);
		this.genreDao = new Dev2GenreDAO(host);
		this.timeFormat1 = new SimpleDateFormat("HH:mm:ss");
		this.timeFormat2 = new SimpleDateFormat("HH:mm");
	}
	
	@Path("artists")
	@GET
	@Produces("application/json")
	public Response getArtists() throws IOException {
		List<MinifiedObject> artists = artistDao.fetchAll();
		getRequestTyped();
		return Response.ok(toJson(artists)).build();
	}
	
	@Path("cities")
	@GET
	@Produces("application/json")
	public Response getCities() throws IOException {
		List<MinifiedObject> cities = cityDao.fetchAll();
		getRequestTyped();
		return Response.ok(toJson(cities)).build();
	}
	
	@Path("genres")
	@GET
	@Produces("application/json")
	public Response getGenres() throws IOException {
		List<MinifiedObject> genres = genreDao.fetchAll();
		getRequestTyped();
		return Response.ok(toJson(genres)).build();
	}
	
	
	@Path("artist/{artist_name}")
	@PUT
	@Produces("application/json")
	public Response putArtist(@PathParam("artist_name") String name) throws IOException {
		MinifiedObject newArtist = new MinifiedObject();
		newArtist.setName(name);
		int id = artistDao.insert(newArtist);
		getRequestTyped();
		return Response.ok(toJson(id)).build();
	}
	
	@Path("city/{city_name}")
	@PUT
	@Produces("application/json")
	public Response putCity(@PathParam("city_name") String name) throws IOException {
		MinifiedObject newCity = new MinifiedObject();
		newCity.setName(name);
		int id = cityDao.insert(newCity);
		getRequestTyped();
		return Response.ok(toJson(id)).build();
	}
	
	@Path("genre/{genre_name}")
	@PUT
	@Produces("application/json")
	public Response putGenre(@PathParam("genre_name") String name) throws IOException {
		MinifiedObject newGenre = new MinifiedObject();
		newGenre.setName(name);
		int id = genreDao.insert(newGenre);
		getRequestTyped();
		return Response.ok(toJson(id)).build();
	}
	
	
	@Path("artist/{artist_id:\\d+}")
	@DELETE
	@Produces("application/json")
	public Response deleteArtist(@PathParam("artist_id") int artist_id) throws IOException {
		artistDao.delete(artist_id);
		getRequestTyped();
		return Response.ok().build();
	}
	
	@Path("city/{city_id:\\d+}")
	@DELETE
	@Produces("application/json")
	public Response deleteCity(@PathParam("city_id") int city_id) throws IOException {
		artistDao.delete(city_id);
		getRequestTyped();
		return Response.ok().build();
	}
	
	@Path("genre/{genre_id:\\d+}")
	@DELETE
	@Produces("application/json")
	public Response deleteGenre(@PathParam("genre_id") int genre_id) throws IOException {
		artistDao.delete(genre_id);
		getRequestTyped();
		return Response.ok().build();
	}
	
	
	@Path("artist/{artist_id:\\d+}/name/{artist_name}")
	@PUT
	@Produces("application/json")
	public Response updateArtist(@ProbeParam("artist_id") int id, @PathParam("artist_name") String name) throws IOException {
		artistDao.find(id).setName(name);
		getRequestTyped();
		return Response.ok().build();
	}
	
	@Path("city/{city_id:\\d+}/name/{city_name}")
	@PUT
	@Produces("application/json")
	public Response updateCity(@ProbeParam("city_id") int id, @PathParam("city_name") String name) throws IOException {
		cityDao.find(id).setName(name);
		getRequestTyped();
		return Response.ok().build();
	}
	
	@Path("genre/{genre_id:\\d+}/name/{genre_name}")
	@PUT
	@Produces("application/json")
	public Response updateGenre(@ProbeParam("genre_id") int id, @PathParam("genre_name") String name) throws IOException {
		genreDao.find(id).setName(name);
		getRequestTyped();
		return Response.ok().build();
	}
	
	
	
	@Path("artist/{artist_id:\\+}/gig/{gig_id:\\d+}/genre/{genre_id:\\+}/time/{time:(0?\\d|1\\d|2[0-3]):[0-5]?\\d(:[0-5]?\\d)?}")
	@PUT
	@Produces("application/json")
	public Response linkArtistToGig(@PathParam("artist_id") int artist_id, @PathParam("gig_id") int gig_id, @PathParam("genre_id") int genre_id, @PathParam("time") String timeString) throws IOException {
		try {
			artistDao.linkToGig(artist_id, gig_id, genre_id, timeFormat1.parse(timeString));
		} catch (ParseException ex) {
			try {
				artistDao.linkToGig(artist_id, gig_id, genre_id, timeFormat2.parse(timeString));
			} catch (ParseException exception) {
				throw new RuntimeException("Timeformat error: " + timeString, exception);
			}
		}
		getRequestTyped();
		return Response.ok().build();
	}
	
	@Path("artist/{artist_id:\\+}/gig/{gig_id:\\d+}")
	@DELETE
	@Produces("application/json")
	public Response unlinkArtistFromGig(@PathParam("artist_id") int artist_id, @PathParam("gig_id") int gig_id) throws IOException {
		artistDao.unlinkFromGig(artist_id, gig_id);
		getRequestTyped();
		return Response.ok().build();
	}
}
