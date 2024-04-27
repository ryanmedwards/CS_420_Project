package opensky.statevector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Application;
import guiutil.OptionPane;
import mvc.source.Source;
import sql.LocalSQL;

public class StateVectorIO 
{
	final static String HEADER_OLD = "time,icao24,lat,lon,velocity,heading,vertrate,callsign,onground,alert,spi,squawk,baroaltitude,geoaltitude,lastposupdate,lastcontact,hour";
	final static String HEADER = "time,icao24,lat,lon,velocity,heading,vertrate,callsign,onground,alert,spi,squawk,baroaltitude,geoaltitude,lastposupdate,lastcontact,hour,class";
	
	private static StateVectorList loadCSV(final Source source) 
	{
		final StateVectorList result = new StateVectorList(source);
		
		try(BufferedReader br = new BufferedReader(new FileReader(source.location)))
		{	
			String line = br.readLine();
			
			if( line.equals(HEADER) || line.equals(HEADER_OLD) )
			{
				while((line = br.readLine()) != null)
				{
					result.add(new StateVector(line.split(",")));
				}
			}
			else
			{
				br.close();
				return null;
			}
			
			br.close();
		} 
		catch (IOException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	/**
	 * Loads a StateVectorList from the given source
	 * 
	 * @param source
	 * @return StateVectorList if successfull, null if not
	 */
	public static StateVectorList loadSource(final Source source)
	{
		try
		{
			switch(source.type)
			{
			case IMPORT_FILE:		
				return StateVectorIO.loadCSV(source);

	
			case LOCAL_DATABASE:
				return LocalSQL.queryStateVector(source);
				
			case OPENSKY_NETWORK:
				return new StateVectorList();
			}
			
			return null;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
	}
	
	public static boolean writeCSV(final StateVectorList data, final String filename)
	{
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
		{
			bw.write(HEADER);
			bw.newLine();
			for(final StateVector entry: data)
			{
				bw.write(entry.toString());
				bw.newLine();
			}
			
			return true;
		} 
		catch (IOException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	public static boolean writeCSV(final StateVectorList data, final String filename, final int[] columns)
	{
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
		{
			bw.write(HEADER);
			bw.newLine();
			for(final StateVector entry: data)
			{
				final String[] array = entry.toArray();
				
				final StringBuilder sb = new StringBuilder();
				
				for(int i = 0; i < columns.length - 1; ++i)
				{
					sb.append(array[columns[i]] + ",");
				}
				sb.append(array[columns[columns.length - 1]]);
				
				System.out.println(sb.toString());
				
				bw.write(sb.toString());
				
				bw.newLine();
			}
			
			return true;
		} 
		catch (IOException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
}
















