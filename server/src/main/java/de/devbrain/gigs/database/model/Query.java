package de.devbrain.gigs.database.model;

import de.devbrain.gigs.utils.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static java.lang.String.join;

/**
 * @author Bjoern Frohberg, DevBrains IT Solutions GmbH
 */
@SuppressWarnings("WeakerAccess")
public abstract class Query<TResult> {
	
	public static final String REGX_TIME     = "(0?\\d|1\\d|2[0-3]):[0-5]?\\d(:[0-5]?\\d)?";
	public static final String REGX_DATE     = "(0?\\d|[1-2]\\d|3[0-1])\\.(0?\\d|1[0-2])\\.\\d{4}";
	public static final String REGX_DATETIME = REGX_DATE + " " + REGX_TIME;
	
	private final Connection               connection;
	private final ResultSetMapper<TResult> mapper;
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	protected Query(Connection connection, ResultSetMapper<TResult> mapper) throws SQLException {
		Validate.notNull(connection);
		this.connection = connection;
		this.mapper = mapper;
	}
	
	public static String text(Object value) {
		return String.format("'%s'", value);
	}
	
	public static int integer(Object value) {
		if(value instanceof Integer) {
			return (int) value;
		}
		return value instanceof String && value.toString().matches("-?\\d+")
		       ? Integer.parseInt(value.toString())
		       : 0;
	}
	
	public static String time(Object value) {
		if(value instanceof Date) {
			TIME_FORMAT.setLenient(false);
			return TIME_FORMAT.format(value);
		}
		return value instanceof String && value.toString().matches(REGX_TIME)
		       ? value.toString()
		       : "00:00:00";
	}
	
	public static String datetime(Object value) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		if(value instanceof Date) {
			format.setLenient(false);
			return format.format(value);
		}
		return value instanceof String && value.toString().matches(REGX_DATETIME)
		       ? value.toString()
		       : "";
	}
	
	public static String date(Object value) {
		if(value instanceof Date) {
			DATE_FORMAT.setLenient(false);
			return DATE_FORMAT.format(value);
		}
		return value instanceof String && value.toString().matches(REGX_DATE)
		       ? value.toString()
		       : "";
	}
	
	public static Date parseDate(String date) throws ParseException {
		return DATE_FORMAT.parse(date);
	}
	
	public static Date parseTime(String time) throws ParseException {
		return TIME_FORMAT.parse(time);
	}
	
	public static String joined(Object value) {
		if(value instanceof Collection<?>) {
			return join(", ", (Collection) value);
		}
		return "";
	}
	
	public TResult execute(Object... parameters) {
		try {
			PreparedStatement statement = init(connection, parameters);
			System.out.println("SQL: " + statement.toString());
			return performStatement(statement, mapper);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected TResult performStatement(PreparedStatement statement, ResultSetMapper<TResult> mapper) throws SQLException {
		if(mapper == null) {
			statement.execute();
			return null;
		} else {
			String query = statement.toString().toLowerCase();
			if(query.contains(" update ") && query.contains(" set ")) {
				statement.executeUpdate();
				return null;
			} else {
				return mapper.map(statement.executeQuery());
			}
		}
	}
	
	protected abstract PreparedStatement init(Connection connection, Object... parameters) throws SQLException;
}
