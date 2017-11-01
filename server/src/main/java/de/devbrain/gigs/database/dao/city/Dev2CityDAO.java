package de.devbrain.gigs.database.dao.city;

import de.devbrain.gigs.database.dao.MinifiedObjectMySqlDatabase;
import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.dao.city.statement.*;
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
public class Dev2CityDAO extends MinifiedObjectMySqlDatabase implements ICityDao {
	
	private final Query<List<MinifiedObject>> fetchAllCitiesQuery;
	private final InsertStatement             insertCityStatement;
	private final Statement                   updateCityNameStatement;
	private final Statement                   deleteCitiesWithIdsStatement;
	private final Query<MinifiedObject>       getCityByIdQuery;
	
	public Dev2CityDAO(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
		connect();
		this.fetchAllCitiesQuery = new FetchAllCitiesQuery(connection);
		this.insertCityStatement = new InsertCityStatement(connection);
		this.updateCityNameStatement = new CityUpdateStatement(connection);
		this.deleteCitiesWithIdsStatement = new CitiesDeleteStatement(connection);
		this.getCityByIdQuery = new GetCityByIdQuery(connection);
	}
	
	@Override
	public List<MinifiedObject> fetchAll() {
		return this.fetchAllCitiesQuery.execute();
	}
	
	@Override
	public int insert(MinifiedObject singleInsert) {
		return this.insertCityStatement.execute(singleInsert.getName());
	}
	
	@Override
	public void save(MinifiedObject single) {
		this.updateCityNameStatement.execute(single.getId(), single.getName());
	}
	
	@Override
	public void delete(Collection<Integer> ids) {
		this.deleteCitiesWithIdsStatement.execute(ids);
	}
	
	@Override
	public MinifiedObject find(int id) {
		return this.getCityByIdQuery.execute(id);
	}
	
}