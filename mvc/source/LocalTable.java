package mvc.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import application.Application;
import mvc.filters.Filter;
import sql.LocalSQL;

public class LocalTable 
{
	
	public enum Status
	{
		IN_LOCAL_DB, 	// Data exists in the local database
		IN_FILE,	 	// Data exists in a file
		DELETED,	 	// Data has been deleted
		FILE_NOT_FOUND	// File is no longer in the known location
	}
	
	
	public final int id;
	public final String name;
	public Status status; 
	
	public final List<Source> sources = new ArrayList<>();
	
	private final Map<Integer, String> historyMap = new HashMap<>();
	private final Map<Integer, String> queryMap = new HashMap<>();
	private String query = null;
	
	private String history = null;
	
	public LocalTable(final int id, final String name, final LocalTable.Status status)
	{
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	
	public boolean addHistory(final int order, final String history)
	{
		if( this.historyMap.containsKey(order) )
		{
			return false;
		}
		
		this.historyMap.put(order, history);
		
		return true;
	}
	
	public boolean addQuery(final int order, final String condition)
	{
		if( this.queryMap.containsKey(order) )
		{
			return false;
		}
		
		this.queryMap.put(order, condition);
		
		return true;
	}
	
	public List<Source> getSources()
	{
		return sources;
	}
	
	public String getHistory()
	{
		if( history != null )
		{
			return history;
		}
		
		final List<String> list = sortMap(this.historyMap);
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append("The following filters have been applied locally:\n");
		
		int order = 0;
		for(final String str: list)
		{
			++order;
			sb.append(order + ". " + str + "\n");
		}
		
		history = sb.toString();
		
		this.historyMap.clear();
		
		return history;
	}
	
	public String getQuery()
	{
		if( query != null )
		{
			return query;
		}
		
		if( this.queryMap.isEmpty() )
		{
			this.query = "This table did not have a query.";
			return query;
		}
		
		final List<String> list = sortMap(this.queryMap);
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append("select "
				+ "time,icao24,lat,lon,velocity,"
				+ "heading,vertrate,callsign,onground,"
				+ "alert,spi,squawk,baroaltitude,geoaltitude,"
				+ "lastposupdate,lastcontact,hour "
				+ "from " + name + " \r\n");

		if(list.get(0).substring(0, 10).equals(" group by "))
		{
			sb.append(list.get(0));
		}
		else
		{
			sb.append(" where\n " + list.get(0));
		}

		for(int i = 1; i < list.size(); ++i)
		{
			if( ! list.get(i).substring(0, 10).equals(" group by ") )
			{
				sb.append("\nand ");
			}
			sb.append(list.get(i));
		}
		
		sb.append(";");
		
		this.query = sb.toString();
		
		return query;
	}
	
	private List<String> sortMap(final Map<Integer, String> map)
	{
		// Straight from stack overflow
		
		final List<String> result = new ArrayList<>();
		
        List<Map.Entry<Integer, String> > list = new LinkedList<Map.Entry<Integer, String> >(map.entrySet());
        
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() 
        {
            public int compare(Map.Entry<Integer, String> o1,
                               Map.Entry<Integer, String> o2)
            {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
         
        HashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
        for (Map.Entry<Integer, String> aa : list) 
        {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        for(final Integer key: temp.keySet())
        {
        	result.add(temp.get(key));
        }
        
        return result;
	}
	
	
	
	@Override
	public String toString()
	{
		return name;
	}
	
	
	

}


