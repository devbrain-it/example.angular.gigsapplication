package de.devbrain.gigs.database.dao;

import de.devbrain.gigs.database.dao._model.MinifiedObject;
import de.devbrain.gigs.database.model.DefaultMySqlDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public abstract class MinifiedObjectMySqlDatabase extends DefaultMySqlDatabase {
	
	public MinifiedObjectMySqlDatabase(String host) throws IOException, SQLException, ClassNotFoundException {
		super(host);
	}
	
	public List<MinifiedObject> getRange(int index, int length) {
		List<MinifiedObject> list     = fetchAll();
		int                  maxIndex = min(list.size(), index + length);
		return new ArrayList<>(list.subList(index, maxIndex));
	}
	
	public int countAll() {
		return fetchAll().size();
	}
	
	@Override
	protected String getDatabaseName() {
		return "dev2gigs";
	}
	
	protected abstract List<MinifiedObject> fetchAll();
}
