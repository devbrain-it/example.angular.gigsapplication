package de.devbrain.gigs.database.dao.artist;

import de.devbrain.gigs.database.dao.MinifiedObjectMySqlDatabase;
import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.artist.statement.*;
import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.Statement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class Dev2ArtistDAO extends MinifiedObjectMySqlDatabase implements IArtistDao {
	
	private final Query<List<MinifiedObject>> fetchAllArtistQuery;
	private final Statement                   linkGigWithArtistStatement;
	private final Statement                   unlinkGigFromArtitsStatement;
	private final InsertStatement             insertArtistStatement;
	private final Statement                   updateArtistNameStatement;
	private final Statement                   deleteArtistsWithIdsStatement;
	private final Query<MinifiedObject>       getArtistByIdQuery;
	
	public Dev2ArtistDAO(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
		connect();
		this.fetchAllArtistQuery = new FetchAllArtitsQuery(connection);
		this.linkGigWithArtistStatement = new LinkGigWithArtistStatement(connection);
		this.unlinkGigFromArtitsStatement = new UnlinkArtistFromGigStatement(connection);
		this.insertArtistStatement = new InsertArtistStatement(connection);
		this.updateArtistNameStatement = new ArtistUpdateStatement(connection);
		this.deleteArtistsWithIdsStatement = new ArtistsDeleteStatement(connection);
		this.getArtistByIdQuery = new GetArtistByIdQuery(connection);
	}
	
	@Override
	public List<MinifiedObject> fetchAll() {
		return this.fetchAllArtistQuery.execute();
	}
	
	@Override
	public void linkToGig(int artistId, int gigId, int genreId, Date time) {
		this.linkGigWithArtistStatement.execute(gigId, artistId, genreId, time);
	}
	
	@Override
	public void unlinkFromGig(int artist, int gigId) {
		this.unlinkGigFromArtitsStatement.execute(gigId, artist);
		// TODO gig löschen, wenn es keine Künstlerzuordnungen mehr gibt
		// INFO - funktioniert nur, wenn kein Benutzer für den Gig angemeldet ist
	}
	
	@Override
	public int insert(MinifiedObject singleInsert) {
		return this.insertArtistStatement.execute(singleInsert.getName());
	}
	
	@Override
	public void save(MinifiedObject single) {
		this.updateArtistNameStatement.execute(single.getId(), single.getName());
	}
	
	@Override
	public void delete(Collection<Integer> ids) {
		this.deleteArtistsWithIdsStatement.execute(ids);
	}
	
	@Override
	public MinifiedObject find(int id) {
		return this.getArtistByIdQuery.execute(id);
	}
	
}