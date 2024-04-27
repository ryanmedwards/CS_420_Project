package opensky.statevector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.Application;
import data.Airline;
import data.Flight;
import sql.LocalSQL;

public class StateVectorOP 
{
	
	/**
	 * Retrieve a list of unique ICAO24 addresses from input data set.
	 * 
	 * @param data list of StateVectors
	 * @return a list of unique ICAO24 addresses
	 */
	public static Set<String> getUniqueICAO(final StateVectorList data)
	{
		final Set<String> result = new HashSet<String>();
		
		for(final StateVector sv: data)
		{
			result.add(sv.icao24());
		}
		
		return result;
	}
	
	public static void sortByTimeAscending(final StateVectorList data)
	{
		Collections.sort(data, new Comparator<StateVector>()
		{
			@Override
			public int compare(StateVector o1, StateVector o2) 
			{
				final int t1 = o1.time();
				final int t2 = o2.time();
				
				if(t1 <  t2) return -1;
				if(t1 == t2) return 0;
				
				return 1;
			}
		});
	}
	
	/**
	 * Retrieve a list of unique flights from data set.
	 * 
	 * 
	 * @param data
	 * @return
	 */
	public static List<Flight> getFlights(final StateVectorList data)
	{

		final Map<String, Integer> minTimeMap = new HashMap<>();
		final Map<String, Integer> maxTimeMap = new HashMap<>();
		
		final Map<String, Integer> messageCountMap = new HashMap<>();
		
		StateVectorOP.sortByTimeAscending(data);
		
		for(final StateVector sv: data)
		{
			final String key = sv.getFlightKey();
			
			if( minTimeMap.containsKey(key) )
			{
				maxTimeMap.put(key, sv.time());
				messageCountMap.put(key, messageCountMap.get(key) + 1);
			}
			else
			{
				minTimeMap.put(key, sv.time());
				maxTimeMap.put(key, sv.time());
				messageCountMap.put(key, 1);
			}		
		}

		final List<Flight> result = new ArrayList<>();
		
		for(final String key: minTimeMap.keySet())
		{
			final String[] split = key.split(",");
			
			final String icao     = split[0];
			final String callsign = split[1];
			
			final MessageClassification type = MessageClassification.getEnum(Integer.parseInt(split[2]));
			
			final int count = messageCountMap.get(key);
			
			final int start = minTimeMap.get(key);
			final int stop  = maxTimeMap.get(key);
			
			result.add(new Flight(data.source, icao, callsign, start, stop, count, type));
		}
		
		return result;
	}
	
	/**
	 * Retrieve the number of entries with the given ICAO24 address
	 * 
	 * @param data list of StateVectors
	 * @param icao24 target ICAO24 address
	 * @return number of messages with the given address
	 */
	public static int getMessageCount(final StateVectorList data, final String icao24, final String callsign)
	{
		int count = 0;
		
		for(final StateVector sv: data)
		{
			if(sv.icao24().equals(icao24) && sv.callsign().equals(callsign))
			{
				++count;
			}
		}
		
		return count;
	}
	
	/**
	 * Retrieve a set of unique class identifiers.
	 * 
	 * @return
	 */
	public static Set<MessageClassification> getUniqueClassifications(final StateVectorList data) 
	{
		final Set<MessageClassification> result = new HashSet<>();
		
		for(final StateVector sv: data)
		{
			result.add(
					MessageClassification.getEnum(
							sv.classification()));
		}
		
		return result;
	}
	
	
	public static StateVectorList flightToStateVector(final Flight flight)
	{
		final StateVectorList sourceList = StateVectorIO.loadSource(flight.source);
		
		final String key = flight.getKey();
		
		final StateVectorList result = new StateVectorList(flight.source);
		
		for(final StateVector sv: sourceList)
		{
			if(sv.getFlightKey().equals(key))
			{
				result.add(sv);
			}
		}
		
		return result;
	}
	
	public static Object[][] toMatrix(final StateVectorList data)
	{
		final Object[][] matrix = new Object[data.size()][StateVector.SIZE];
		
		for(int i = 0; i < data.size(); ++i)
		{
			matrix[i] = data.get(i).toArray();
		}
		
		return matrix;
	}
	
	public static Object[][] toTransposedMatrix(final StateVectorList data)
	{
		final Object[][] matrix = toMatrix(data);
		
		final int m = matrix.length;
		final int n = matrix[0].length;
		
		final Object[][] transpose = new Object[n][m];
		
	
	    for(int i = 0; i < n; ++i) 
	    {
	        for(int j = 0; j < m; ++j) 
	        {
	            transpose[i][j] = matrix[j][i];
	        }
	    }
	    
	    return transpose;
	}
	

	
	// Filters
	
