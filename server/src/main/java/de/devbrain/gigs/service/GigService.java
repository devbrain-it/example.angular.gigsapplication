package de.devbrain.gigs.service;

import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.model.GigData;
import de.devbrain.gigs.model.GigRepository;
import de.devbrain.gigs.model.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//import static java.util.Arrays.asList;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
@SuppressWarnings("SameParameterValue")
@Path("gigs/rest")
public class GigService extends AbstractService {
	
	private final GigRepository  gigRepository;
	private final UserRepository userRepository;
	
	public GigService() throws SQLException, IOException, ClassNotFoundException {
		gigRepository = new GigRepository();
		userRepository = new UserRepository();
	}
	
	@Path("gigs/summary")
	@GET
	public int countGigs() {
		return gigRepository.size();
	}
	
	@Path("gigs")
	@GET
	@Produces("application/json")
	public Response getGigsAll() throws IOException, SQLException {
		return getGigsRange(0, countGigs());
	}
	
	@Path("gigs/from/{index:\\d+}/count/{count:\\d++}")
	@GET
	@Produces("application/json")
	public Response getGigsRange(@PathParam("index") int index, @PathParam("count") int count) throws IOException, SQLException {
		List<GigData> all      = gigRepository.getAllGigs();
		List<GigData> filtered = new ArrayList<>(all.subList(index, Math.min(index + count, all.size())));
		return Response.ok(toJson(filtered)).build();
	}
	
	@Path("user/{userid:-?\\d+}/gigs")
	@GET
	@Produces("application/json")
	public Response getGigs(@PathParam("userid") int userId) throws IOException {
		return Response.ok(toJson(gigRepository.getUserGigs(userId))).build();
	}
	
	@Path("user/{userid:\\d+}/gig/{gigid:\\d+}/put")
	@GET
	@Produces("application/json")
	public Response attendUserForGig(@PathParam("userid") int userid, @PathParam("gigid") int gigid) throws IOException {
		Response.ResponseBuilder builder;
		try {
			getRequestTyped();
			gigRepository.storeForUser(userid, gigid);
			builder = Response.ok(toJson(true));
		} catch (Exception e) {
			builder = sendError(e);
		}
		return crossDomainHeader(builder, "GET").build();
	}
	
	@Path("user/{userid:\\d+}/gig/{gigid:\\d+}/delete")
	@GET
	@Produces("application/json")
	public Response unattendUserFromGig(@PathParam("userid") int userid, @PathParam("gigid") int gigid) throws IOException {
		Response.ResponseBuilder builder;
		try {
			getRequestTyped();
			gigRepository.deleteFromUser(userid, gigid);
			builder = Response.ok(toJson(true));
		} catch (Exception e) {
			builder = sendError(e);
		}
		return crossDomainHeader(builder, "GET").build();
	}
	
	@Path("gig/delete")
	@DELETE
	@Produces("application/json")
	public Response deleteGig(@FormParam("gigId") int gigId, @FormParam("userId") int userId) throws IOException {
		Response.ResponseBuilder builder;
		try {
			getRequestTyped();
			if(!userRepository.isUserModerator(userId)) {
				String error = String.format("Privileges error for user '%d'", userId);
				builder = Response.ok(toJson(error));
			} else {
				gigRepository.deleteGig(gigId);
				builder = Response.ok(toJson(true));
			}
		} catch (Exception e) {
			builder = sendError(e);
		}
		return crossDomainHeader(builder, "DELETE").build();
	}
	
	@Path("gig")
	@POST
	@Produces("application/json")
	public Response registerGig(@FormParam("date") String date, @FormParam("time") String time, @FormParam("artistId") int artistId, @FormParam("genreId") int genreId, @FormParam("cityId") int cityId) throws ParseException, IOException {
		
		if(!isArtistValidForGig(artistId, cityId, date, time)) {
			Response.ResponseBuilder builder = sendError(new RuntimeException("Artist already hired for another gig!"));
			return crossDomainHeader(builder, "POST").build();
		}
		
		// gig
		int gigId = gigRepository.registerGig(Query.parseDate(date), cityId);
		if(gigId > 0) {
			// artist : gig
			gigRepository.attendArtistToGig(gigId, artistId, time, genreId);
			
			getRequestTyped();
			return crossDomainHeader(Response.ok(gigId), "POST").build();
		}
		return crossDomainHeader(Response.status(Response.Status.BAD_REQUEST), "POST").build();
	}
	
	private boolean isArtistValidForGig(int artistId, int cityId, String date, String time) {
		// TODO implement
		return false;
	}
	
	private Response.ResponseBuilder sendError(Exception e) throws IOException {
		Response.ResponseBuilder builder;
		String                   error = e.toString();
		System.err.println(error);
		builder = Response.ok(toJson(error));
		return builder;
	}
}
