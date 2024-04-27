package sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Application;
import application.Log;
import data.Airline;
import data.CenterPoint;
import mvc.filters.AirlineFilter;
import mvc.filters.BoundaryBoxFilter;
import mvc.filters.BoundaryPointFilter;
import mvc.filters.DateTimeFilter;
import mvc.filters.DuplicatesFilter;
import mvc.filters.ExpiredFilter;
import mvc.filters.Filter;
import mvc.filters.NullsFilter;
import mvc.source.LocalTable;
import mvc.source.Source;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorIO;
import opensky.statevector.StateVectorList;

public class LocalSQL 
{
	/**
	 * Project Directory
	 */
	private final static String DIRECTORY = System.getProperty("user.dir") + File.separator + "database";
	
	/**
	 * Path to LocalDB.db
	 */
	private final static String LOCAL_DB = DIRECTORY + File.separator + "LocalDB.db";
	
	/**
	 * Path to sqlite.exe
	 */
	private final static String SQLITE_EXE = DIRECTORY + File.separator + "sqlite3.exe";
	
	/**
	 * Database connection string
	 */
	public final static String URL = "jdbc:sqlite:" + LOCAL_DB;

	/**
	 * File path for writing temporary state vector data sets.
	 * <br><br>
	 * Used when importing a state vector data set into the database.
	 */
	private final static String TEMP_SV_PATH = DIRECTORY + File.separator + "temp_sv.csv";
	
	
	/**
	 * Returns the entire state vector data set from the given table name.
	 * <br><br>
	 * If the table does not exist, an empty StateVectorList will be returned.
	 * 
	 * @param source 
	 * @return StateVectorList
	 */
	public static StateVectorList queryStateVector(final Source source)
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery("select * from " + source.location + ";");
			
			final StateVectorList result = new StateVectorList(source);
			
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
				temp[17] = rs.getString("class");
				
