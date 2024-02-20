package opensky.statevector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import sql.LocalSQL;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/*
0		time
1		icao24
2		lat
3		lon
4		velocity
5		heading
6		vertrate
7		callsign
8		onground
9		alert
10		spi
11		squawk
12		baroaltitude
13		geoaltitude
14		lastposupdate
15		lastcontact
16		hour
17		class
*/

public class Filter
{
	
	public static List<List<String>> getColumns(final List<List<String>> data, int... col)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			List<String> temp = new ArrayList<>();
			
			for(int i: col)
			{
				temp.add(entry.get(i));
			}
			
			result.add(temp);
		}
		
	
		return result;
	}
	
	public static List<List<String>> getData(final String file)
	{
		List<List<String>> result = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(file + ".csv")))
		{
			String line;
			
			line = br.readLine();
			
			while((line = br.readLine()) != null)
			{
				String[] values = line.split(",");	

				result.add(Arrays.asList(values));
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void writeToCSV(final List<List<String>> data, final String file)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file + ".csv")))
		{
			bw.write("time,icao24,lat,lon,velocity,heading,vertrate,callsign,onground,alert,spi,squawk,baroaltitude,geoaltitude,lastposupdate,lastcontact,hour,class");
			bw.newLine();
			for(List<String> entry: data)
			{
				for(int j = 0; j < entry.size(); ++j)
				{
					bw.write(entry.get(j));
					if(j != entry.size()-1)
					{
						bw.write(",");
					}
				}
				bw.newLine();
			}
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}
	}

	public static List<String> getUniqueICAO(final List<List<String>> data)
	{
		List<String> unique = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			if(!unique.contains(entry.get(1)))
			{
				unique.add(entry.get(1));
			}
		}
		
		return unique;
	}

	public static List<List<String>> changeColumn(final List<List<String>> data, final int column, final String value)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			List<String> temp = new ArrayList<>(entry);
			
			temp.set(column, value);
			
			result.add(temp);
		}
		
		return result;
	}
	
	public static List<List<String>> appendAll(final List<List<String>> data, final String value)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			List<String> temp = new ArrayList<>(entry);
			
			temp.add(value);
			
			result.add(temp);
		}
		
		return result;
	}

	
 	public static List<StateVector> removeDuplicates(final List<StateVector> data)
	{
		System.out.println("Removing Duplicates");
		
		List<String> uniqueICAO = LocalSQL.getUniqueICAOList();
		
		List<StateVector> result = new ArrayList<>();
		
		for(final String icao: uniqueICAO)
		{
			List<String> unique = new ArrayList<>();
			
			for(final StateVector entry: data)
			{
				if(entry.get(StateVector.ICAO24).equals(icao))
				{
					StringBuilder sb = new StringBuilder("");
					
					sb.append(entry.get(StateVector.LAT));
					sb.append(entry.get(StateVector.LON));
					sb.append(entry.get(StateVector.VELOCITY));
					sb.append(entry.get(StateVector.HEADING));
					sb.append(entry.get(StateVector.VERTRATE));
					sb.append(entry.get(StateVector.BAROALTITUDE));
					sb.append(entry.get(StateVector.GEOALTITUDE));
					
					if(!unique.contains(sb.toString()))
					{
						unique.add(sb.toString());
						result.add(entry);
					}
				}
			}
		}
		
		return result;
	}
	
	public static List<List<String>> removeNulls(final List<List<String>> data)
	{
		/*
		0		time
		1		icao24
		2		lat
		3		lon
		4		velocity
		5		heading
		6		vertrate
		7		callsign
		8		onground
		9		alert
		10		spi
		11		squawk
		12		baroaltitude
		13		geoaltitude
		14		lastposupdate
		15		lastcontact
		16		hour
		*/
		
		System.out.println("Removing Nulls");
		
		List<List<String>> result = new ArrayList<>();
		
//		for(final List<String> entry: data)
//		{
//			boolean hasNulls = false;
//			for(final String str: entry)
//			{
//				if(str.equals("NULL"))
//				{
//					hasNulls = true;
//					break;
//				}
//			}
//			
//			if(!hasNulls)
//			{
//				result.add(entry);
//			}
//		}
		
		final int[] targets = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 12, 13};
		
		for(final List<String> entry: data)
		{
			boolean hasNulls = false;
			
			for(int i: targets)
			{
				if(entry.get(i).equals("NULL"))
				{
					hasNulls = true;
					break;
				}
			}
			
			if(!hasNulls)
			{
				result.add(entry);
			}
		}
		
		return result;
	}
	
	public static List<List<String>> reduceByRadius(final List<List<String>> data)
	{
		System.out.println("Removing outside 50 mile radius");
		
		List<List<String>> result = new ArrayList<>();
		
		for(int i = 0; i < data.size(); ++i)
		{
			final double lat1 = Double.parseDouble(data.get(i).get(2)) * (Math.PI/180);
			final double lon1 = Double.parseDouble(data.get(i).get(3)) * (Math.PI/180);
			
			final double lat2 = 41.9802 * (Math.PI/180);
			final double lon2 = -87.904724 * (Math.PI/180);
			
			final double dist = Math.acos( Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1) ) * 6371.0;
			
			final double r = 50.0 * 1.609;
			
			if(dist <= r)
			{
				result.add(data.get(i));
			}
		}

		return result;
	}

	public static List<List<String>> removeAnomolies(final List<List<String>> data)
	{
		System.out.println("Removing Anomolies");
		
		List<List<String>> result = new ArrayList<>();
		
		final int min = 1693026000;
		final int max = 1693108800;
		
		for(Integer i = min; i <= max; i += 3600)
		{
			List<List<String>> hour = new ArrayList<>();
			
			for(final List<String> entry: data)
			{
				if(entry.get(16).equals(i.toString()))
				{
					hour.add(entry);
				}
			}
			
			final List<String> uniqueICAO = getUniqueICAO(hour);
			
			for(final String icao: uniqueICAO)
			{
				List<List<String>> flight = new ArrayList<>();
				
				for(final List<String> entry: hour)
				{
					if(entry.get(1).equals(icao))
					{
						flight.add(entry);
					}
				}
				
				result.addAll(fixPath(flight));
			}
		}
		
		return result;
	}
	
	public static List<List<String>> sortByTime(final List<List<String>> data)
	{
		System.out.println("Sorting By Time");
		
		List<List<String>> result = new ArrayList<>(data);
		
		Collections.sort(result, new Comparator<List<String>>()
		{
			@Override
			public int compare(List<String> o1, List<String> o2) 
			{		
				final String s1 = o1.get(0);
				final String s2 = o2.get(0);
				
				return s1.compareTo(s2);
			}
		});
		
		return result;
	}
	
	public static HashMap<String, Integer> getAircraftMap(final List<List<String>> data)
	{
		HashMap<String, Integer> result = new HashMap<>();

		for(List<String> entry: data)
		{
			String icao = entry.get(1);
			
			if(!result.containsKey(icao))
			{
				result.put(icao, 1);
			}
			else
			{
				result.put(icao, result.get(icao) + 1);
			}
		}
		
		return sortMap(result);
	}
	
	public static void printAircraftMap(final List<List<String>> data)
	{
		final HashMap<String, Integer> map = getAircraftMap(data);
		
		for(final String key: map.keySet())
		{
			System.out.println(key + "\t\t" + map.get(key));
		}
	}
	
	public static HashMap<String, Integer> getHourMap(final List<List<String>> data)
	{
		HashMap<String, Integer> result = new HashMap<>();

		for(List<String> entry: data)
		{
			String hour = entry.get(16);
			
			if(!result.containsKey(hour))
			{
				result.put(hour, 1);
			}
			else
			{
				result.put(hour, result.get(hour) + 1);
			}
		}
		
		return sortHourMap(result);
	}
	
	public static HashMap<String, Integer> sortHourMap(final HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() 
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
         
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	
	public static HashMap<String, Integer> sortMap(final HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() 
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	
	public static List<List<String>> getHourList(final List<List<String>> data, final String hour)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			if(entry.get(16).equals(hour))
			{
				result.add(entry);
			}
		}
		
		return result;
	}
	
	public static List<List<String>> getAirlineList(final List<List<String>> data, final String airline)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			if(entry.get(7).substring(0, 3).equals(airline))
			{
				result.add(entry);
			}
		}
		
		return result;
	}

	public static void writeFilterProcess(final List<List<String>> data, final String icao, final String hour)
	{
		List<List<String>> temp = getHourList(getByICAO(data, icao), hour);

		Filter.writeByICAO(temp, icao, "icao_" + icao + "_1");
		
		//temp = Filter.removeDuplicates(temp);
		Filter.writeByICAO(temp, icao, "icao_" + icao + "_2");
		
		temp = Filter.removeNulls(temp);
		Filter.writeByICAO(temp, icao, "icao_" + icao + "_3");
		
		temp = Filter.reduceByRadius(temp);
		Filter.writeByICAO(temp, icao, "icao_" + icao + "_4");
		
		temp = Filter.removeAnomolies(temp);	
		Filter.writeByICAO(temp, icao, "icao_" + icao + "_5");
	}
	
	public static void printStats(final List<List<String>> data)
	{
		final int min = 1693026000;
		final int max = 1693108800;	
		
		final String[] airlines = {"UAL", "DAL", "AAL", "SWA", "ASA"};
		
		final String[] time = new String[]{"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM",
				"10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM",
				"10 PM", "11 PM"};
		int timeIndex = 0;
		
		for(Integer i = min; i <= max; i += 3600)
		{

			List<List<String>> hour = getHourList(data, i.toString());
			
			HashMap<String, Integer> hourMap = getAircraftMap(hour);

			System.out.print(time[timeIndex] + "\t" + hourMap.size() + "\t" + hour.size() + "\t"); // tot aircrafts  tot messages

//			for(int j = 0; j < airlines.length; ++j)
//			{
//				List<List<String>> airline = getAirlineList(hour, airlines[j]);
//
//				HashMap<String, Integer> airlineMap = getAircraftMap(airline);
//				
//				System.out.print(time[timeIndex] + "\t" + airlineMap.size() + "\t" + airline.size() + "\t"); // tot aircrafts  tot messages
//
//			}
			++timeIndex;
			System.out.println();
		}
	}
	
	public static List<List<String>> fixPath(final List<List<String>> data)
	{		
		List<List<String>> result = new ArrayList<>();
		
		result.add(data.get(0));

		double factor = 0.06;
		

		for(int i = 0; i < data.size() - 1; ++i)
		{
			final double lat1 = Double.parseDouble(data.get(i).get(2));
			final double lat2 = Double.parseDouble(data.get(i+1).get(2));
			
			final double lon1 = Double.parseDouble(data.get(i).get(3));
			final double lon2 = Double.parseDouble(data.get(i+1).get(3));
			
			double dif = Math.max(Math.abs(lat1-lat2), Math.abs(lon1-lon2));
			
			if(dif <= factor) 
			{
				result.add(data.get(i+1));
			}
			else
			{
				//System.out.println(data.get(i+1).toString());
				dif = 0;
				while(dif <= factor && i < data.size() - 2)
				{
					++i;
					final double la1 = Double.parseDouble(data.get(i).get(2));
					final double la2 = Double.parseDouble(data.get(i+1).get(2));
					
					final double lo1 = Double.parseDouble(data.get(i).get(3));
					final double lo2 = Double.parseDouble(data.get(i+1).get(3));
					
					dif = Math.max(Math.abs(la1-la2), Math.abs(lo1-lo2));
				}
			}

		}
		
		// Print out fixed icao
		if(data.size() != result.size())
		{
			final int index = (Integer.parseInt(data.get(0).get(16)) - 1693026000)/3600;
			
			//System.out.print(index + ",");
			
			System.out.println(data.get(0).get(1) + "\t\t" + index);
		}
		
		return result;
	}
	
	public static List<List<String>> getByICAO(final List<List<String>> data, final String icao)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(final List<String> entry: data)
		{
			if(entry.get(1).equals(icao))
			{
				result.add(entry);
			}
		}
		
		return result;
	}
	
	public static List<List<String>> reduceByMessageCount(final List<List<String>> data, final int min, final int max)
	{
		List<List<String>> result = new ArrayList<>();
		
		final int minT = 1693026000;
		final int maxT = 1693108800;	
		
		for(Integer i = minT; i <= maxT; i += 3600)
		{
			final List<List<String>> hour = getHourList(data, i.toString());
			
			final HashMap<String, Integer> aircraftMap = getAircraftMap(hour);

			for(final String key: aircraftMap.keySet())
			{
				final int messageCount = aircraftMap.get(key);
				
				if(messageCount >= min && messageCount <= max)
				{
					result.addAll(getByICAO(hour, key));
				}
			}
		}

		
		return result;
	}
	
	public static void writeByICAO(final List<List<String>> data, final String icao, final String file)
	{
		List<List<String>> result = new ArrayList<>();
		
		for(List<String> entry: data)
		{
			if(entry.get(1).equals(icao))
			{
				result.add(entry);
			}
		}

		writeToCSV(result, file);
	}
	
	public static void writeAllAircrafts(final List<List<String>> data, final String file)
	{
		final List<String> icao = getUniqueICAO(data);
		
		for(final String i: icao)
		{
			writeToCSV(getByICAO(data, i), file + "_icao_" + i);
		}
	}
	
 	public static void convertLogToCSV(final String in, final String out)
	{
		List<String> result = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(in + ".log")))
		{
			String line;
			
			while( (line = br.readLine()) != null )
			{
				if(line.substring(0, 3).equals("| 1"))
				{
					line = line.replaceAll("\s+", "");
					line = line.replace('|', ',');
					line = line.substring(1, line.length() - 1);
					result.add(line);
				}
			}
		}
		catch(IOException e) { e.printStackTrace(); }
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(out + ".csv")))
		{
			for(final String entry: result)
			{
				bw.write(entry);
				bw.newLine();
			}
		}
		catch(IOException e) { e.printStackTrace(); }

	}

 	public static void writeByClass(final List<List<String>> data, final String type, final String file)
 	{
 		List<List<String>> result = new ArrayList<>();
 		
 		for(final List<String> entry: data)
 		{
 			if(entry.get(17).equals(type))
 			{
 				result.add(entry);
 			}
 		}
 		
 		writeToCSV(result, file);
 	}
}





















