package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Application;
import data.Airline;
import data.Unit;
import mvc.filters.BoundaryPointFilter;
import mvc.filters.BoundaryPointFilter.Shape;
import mvc.filters.DateTimeFilter;
import mvc.filters.Filter;
import mvc.filters.FilterHistory;
import mvc.filters.FilterQuery;
import mvc.source.LocalTable;
import mvc.source.Source;

public class LocalSVTableLoader 
{
	public static final List<LocalTable> LOCAL_TABLES = load();
	
	
	public static void loadTable(final String name)
	{
		
	}
	
	private static List<LocalTable> load()
	{
		final Map<Integer, LocalTable> map; if((map = loadSVTables()) == null) return new ArrayList<>();
		
		if( ! loadSources(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load sources");
			return new ArrayList<>();
		}
		
		if( ! loadDateTimeFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load date time filters");
			return new ArrayList<>();
		}
		
		if( ! loadBoundaryBoxFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load boundary box filters");
			return new ArrayList<>();
		}
		
		if( ! loadBoundaryPointFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to boundary point filters");
			return new ArrayList<>();
		}
		
		if( ! loadNullsFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load nulls filters");
			return new ArrayList<>();
		}
		
		if( ! loadDuplicatesFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load duplicates filters");
			return new ArrayList<>();
		}
		
		if( ! loadExpiredFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load expired filters");
			return new ArrayList<>();
		}
		
		if( ! loadAirlineFilters(map) ) 
		{
			if(Application.DEBUG) System.err.println("LocalSVTableLoader -> failed to load airline filters");
			return new ArrayList<>();
		}
		
		final List<LocalTable> result = new ArrayList<>();
		
		for(final Integer key: map.keySet())
		{
			result.add(map.get(key));
		}
		
		return result;
	}

	
	private static Map<Integer, LocalTable> loadSVTables()
	{
		final Map<Integer, LocalTable> tableMap = new HashMap<>();

		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery("select rowid, name, status from sv_tables;");
			
			while(rs.next())
			{
				tableMap.put(
						rs.getInt("rowid"), 
						new LocalTable(
								rs.getInt("rowid"),
								rs.getString("name"), 
								LocalTable.Status.valueOf(rs.getString("status"))));
			}
			
			conn.close();

			return tableMap;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
		}
		
		return null;
	}

	/**
	 *  
	 * @return
	 */
	private static boolean loadSources(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery("select id, source, location from sv_sources;");
			
			while(rs.next())
			{
				final int id = rs.getInt("id");	
				final Source source = new Source(
						Source.Type.getEnum(rs.getString("source")), 
						rs.getString("location"));
				
				if( ! map.keySet().contains(id) )
				{
					if(Application.DEBUG) System.err.println("Invalid id in LocalSVTableLoader.loadSources");
					return false;
				}
				
				map.get(id).sources.add(source);
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadDateTimeFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, start, duration from %s;", 
							Filter.DBTable.DATE_TIME));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final int start = rs.getInt("start");
				final int duration = rs.getInt("duration");
				
