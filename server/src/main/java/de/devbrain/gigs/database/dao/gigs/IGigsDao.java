package de.devbrain.gigs.database.dao.gigs;

import de.devbrain.gigs.database.dao.gigs.entity.GigEntity;
import de.devbrain.gigs.database.dao.gigs.model.UserGigInfo;
import de.devbrain.gigs.database.model.IDao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface IGigsDao extends IDao {
	
	Collection<GigEntity> getAllGigs() throws SQLException;
	
	List<UserGigInfo> getUserGigsByUserId(int userId);
	
	void attendUserForGig(int userid, int gigid);
	
	void unattendUserFromGig(int userid, int gigid);
	
	void deleteGigById(int gigId);
	
	int registerGig(Date date, int cityId);
}
