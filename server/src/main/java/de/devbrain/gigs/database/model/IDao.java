package de.devbrain.gigs.database.model;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public interface IDao {
	
	void connect() throws IOException, ClassNotFoundException, SQLException;
}