				switch(applied)
				{
				case JAVA:
					if( ! map.get(id).addHistory(order, FilterHistory.getDateTimeHistory(start, duration)) ) return false;
					break;
				case QUERY:
					if( ! map.get(id).addQuery(order, FilterQuery.getDateTimeQuery(start, duration)) ) return false;
					break;
				default:
					return false;
				}
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadBoundaryBoxFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, north, south, east, west from %s;", 
							Filter.DBTable.BOUNDARY_BOX));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final double n = rs.getDouble("north");
				final double s = rs.getDouble("south");
				final double e = rs.getDouble("east");
				final double w = rs.getDouble("west");
				
				switch(applied)
				{
				case JAVA:
					map.get(id).addHistory(order, FilterHistory.getBoundaryBoxHistory(n, s, e, w));
					break;
				case QUERY:
					map.get(id).addQuery(order, FilterQuery.getBoundaryBoxQuery(n, s, e, w));
					break;
				default:
					return false;
				}
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadBoundaryPointFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, distance, unit, shape, name, lat, lon from %s as ft join center_points as ct on ft.point_id = ct.point_id;", 
							Filter.DBTable.BOUNDARY_POINT));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final double distance = rs.getDouble("distance");
				final Unit unit = Unit.getEnum(rs.getString("unit"));
				final Shape shape = BoundaryPointFilter.Shape.getEnum(rs.getString("shape"));
				final String name = rs.getString("name");
				final double lat = rs.getDouble("lat");
				final double lon = rs.getDouble("lon");
				
				switch(applied)
				{
				
				case JAVA: map.get(id).addHistory(order, FilterHistory.getBoundaryPointHistory(name, distance, unit, shape)); break;
	
				case QUERY:
					
					switch(shape)
					{
					case BOX: 
						final double[] box = BoundaryPointFilter.toBounds(lat, lon, distance);
						map.get(id).addQuery(order, FilterQuery.getBoundaryBoxQuery(box[0], box[1], box[2], box[3]));
						break;
					
					case CIRCLE: map.get(id).addQuery(order, FilterQuery.getBoundaryCircleQuery(lat, lon, distance)); break;
					
					default: return false;
					}
					
					break;
	
				default: return false;
				}	
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadNullsFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, selected from %s;", 
							Filter.DBTable.NULLS));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final int selected = rs.getInt("selected");
				
				switch(applied)
				{
				case JAVA: map.get(id).addHistory(order, FilterHistory.getNullsHistory(selected)); break;
					
				case QUERY: map.get(id).addQuery(order, FilterQuery.getNullsQuery(selected)); break;
					
				default: return false;
				}
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadDuplicatesFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, selected from %s;", 
							Filter.DBTable.DUPLICATES));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final int selected = rs.getInt("selected");
				
				switch(applied)
				{
				case JAVA: map.get(id).addHistory(order, FilterHistory.getDuplicatesHistory(selected)); break;
					
				case QUERY: map.get(id).addQuery(order, FilterQuery.getDuplicatesQuery(selected)); break;
					
				default: return false;
				}
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadExpiredFilters(final Map<Integer, LocalTable> map)
	{
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, lastcontact_delay, lastposupdate_delay from %s;", 
							Filter.DBTable.EXPIRED));
				
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final int lcd = rs.getInt("lastcontact_delay");
				final int lpd = rs.getInt("lastposupdate_delay");
				
				switch(applied)
				{
				case JAVA: map.get(id).addHistory(order, FilterHistory.getExpiredHistory(lcd, lpd)); break;
				
				case QUERY:
					
					final StringBuilder sb = new StringBuilder();
					
					if(lcd > 0 && lpd > 0) 
					{
						map.get(id).addQuery(order,
							FilterQuery.getExpiredContactQuery(lcd) 
							+ " and " 
							+ FilterQuery.getExpiredPositionQuery(lpd));
					}
					else if(lcd > 0)
					{
						map.get(id).addQuery(order,
								FilterQuery.getExpiredContactQuery(lcd));
					}
					else if(lpd > 0)
					{
						map.get(id).addQuery(order,
								FilterQuery.getExpiredPositionQuery(lpd));
					}
					
					break;
					
				default: return false;
				}
			}
			
			conn.close();
			
			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadAirlineFilters(final Map<Integer, LocalTable> map)
	{
		class AirlineList
		{
			final int order;
			final Filter.Applied applied;
			
			final List<Airline> airlines = new ArrayList<>();
			
			AirlineList(final int order, final Filter.Applied applied)			
			{
				this.order = order;
				this.applied = applied;
			}
		}
		
		try
		{
			final Connection conn = DriverManager.getConnection(LocalSQL.URL);
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(
					String.format("select id, f_ord, f_applied, a.icao, a.airline from %s as t join airlines as a on t.icao == a.icao;", 
							Filter.DBTable.AIRLINE));
				
			final Map<Integer, AirlineList> airlineMap = new HashMap<>();
			
			while(rs.next())
			{
				final int id = rs.getInt("id");
				final int order = rs.getInt("f_ord");
				final Filter.Applied applied = Filter.Applied.valueOf(rs.getString("f_applied"));
				final String icao = rs.getString("icao");
				final String name = rs.getString("airline");
				
				if( ! airlineMap.containsKey(id) )
				{
					airlineMap.put(id, new AirlineList(order, applied));
				}
				
				airlineMap.get(id).airlines.add(new Airline(name, icao));
			}
			
			conn.close();
			
			for(final Integer key: airlineMap.keySet())
			{
				final AirlineList list = airlineMap.get(key);
				
				switch(list.applied)
				{
				case JAVA: map.get(key).addHistory(list.order, FilterHistory.getAirlinesHistory(list.airlines)); break;
					
				case QUERY: map.get(key).addQuery(list.order, FilterQuery.getAirlinesQuery(list.airlines)); break;
					
				default: return false;
				}
			}

			return true;
		}
		catch(SQLException e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	
	
	
}









