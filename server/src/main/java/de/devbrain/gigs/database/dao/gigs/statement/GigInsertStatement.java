package de.devbrain.gigs.database.dao.gigs.statement;

import de.devbrain.gigs.database.dao.user.statement.InsertStatement;
import de.devbrain.gigs.database.dao.utils.Parameter;
import de.devbrain.gigs.database.dao.utils.QueryFormat;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bjoern Frohberg, DevBrain IT Solutions GmbH
 */
public class GigInsertStatement extends InsertStatement {
	
	public GigInsertStatement(Connection connection) throws SQLException {
		super(connection,
		      new QueryFormat("INSERT INTO gig(date, city_fk) VALUES (${date}, ${cityId})")
				      .declareParameter(0, "date", Parameter.ValueTypeFormat.DATE)
				      .declareParameter(1, "cityId", Parameter.ValueTypeFormat.INT));
	}
}
