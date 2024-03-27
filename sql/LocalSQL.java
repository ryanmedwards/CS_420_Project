package sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.Airport;
import mvc.filters.Filter;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorIO;

public class LocalSQL 
{
	private static final boolean DEBUG = true;
	
	// Connection Strings
	
	public final static String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + File.separator + "database" + File.separator + "LocalDB.db";
	
	public static String table = "test"; 
	
	public static List<StateVector> query(final String query)
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<StateVector> result = new ArrayList<>();
			
			while(rs.next())
			{
				Object[] temp = new Object[StateVector.SIZE];
				
				temp[ 0] = rs.getInt("time");
				temp[ 1] = rs.getString("icao24");
				temp[ 2] = rs.getDouble("lat");
				temp[ 3] = rs.getDouble("lon");
				temp[ 4] = rs.getDouble("velocity");
				temp[ 5] = rs.getDouble("heading");
				temp[ 6] = rs.getDouble("vertrate");
				temp[ 7] = rs.getString("callsign");
				temp[ 8] = rs.getBoolean("onground");
				temp[ 9] = rs.getBoolean("alert");
				temp[10] = rs.getBoolean("spi");
				temp[11] = rs.getString("squawk");
				temp[12] = rs.getDouble("baroaltitude");
				temp[13] = rs.getDouble("geoaltitude");
				temp[14] = rs.getDouble("lastposupdate");
				temp[15] = rs.getDouble("lastcontact");
				temp[16] = rs.getInt("hour");
				
				result.add(new StateVector(temp));
			}

			
			conn.close();

			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new ArrayList<StateVector>();
	}
	

	
	public static List<String> getUniqueICAOList()
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final String query = "select distinct icao24 from state_vectors;";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			conn.close();
			
			final List<String> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(rs.getString("icao24"));
			}
			
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public static List<String> getStateVectorTableList()
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final String query = "select name from sqlite_master where type='table' and substr(name, -1, -12) = 'state_vector'";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			final List<String> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(rs.getString("name"));
			}
			
			conn.close();

			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public static String[] getAirlinesList()
	{
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final String query = "select airline from airlines;";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<String> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(rs.getString("airline"));
			}

			
			conn.close();
			
			final String[] array = new String[result.size()];
			
			for(int i = 0; i < result.size(); ++i)
			{
				array[i] = result.get(i);
			}
			
			return array;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new String[0];
	}
	
	public static void importCSV(final String filepath)
	{
		final String cmd = ".import \'" + filepath + "\' temp_state_vectors --csv";

		try(final BufferedWriter bw = new BufferedWriter(new FileWriter("database\\import.txt")))
		{
			bw.write(cmd);
			
			bw.close();
			
			Runtime.getRuntime().exec("cmd /c start database\\importCSV.bat");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void loadTempData(final List<StateVector> data)
	{
		final String filename = "output" + File.separator + "temp" + File.separator + "tempdata.csv";
		
		StateVectorIO.writeCSV(data, filename);
		
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final String update = "delete from temp_state_vectors;";
			
			final Statement stmt = conn.createStatement();
			
			stmt.executeUpdate(update);
			
			conn.close();
			
			importCSV(new File(filename).getAbsolutePath());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public static List<Airport> getAirports()
	{
		final String query = "select short_name, lat, lon from airports;";
		
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<Airport> result = new ArrayList<>();
			
			while(rs.next())
			{
				final String name = rs.getString("short_name");
				final double lat = rs.getDouble("lat");
				final double lon = rs.getDouble("lon");
				
				result.add(new Airport(name, lat, lon));
			}
			
			conn.close();
			
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	
	
	
	
	/**
	 * 
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
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
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
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
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
			e.printStackTrace();
			
			return false;
		}
	}

	
	/**
	 * Duplicates a table in the database.
	 * <br>
	 * Performs: create table {destinationTable} as select * from {sourceTable};
	 * 
	 * @param destinationTable the new table
	 * @param sourceTable the table to be copied
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean createAs(final String destinationTable, final String sourceTable)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();

			stmt.execute(
					String.format("create table %s as select * from %s;", 
							destinationTable, 
							sourceTable));

			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	
	/**
	 * Get the next available process id.
	 * 
	 * @return the next available process_id, or -1 if database access failed
	 */
	public static int getNextProcessID()
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery("select max(process_id) from sv_tables;");

			conn.close();
			
			if(rs.next())
			{
				return rs.getInt("max(process_id)") + 1;
			}
			else
			{
				return 0;
			}
		}
		catch(SQLException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
			return -1;
		}
	}
	
	/**
	 * Executes a list of INSERT statements.
	 * 
	 * @param inserts a list of INSERT statements
	 * @return true if operation is successful, false if unsuccessful
	 */
	public static boolean insert(final List<String> inserts)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			final int batchSize = 100;
			
			int i = 0;
			
			while(i < inserts.size())
			{
				if(inserts.size() - i < batchSize)
				{
					while(i < inserts.size())
					{
						stmt.addBatch(inserts.get(i));
						++i;
					}
				}
				else
				{
					for(int j = 0; j < batchSize; ++i)
					{
						stmt.addBatch(inserts.get(i));
						++i;
					}
				}
				
				stmt.executeBatch();
				stmt.clearBatch();
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<String> getStateVectorTables()
	{
		final String query = "select name from sv_tables;";
		
		try(final Connection conn = DriverManager.getConnection(URL))
		{
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<String> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(rs.getString("name"));			
			}
			
			conn.close();
			
			return result;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	
	public static boolean logFilter(final Filter filter, final int filterID)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(URL);
			
			final Statement stmt = conn.createStatement();
			
			
			
			
			return true;
		}
		catch(SQLException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	
}















