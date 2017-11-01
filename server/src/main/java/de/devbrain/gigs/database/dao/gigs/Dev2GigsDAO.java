package de.devbrain.gigs.database.dao.gigs;

import de.devbrain.gigs.database.dao.gigs.entity.GigEntity;
import de.devbrain.gigs.database.dao.gigs.model.UserGigInfo;
import de.devbrain.gigs.database.dao.gigs.statement.*;
import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.model.DefaultMySqlDatabase;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.Statement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static de.devbrain.gigs.database.model.Query.date;
import static java.util.stream.Collectors.toList;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class Dev2GigsDAO extends DefaultMySqlDatabase implements IGigsDao {
	
	private final Query<List<Map.Entry<Integer, GigEntity>>> getAllGigsQuery;
	private final Query<List<UserGigInfo>>                   getUserGigsQuery;
	private final Statement                                  attendUserToGigStatement;
	private final Statement                                  unattendUserFromGigStatement;
	private final Statement                                  deleteGigByIdStatment;
	private final Statement                                  deleteUnattachedGigsStatement;
	private final InsertStatement                            registerGigQuery;
	
	public Dev2GigsDAO(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
		connect();
		this.getAllGigsQuery = new GetAllGigsQuery(connection);
		this.getUserGigsQuery = new GetUserGigsQuery(connection);
		this.attendUserToGigStatement = new AttendUserToGigStatement(connection);
		this.unattendUserFromGigStatement = new UnattendUserFromGigStatement(connection);
		this.deleteGigByIdStatment = new DeleteGigStatement(connection);
		this.deleteUnattachedGigsStatement = new DeleteUnattachedGigsStatement(connection);
		this.registerGigQuery = new GigInsertStatement(connection);
	}
	
	@Override
	protected String getDatabaseName() {
		return "dev2gigs";
	}
	
	@Override
	public Collection<GigEntity> getAllGigs() {
		return getAllGigsQuery.execute().stream().map(Map.Entry::getValue).collect(toList());
	}
	
	@Override
	public List<UserGigInfo> getUserGigsByUserId(int userId) {
		return this.getUserGigsQuery.execute(userId);
	}
	
	@Override
	public void attendUserForGig(int userid, int gigid) {
		this.attendUserToGigStatement.execute(userid, gigid);
	}
	
	@Override
	public void unattendUserFromGig(int userid, int gigid) {
		this.unattendUserFromGigStatement.execute(userid, gigid);
		
		cleanDeletedGigs();
	}
	
	@Override
	public void deleteGigById(int gigId) {
		this.deleteGigByIdStatment.execute(gigId);
		
		cleanDeletedGigs();
	}
	
	@Override
	public int registerGig(Date date, int cityId) {
		// 0 = datum: String
		// 1 = cityId: Integer
		return this.registerGigQuery.execute(date(date), cityId);
	}
	
	private void cleanDeletedGigs() {
		this.deleteUnattachedGigsStatement.execute();
	}
	
}