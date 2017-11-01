package de.devbrain.gigs.service;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.model.UserRepository;

import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
@Path("gigs/rest")
public class UserService extends AbstractService {
	
	private final UserRepository repository;
	
	public UserService() throws SQLException, IOException, ClassNotFoundException {
		repository = new UserRepository();
	}
	
	/**
	 * Meldet den Benutzer an, wenn alles stimmt und liefert den Token des Benutzers.
	 */
	@Path("user/login")
	@POST
	@Produces("application/json")
	public Response login(@FormParam("login") String username, @FormParam("password") String pwHash) throws IOException, ServletException {
		final String method = "POST";
		// neu
		UserEntity user = repository.getUserByLogin(username, pwHash);
		if(user != null) {
			getRequestTyped();
			return crossDomainHeader(Response.ok(toJson(user)), method).build();
		}
		return postResponseValue(null, method);
	}
	
	/**
	 * Registriert und speichert einen neuen Benutzer und liefert bei Erfolg die Benutzer ID zurück
	 */
	@Path("user/profile/update")
	@POST
	@Produces("application/json")
	public Response updateUserProfile(@FormParam("login") String login, @FormParam("password") String passwordHash, @FormParam("userName") String visUserName, @FormParam("role") int user_role_id) throws IOException {
		
		if(passwordHash == null || passwordHash.equalsIgnoreCase("0")) {
			passwordHash = repository.getPasswordHashByUserLogin(login);
			if(passwordHash == null) {
				passwordHash = "0";
			}
		}
		
		String exceptionOrOK;
		if(!repository.hasUser(login)) {
			exceptionOrOK = String.format("User unknown '%s'", login);
		} else {
			int     user_id = repository.registerUpdate(login, passwordHash, user_role_id, visUserName);
			boolean success = user_id == repository.getUserByLogin(login, passwordHash).getId();
			if(success) {
				getRequestTyped();
			}
			exceptionOrOK = String.valueOf(success);
		}
		return postResponseValue(exceptionOrOK);
	}
	
	/**
	 * Registriert und speichert einen neuen Benutzer und liefert bei Erfolg die Benutzer ID zurück
	 */
	@Path("user/register")
	@POST
	@Produces("application/json")
	public Response registerUser(@FormParam("login") String login, @FormParam("password") String passwordHash, @FormParam("userName") String visUserName, @FormParam("role") int user_role_id) throws IOException {
		String exceptionOrOK = "";
		if(repository.hasUser(login)) {
			exceptionOrOK = String.format("Username already taken '%s'", login);
		} else {
			int user_id = repository.register(login, passwordHash, user_role_id);
			exceptionOrOK = String.valueOf(user_id);
		}
		return postResponseValue(exceptionOrOK);
	}
	
	@Path("roles")
	@GET
	@Produces("application/json")
	public Response getRoles() throws IOException {
		getRequestTyped();
		return postResponseValue(repository.getUserRoles(), "GET");
	}
}
