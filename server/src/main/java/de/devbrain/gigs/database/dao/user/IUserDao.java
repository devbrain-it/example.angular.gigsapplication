package de.devbrain.gigs.database.dao.user;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.database.model.IDao;

import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public interface IUserDao extends IDao {
	
	UserEntity getUserByLogin(String username, String passwordHash);
	
	boolean hasUsername(String username);
	
	int registerUserReturnUserId(String username, String login, String passwordHash, int user_role_id, boolean update);
	
	List<Role> getRoles();
	
	String getPasswordHash(String usernameLogin);
	
	boolean isUserModerator(int userId);
}
