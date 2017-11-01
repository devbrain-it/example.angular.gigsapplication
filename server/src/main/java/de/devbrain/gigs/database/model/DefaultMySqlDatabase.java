package de.devbrain.gigs.database.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.glassfish.jersey.internal.util.Base64.decodeAsString;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public abstract class DefaultMySqlDatabase implements IDao {
	
	private final String dataSourceURL;
	protected Connection connection;
	
	public DefaultMySqlDatabase(final String host) throws IOException, SQLException, ClassNotFoundException {
		this.dataSourceURL = String.format("%s:%d/%s", host, 3306, getDatabaseName());
	}
	
	@Override
	public void connect() throws IOException, ClassNotFoundException, SQLException {
		String driver           = "com.mysql.jdbc.Driver";
		String connectionString = "jdbc:mysql://" + dataSourceURL;
		Class.forName(driver);
		connection = DriverManager.getConnection(connectionString,
		                                         decodeAsString(getUsernameBase64()),
		                                         decodeAsString(getPasswordBase64()));
	}
	
	protected String getPasswordBase64() {
		return "dYXJub2xkNDcwMA==IT";
	}
	
	protected String getUsernameBase64() {
		return "dZWNyZWF0b3JzIT";
	}
	
	protected String getDatabaseName() {
		return "dev2gigs";
	}
	
}
