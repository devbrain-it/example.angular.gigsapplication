package de.devbrain.gigs.database.dao.genre;

import de.devbrain.gigs.database.dao.MinifiedObjectMySqlDatabase;
import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.genre.statement.*;
import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.model.Query;
import de.devbrain.gigs.database.model.Statement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class Dev2GenreDAO extends MinifiedObjectMySqlDatabase implements IGenreDao {
	
	private final Query<List<MinifiedObject>> fetchAllGenresQuery;
	private final InsertStatement             insertGenreStatement;
	private final Statement                   updateGenreNameStatement;
	private final Statement                   deleteGenresWithIdsStatement;
	private final Query<MinifiedObject>       getGenreByIdQuery;
	
	public Dev2GenreDAO(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
		connect();
		this.fetchAllGenresQuery = new FetchAllGenresQuery(connection);
		this.insertGenreStatement = new InsertGenreStatement(connection);
		this.updateGenreNameStatement = new GenreUpdateStatement(connection);
		this.deleteGenresWithIdsStatement = new GenresDeleteStatement(connection);
		this.getGenreByIdQuery = new GetGenreByIdQuery(connection);
	}
	
	@Override
	public List<MinifiedObject> fetchAll() {
		return this.fetchAllGenresQuery.execute();
	}
	
	@Override
	public int insert(MinifiedObject singleInsert) {
		return this.insertGenreStatement.execute(singleInsert.getName());
	}
	
	@Override
	public void save(MinifiedObject single) {
		this.updateGenreNameStatement.execute(single.getId(), single.getName());
	}
	
	@Override
	public void delete(Collection<Integer> ids) {
		this.deleteGenresWithIdsStatement.execute(ids);
	}
	
	@Override
	public MinifiedObject find(int id) {
		return this.getGenreByIdQuery.execute(id);
	}
	
}