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
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorIO;

public class LocalSQL 
{
	
	// Connection Strings
	
	public final static String databaseURL = "jdbc:sqlite:" + System.getProperty("user.dir") + File.separator + "database" + File.separator + "LocalDB.db";
	
	public static String table = "test"; 
	
	public static List<StateVector> query(final String query)
	{
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
		
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
		
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
	
	

	
	public static List<String> getStateVectorTables()
	{
		final String query = "select name from sv_tables;";
		
		try(final Connection conn = DriverManager.getConnection(databaseURL))
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
	
	
}















