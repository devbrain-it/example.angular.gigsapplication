package de.devbrain.gigs.model;

import de.devbrain.gigs.database.dao.user.Dev2UserDAO;
import de.devbrain.gigs.database.dao.user.IUserDao;
import de.devbrain.gigs.database.dao.user.Role;
import de.devbrain.gigs.database.dao.user.entity.UserEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class UserRepository {
	
	private final IUserDao             userDao;
	
	public UserRepository() throws SQLException, IOException, ClassNotFoundException {
		userDao = new Dev2UserDAO("localhost");
	}
	
	public UserEntity getUserByLogin(String username, String pwHash) {
		return userDao.getUserByLogin(username, pwHash);
	}
	
	public boolean hasUser(String username) {
		return userDao.hasUsername(username);
	}
	
	public int register(String login, String pwHash, int user_role_id) {
		return userDao.registerUserReturnUserId(login, login.toLowerCase(), pwHash, user_role_id, false);
	}
	
	public int registerUpdate(String login, String pwHash, int user_role_id, String visUserName) {
		return userDao.registerUserReturnUserId(visUserName, login.toLowerCase(), pwHash, user_role_id, true);
	}
	
	public Collection<Role> getUserRoles() {
		return userDao.getRoles();
	}
	
	public String getPasswordHashByUserLogin(String login) {
		return userDao.getPasswordHash(login);
	}
	
	public boolean isUserModerator(int userId) {
		return userDao.isUserModerator(userId);
	}
}