	public static String errorMessage = "";
	
	/**
	 * 
	 * 
	 * @param data
	 * @param columns
	 */
	public static boolean filterDuplicates(StateVectorList data, final int[] columns)
	{
		try
		{
			final Set<String> set = new HashSet<>();
			final StringBuilder sb = new StringBuilder();
			final Iterator<StateVector> it = data.iterator();	
			while(it.hasNext())
			{
				final String[] sv = it.next().toArray();
				
				sb.delete(0, sb.length());			
				
				for(int i = 0; i < columns.length; ++i)
				{
					sb.append(sv[columns[i]]+",");
				}
	
				if( ! set.add(sb.toString()) )
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	
	public static boolean filterNulls(StateVectorList data)
	{
		return filterNulls(data, new int[]
		{
			StateVector.TIME,
			StateVector.ICAO24,
			StateVector.LAT,
			StateVector.LON,
			StateVector.VELOCITY,
			StateVector.HEADING,
			StateVector.VERTRATE,
			StateVector.CALLSIGN,
			StateVector.ONGROUND,
			StateVector.ALERT,
			StateVector.SPI,
			StateVector.SQUAWK,
			StateVector.BAROALTITUDE,
			StateVector.GEOALTITUDE,
			StateVector.LASTPOSUPDATE,
			StateVector.LASTCONTACT,
			StateVector.HOUR,
			StateVector.CLASSIFICATION
		});
	}
	
	public static boolean filterNulls(StateVectorList data, final int[] columns)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final String[] sv = it.next().toArray();
				
				for(int i = 0; i < columns.length; ++i)
				{
					if(sv[columns[i]] == null ||
					   sv[columns[i]].equals("NULL"))
					{
						it.remove();
						break;
					}
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param data
	 * @param box WGS84 coordinate limits in order of N S E W
	 */
	public static boolean filterBoundaryBox(StateVectorList data, final double[] box)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final StateVector sv = it.next();
				final double lat = sv.lat();
				final double lon = sv.lon();
				
				if(lat > box[0] || lat < box[1] || lon > box[2] || lon < box[3])
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	

	public static boolean filterBoundaryRadius(StateVectorList data, final double clat, final double clon, final double radius)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			final double c = Math.PI / 180.0;
			final double r = 6371.0;
			
			final double lat2 = clat * c;
			final double lon2 = clon * c;
			
			
			while(it.hasNext())
			{
				final StateVector sv = it.next();
				
				final double lat1 = sv.lat() * c;
				final double lon1 = sv.lon() * c;
				
				final double dist = Math.acos( Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1) ) * r;
				
				if(dist > radius)
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}

	
	/**
	 * 
	 * @param data
	 * @param start
	 * @param stop
	 */
	public static boolean filterDateTime(StateVectorList data, final long start, final long stop)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final int time = it.next().time();
				
				if(time < start || time >= stop)
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	
	public static boolean filterExpired(StateVectorList data, final double interval)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final StateVector sv = it.next();
				
				final int time = sv.time();
				final double lastcontact = sv.lastcontact();
				
				if(time - lastcontact > interval)
				{
					it.remove();
				}
			}
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	
	public static boolean filterPosExpired(StateVectorList data, final double interval)
	{
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final StateVector sv = it.next();
				
				final int time = sv.time();
				final double lastposupdate = sv.lastposupdate();
				
				if(time - lastposupdate > interval)
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}
	
	public static boolean filterAirlines(StateVectorList data, final List<Airline> airlines)
	{
		final List<String> codes = new ArrayList<>(airlines.size());
		
		for(final Airline a: airlines)
		{
			codes.add(a.code);
		}
		
		try
		{
			final Iterator<StateVector> it = data.iterator();
			
			while(it.hasNext())
			{
				final String code = it.next().callsign().substring(0, 3);
				
				if( ! codes.contains(code) )
				{
					it.remove();
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}
	}

	
	
	public static StateVectorList getRandomList(final StateVectorList data, final int count) 
	{
		try
		{
			final List<Integer> list = new ArrayList<>(data.size());
			
			for(int i = 0; i < data.size(); ++i)
			{
				list.add(i);
			}
			
			Collections.shuffle(list);
			
			final StateVectorList result = new StateVectorList(data.source);
			
			for(int i = 0; i < count; ++i)
			{
				final int index = list.get(i);
				
				result.add(data.get(index));
			}
			
			return result;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
	}


}
