				result.add(new StateVector(temp));
			}

			
			conn.close();

			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new StateVectorList(source);
	}

	/**
	 * Retrieve a list of every airline stored in LocalDB.db.
	 * 
	 * @return List, empty if SQLException occurs
	 */
	public static List<Airline> getAirlinesList()
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final String query = "select airline, icao from airlines;";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<Airline> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(new Airline
				(
					rs.getString("airline"),
					rs.getString("icao")
				));
			}
	
			conn.close();
			
			return result;
		} 
		catch (SQLException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * Inserts state vector data into the table with the given name.
	 * <br><br>
	 * This method assumes that a file exists at TEMP_SV_PATH.
	 * 
	 * @param table the name of the table that this data will be added to
	 * @return true if successful, false otherwise
	 */
	private static boolean importStateVectorCSV(final String table)
	{
		try
		{
			System.out.println(table);
			final String cmd = String.format(
					".import \'%s\' %s --csv", 
					TEMP_SV_PATH, table); 
			
			Log.append("\nExecuting import command...   ");
			final ProcessBuilder pb = new ProcessBuilder
			(
				SQLITE_EXE, LOCAL_DB, cmd
			);
	
			Log.append("\nStarting process...   ");
			final Process p = pb.start();

	        Log.append("\nWaiting for process...   ");
			final int status = p.waitFor();
			
			Log.append("\nStatus: " + status);
			
			Log.append("Success.");
			return true;

		} 
		catch (Exception e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			Log.append("Failed");
			Log.append("\nError Message: " + e.toString());
			return false;
		}
	}


	/**
	 * Retrieve a list of every center point stored in LocalDB.db
	 * 
	 * @return List, empty if SQLException occurs
	 */
	public static List<CenterPoint> getCenterPoints()
	{
		final String query = 
				  "select cp.point_id, cp.name, cp.short_name, cp.lat, cp.lon, cp.city, cp.type, "
				+ "c.state, c.country, c.timezone, "
				+ "t.utc_time_dif "
				+ "from center_points as cp "
				+ "join cities as c, timezones as t "
				+ "on cp.city = c.city and c.timezone = t.timezone;";
		
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<CenterPoint> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(new CenterPoint
				(
					rs.getInt("point_id"),
					rs.getString("name"),
					rs.getString("short_name"),
					rs.getDouble("lat"),
					rs.getDouble("lon"),
					rs.getString("city"),
					rs.getString("type"),
					rs.getString("state"),
					rs.getString("country"),
					rs.getString("timezone"),
					rs.getInt("utc_time_dif")
				));
			}
			
			
			conn.close();
			
			return result;
		} 
		catch (SQLException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	
	/**
	 * Executes the given SQL statement and returns true if successful and false if not.
	 * <br>
	 * This method is meant for INSERT, UPDATE, and DELETE statements.
	 * 
	 * @param update the SQL update statement that will be executed 
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean update(final String update)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			stmt.executeUpdate(update);
			
			conn.close();
			
			return true;
		} 
		catch (SQLException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Removes the specified table from the database.
	 * 
	 * @param table the table to be removed
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean drop(final String table)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			stmt.executeUpdate(String.format("drop table %s;", table));

			conn.close();
			
			return true;
		} 
		catch (SQLException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Performs the VACUUM operation on the database.
	 * 
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean vacuum()
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			stmt.execute("vacuum;");
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}

	
	
	/**
	 * Creates a table in the local database with the given name.
	 * <p>
	 * The table stores OpenSky-Network State Vectors.
	 * 
	 * @param table the table name
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean createStateVectorTable(final String table)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();

			stmt.execute("create table " + table + " ("
							+ "time INT,"
							+ "icao24 TEXT,"
							+ "lat REAL,"
							+ "lon REAL,"
							+ "velocity REAL,"
							+ "heading REAL,"
							+ "vertrate REAL,"
							+ "callsign TEXT,"
							+ "onground TEXT,"
							+ "alert TEXT,"
							+ "spi TEXT,"
							+ "squawk INT,"
							+ "baroaltitude REAL,"
							+ "geoaltitude REAL,"
							+ "lastposupdate REAL,"
							+ "lastcontact REAL,"
							+ "hour INT,"
							+ "class INT);");
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Counts the total entries in the table with the given name.
	 * 
	 * @param table the name of the table
	 * @return the total entries if succesfull, -1 otherwise
	 */
	public static int count(final String table)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select count(*) from %s;", table));
			
			final int result = rs.getInt("count(*)");
			
			conn.close();
			
			return result;
		}
		
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return -1;
		}
	}

	
	

	/**
	 * Get the next available process id.
	 * 
	 * @return the next available process_id, or -1 if database access failed
	 */
	private static int getSVTableID(final String name)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery("select rowid from sv_tables where name = \'" + name + "\';");

			rs.next();
			
			final int id = rs.getInt("rowid");

			conn.close();
			
			return id;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Adds the StateVectorList to LocalDB.db and logs the Filter's that
	 * have been applied to the list. The data is stored in a table with the 
	 * given name.
	 * 
	 * @param data list of state vectors
	 * @param filters list of filters that have been applied to the list
	 * @param table the name of table that the data will be stored in
	 * @return true if successful, false otherwise
	 */
	public static boolean add(final StateVectorList data, final List<Source> sources, final List<Filter> filters, final String table)
	{
		// Temporarily write to file
		Log.append("\nWriting to a temporary file...   ");
		if( ! StateVectorIO.writeCSV(data, TEMP_SV_PATH) )
		{
			Log.append("Failed.");
			return false;
		}
		Log.append("Successfully written to: " + TEMP_SV_PATH);
		
		// Import temp file to database
		Log.append("\nImporting temp file...   ");
		if( ! LocalSQL.importStateVectorCSV(table) )
		{
			Log.append("Failed.");
			return false;
		}
		Log.append("Succesfully loaded data into table: " + table);
		
		// Delete temp file
		Log.append("\nDeleting temporary file...   ");
		if( ! new File(TEMP_SV_PATH).delete() ) 
		{
			Log.append("Failed.");
			return false;
		}
		Log.append("Success.");
		
		// Log table to sv_tables		
		Log.append("\nLogging table info...   ");
		if( ! LocalSQL.update(
				String.format(
						"insert into sv_tables values (\'%s\', \'%s\');", 
						table, 
						LocalTable.Status.IN_LOCAL_DB)))
		{
			Log.append("Failed.");
			return false;
		}
		Log.append("Success.");
		
		// Get the next valid process id
		Log.append("\nGetting table id...   ");
		final int id = LocalSQL.getSVTableID(table);
		if( id == -1)
		{
			Log.append("Failed.");
			return false;
		}
		Log.append("Success.");
		
		// Log Sources
		Log.append("\nLogging source info...   ");
		for(final Source source: sources)
		{
			if( ! LocalSQL.update(
					String.format(
							"insert into sv_sources values(%d, \'%s\', \'%s\');", 
							id,
							source.type.toString(),
							source.location)))
			{
				Log.append("Failed");
				return false;
			}
		}
		Log.append("Success.");
		
		// Log each filter
		Log.append("\nLogging filter info...   ");
		int order = 0;
		for(final Filter filter: filters)
		{
			++order;

			// Log to filter table
			if( ! LocalSQL.update(filter.getLog(id, order, filter.applied)) )
			{
				Log.append("Failed to log filter to table: " + filter.getDBTable());
				return false;
			}
			Log.append("Success.");
		}
		
		
		return true;
	}
	
	




}















