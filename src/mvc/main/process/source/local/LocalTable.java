package mvc.main.process.source.local;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sql.LocalSQL;

public class LocalTable 
{
	
	public static final String DUPLICATES_TABLE       = "duplicates_filter";
	public static final String NULLS_TABLE            = "nulls_filter";
	public static final String BOUNDARY_BOX_TABLE     = "boundary_box_filter";
	public static final String BOUNDARY_AIRPORT_TABLE = "boundary_airport_filter";
	public static final String DATE_TIME_TABLE        = "date_time_filter";
	

	
	
	protected final String name;
	protected final String source;
	protected final String table;
	protected final int    process_id;
	protected final int    count;		// Fix query
	
	protected final StringBuilder history;
	
	protected String getHistory()
	{
		return history.toString();
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	protected LocalTable(final String name, final String source, final String table, final int process_id, final int count)
	{
		this.name = name;
		this.source = source;
		this.table = table;
		this.process_id = process_id;
		this.count = count;
		this.history = new StringBuilder();
		
		writeHistory();
	}
	
	private void writeHistory()
	{

		final String query = String.format("select f.filter_id, filter_table_name, filter_order "
										 + "from process as p, filter as f "
										 + "where p.process_id = %d "
										 + "and p.filter_id = f.filter_id "
										 + "order by p.filter_order;"
										 ,  process_id);
		

		final List<Filter> filters = new ArrayList<>();
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next())
			{
				filters.add(new Filter
				(
					rs.getInt("filter_id"),
					rs.getString("filter_table_name"),
					rs.getInt("filter_order")
				));
			}
			
			conn.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}

		
		for(final Filter filter: filters)
		{
			switch(filter.filter_table_name)
			{
			case DUPLICATES_TABLE: 
				
				history.append(String.format("%d. Removed Duplicates based on:\n", filter.filter_order));
				
				writeDuplicates(filter); 
				
				history.append("\n");
				
				break;		
			
			case NULLS_TABLE: 
				
				history.append(String.format("%d. Removed Nulls from the following columns:\n", filter.filter_order));
				
				writeNulls(filter); 
				
				history.append("\n");
				
				break;
			
			case BOUNDARY_BOX_TABLE: 
				
				history.append(String.format("%d. Boundary Box limited to:\n", filter.filter_order));
				
				writeBoundaryBox(filter); 
				
				history.append("\n");
				
				break;
			
			case BOUNDARY_AIRPORT_TABLE: 
				writeBoundaryAirport(filter); 
				
				history.append("\n");
				
				break;
			
			case DATE_TIME_TABLE:
				
				history.append(String.format("%d. Date and Time limited to:\n", filter.filter_order));
				
				writeDateTime(filter); 
				
				history.append("\n");
				
				break;
				
			default:
			}
		}
		
		
		
		
	}
	
	private void writeDuplicates(final Filter filter)
	{
		final String query = String.format("select * from %s where filter_id = %d;", DUPLICATES_TABLE, filter.filter_id);
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
			{
				if(rs.getBoolean("time")) 			history.append("time\n");
				if(rs.getBoolean("icao24")) 		history.append("icao24\n");
				if(rs.getBoolean("lat")) 			history.append("lat\n");
				if(rs.getBoolean("lon")) 			history.append("lon\n");
				if(rs.getBoolean("velocity")) 		history.append("velocity\n");
				if(rs.getBoolean("heading")) 		history.append("heading\n");
				if(rs.getBoolean("vertrate")) 		history.append("vertrate\n");
				if(rs.getBoolean("callsign")) 		history.append("callsign\n");
				if(rs.getBoolean("onground")) 	   	history.append("onground\n");
				if(rs.getBoolean("alert")) 		   	history.append("alert\n");
				if(rs.getBoolean("spi")) 		   	history.append("spi\n");
				if(rs.getBoolean("squawk"))        	history.append("squawk\n");
				if(rs.getBoolean("baroaltitude"))  	history.append("baroaltitude\n");
				if(rs.getBoolean("geoaltitude"))   	history.append("geoaltitude\n");
				if(rs.getBoolean("lastposupdate"))  history.append("lastposupdate\n");
				if(rs.getBoolean("lastcontact"))    history.append("lastcontact\n");
				if(rs.getBoolean("hour"))          	history.append("hour\n");		
			}
			
			conn.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	private void writeNulls(final Filter filter)
	{
		final String query = String.format("select * from %s where filter_id = %d;", NULLS_TABLE, filter.filter_id);
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
			{
				if(rs.getBoolean("time")) 			history.append("time\n");
				if(rs.getBoolean("icao24")) 		history.append("icao24\n");
				if(rs.getBoolean("lat")) 			history.append("lat\n");
				if(rs.getBoolean("lon")) 			history.append("lon\n");
				if(rs.getBoolean("velocity")) 		history.append("velocity\n");
				if(rs.getBoolean("heading")) 		history.append("heading\n");
				if(rs.getBoolean("vertrate")) 		history.append("vertrate\n");
				if(rs.getBoolean("callsign")) 		history.append("callsign\n");
				if(rs.getBoolean("onground")) 	   	history.append("onground\n");
				if(rs.getBoolean("alert")) 		   	history.append("alert\n");
				if(rs.getBoolean("spi")) 		   	history.append("spi\n");
				if(rs.getBoolean("squawk"))        	history.append("squawk\n");
				if(rs.getBoolean("baroaltitude"))  	history.append("baroaltitude\n");
				if(rs.getBoolean("geoaltitude"))   	history.append("geoaltitude\n");
				if(rs.getBoolean("lastposupdate"))  history.append("lastposupdate\n");
				if(rs.getBoolean("lastcontact"))    history.append("lastcontact\n");
				if(rs.getBoolean("hour"))          	history.append("hour\n");		
			}
			
			conn.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	private void writeBoundaryBox(final Filter filter)
	{
		final String query = String.format("select * from %s where filter_id = %d;", BOUNDARY_BOX_TABLE, filter.filter_id);
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
			{
				history.append(String.format("\tNorth: %s\n", rs.getDouble("north")));
				history.append(String.format("\tSouth: %s\n", rs.getDouble("south")));
				history.append(String.format("\tEast: %s\n", rs.getDouble("east")));
				history.append(String.format("\tWest: %s\n", rs.getDouble("west")));
			}
			
			conn.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	private void writeBoundaryAirport(final Filter filter)
	{

	}
	
	private void writeDateTime(final Filter filter)
	{
		final String query = String.format("select "
				+ "datetime(start, \'unixepoch\') as s, "
				+ "datetime(start+duration, \'unixepoch\') as e "
				+ "from %s where filter_id = %d;", DATE_TIME_TABLE, filter.filter_id);
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next())
			{
				history.append(String.format("\tStart: %s\n", rs.getString("s")));
				history.append(String.format("\tStop: %s\n", rs.getString("e")));
			}
			
			conn.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	protected static List<LocalTable> getLocalTables()
	{
		
		final String query = "select name, source_name, source_table, process_id, count(*) from sv_tables;";
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.databaseURL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<LocalTable> result = new ArrayList<>();
			
			while(rs.next())
			{
				result.add(new LocalTable
				(
						rs.getString("name"),
						rs.getString("source_name"),
						rs.getString("source_table"),
						rs.getInt("process_id"),
						rs.getInt("count(*)")
				));			
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
	
	
	private class Filter
	{
		private final int    filter_id;
		private final String filter_table_name;
		private final int    filter_order;
		
		private Filter(final int filter_id, final String filter_table_name, final int filter_order)
		{
			this.filter_id = filter_id;
			this.filter_table_name = filter_table_name;
			this.filter_order = filter_order;
		}
	}
	
	
}
