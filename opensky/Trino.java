package opensky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Trino 
{
	public static final String LOGIN_URL = "https://trino.opensky-network.org/";
	
	private static final String DATABASE_URL = "jdbc:trino://trino.opensky-network.org:443";
	
	private static Connection connection = null;
	
	private static String currentErrorMessage = "No Errors... yet";
	
	
	/**
	 * Checks if the connection is not null.
	 * 
	 * @return true if connection is not null, otherwise false
	 */
	public static boolean hasConnection()
	{
		return connection != null;
	}
	
	
	/**
	 * Returns the error message of the most recent exception caught.
	 * 
	 * @return error message
	 */
	public static String getErrorMessage()
	{
		return currentErrorMessage;
	}
	
	
	/**
	 * Trys to create a new connection to the OpenSky Database with the given username.<br>
	 * If the operation is successful, then the method will return true, otherwise, false.
	 * 
	 * @param username the username used to login through Trino
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean createConnection(final String username)
	{
		final Properties properties = new Properties();
		properties.setProperty("user", String.format("\'%s\'", username));
		properties.setProperty("externalAuthentication", "true");
		
		try
		{
			connection = DriverManager.getConnection(DATABASE_URL, properties);	
			return true;
		}
		catch(SQLException e)
		{
			currentErrorMessage = e.getMessage();
			return false;
		}
	}
	
	
}
