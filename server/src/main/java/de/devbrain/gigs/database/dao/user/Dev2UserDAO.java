package de.devbrain.gigs.database.dao.user;

import de.devbrain.gigs.database.dao.user.entity.UserEntity;
import de.devbrain.gigs.database.dao.user.statement.*;
import de.devbrain.gigs.database.model.DefaultMySqlDatabase;
import de.devbrain.gigs.database.model.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class Dev2UserDAO extends DefaultMySqlDatabase implements IUserDao {
	
	private final Query<UserEntity> getUserByLoginWithPasswordQuery;
	private final Query<Boolean>    hasUserWithLoginQuery;
	private final InsertStatement   registerUserStatement;
	private final Query<List<Role>> getAllUserRolesQuery;
	private final Query<Integer>    updateUserStatement;
	private final Query<String>     getUserPasswordHash;
	private final Query<Boolean>    isUserAModeratorQuery;
	
	public Dev2UserDAO(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
		connect();
		this.getUserByLoginWithPasswordQuery = new GetUserByLoginWithPasswordQuery(connection);
		this.hasUserWithLoginQuery = new HasUserWithLoginQuery(connection);
		this.registerUserStatement = new InsertNewUserStatement(connection);
		this.updateUserStatement = new UpdateUserStatement(connection);
		this.getAllUserRolesQuery = new GetAllUserRolesQuery(connection);
		this.getUserPasswordHash = new GetUserPasswordHashQuery(connection);
		this.isUserAModeratorQuery = new IsUserAModeratorQuery(connection);
	}
	
	@Override
	public UserEntity getUserByLogin(String username, String passwordHash) {
		UserEntity result = this.getUserByLoginWithPasswordQuery.execute(username, passwordHash);
		if(result != null && result.getId() > 0) {
			return result;
		}
		return null;
	}
	
	@Override
	public boolean hasUsername(String username) {
		return this.hasUserWithLoginQuery.execute(username);
	}
	
	@Override
	public int registerUserReturnUserId(String username, String login, String passwordHash, int user_role_id, boolean update) {
		Query<Integer> query;
		if(update) {
			query = this.updateUserStatement;
		} else {
			query = this.registerUserStatement;
		}
		query.execute(username, login, passwordHash, user_role_id);
		UserEntity user = getUserByLogin(login, passwordHash);
		if(user != null && user.getRoleId() == user_role_id) {
			return user.getId();
		} else {
			return -1;
		}
	}
	
	@Override
	public List<Role> getRoles() {
		return this.getAllUserRolesQuery.execute();
	}
	
	@Override
	public String getPasswordHash(String usernameLogin) {
		return this.getUserPasswordHash.execute(usernameLogin);
	}
	
	@Override
	public boolean isUserModerator(int userId) {
		return this.isUserAModeratorQuery.execute(userId);
	}
	
}