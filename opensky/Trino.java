package opensky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import application.Application;
import application.Log;
import guiutil.OptionPane;
import mvc.filters.BoundaryBoxFilter;
import mvc.filters.DateTimeFilter;
import mvc.source.Source;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorList;
import sql.LocalSQL;

public class Trino 
{

	public static final String LOGIN_URL = "https://trino.opensky-network.org/";
	
	private static final String DATABASE_URL = "jdbc:trino://trino.opensky-network.org:443";

	public static String USERNAME = null;
	
	
	private static Connection getConnection() throws SQLException, SQLTimeoutException
	{
		final Properties properties = new Properties();
		properties.setProperty("user", USERNAME);
		properties.setProperty("externalAuthentication", "true");
		properties.setProperty("externalAuthenticationTokenCache", "MEMORY");

		return DriverManager.getConnection(DATABASE_URL, properties);	
	}
	
	private static void setUsername()
	{
		// Check if there is a valid user name
		if(USERNAME == null || USERNAME.isBlank())
		{
			while(USERNAME == null || USERNAME.isBlank())
			{
				USERNAME = OptionPane.showInput(
						  "<html>"
						+ "Please enter your OpenSky/Trino user name."
						+ "<p>"
						+ "<html>");
			}
		}
	}
	
	/**
	 * 
	 * @param query string
	 * @return StateVectorList if operation is successful, null if unsuccessful
	 */
	public static StateVectorList queryStateVectorData4(final String query)
	{
		setUsername();
		
		if(Application.DEBUG) System.out.println(query);
		try
		{
			// Create connection and query for data.
			Log.append("\nCreating connection to OpenSky-Network...   ");
			final Connection conn = Trino.getConnection();
			Log.append("Success.");
			
			// Execute Query
			Log.append("\nQuerying OpenSky-Network...   ");
			final ResultSet rs = conn.createStatement().executeQuery(query);		
			Log.append("Success.");
			
			// Create StateVectorList
			final StateVectorList result = new StateVectorList
					(new Source(Source.Type.OPENSKY_NETWORK, OpenSkyTable.STATE_VECTORS_DATA4.toString()));
			
			while(rs.next())
			{
				final String[] temp = new String[StateVector.SIZE];
				
				temp[ 0] = rs.getString("time");
				temp[ 1] = rs.getString("icao24");
				temp[ 2] = rs.getString("lat");
				temp[ 3] = rs.getString("lon");
				temp[ 4] = rs.getString("velocity");
				temp[ 5] = rs.getString("heading");
				temp[ 6] = rs.getString("vertrate");
				temp[ 7] = rs.getString("callsign");
				temp[ 8] = rs.getString("onground");
				temp[ 9] = rs.getString("alert");
				temp[10] = rs.getString("spi");
				temp[11] = rs.getString("squawk");
				temp[12] = rs.getString("baroaltitude");
				temp[13] = rs.getString("geoaltitude");
				temp[14] = rs.getString("lastposupdate");
				temp[15] = rs.getString("lastcontact");
				temp[16] = rs.getString("hour");
				temp[17] = "0";
				
				result.add(new StateVector(temp));
			}
			

			// Close connection
			conn.close();

			// Operation was successful
			return result;
		}
		catch(SQLException e)
		{
			// Operation was unsuccessful
			if(Application.DEBUG) e.printStackTrace();
			Log.append("Failed.");
			Log.append("\n" + e);
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
