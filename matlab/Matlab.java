package matlab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

import application.Application;
import data.Flight;
import guiutil.OptionPane;
import mvc.source.Source;

public class Matlab 
{
	private static MatlabEngine engine = null;
	
	
	private static final String LOCAL_DB = "database\\LocalDB.db";
	
	
	private static int next = 1;
	
	protected static final Map<String, Flight> FLIGHTS = new HashMap<>();
	
	protected static final Map<String, String> TABLES = new HashMap<>();
	
	
	public static boolean addFlight(final Flight flight)
	{
		// Should only be able to add flights after source is loaded
		if( ! TABLES.containsKey(flight.source.toString()) )
		{
			// Maybe ask if user wants to load source in future
			return false;
		}
		
		if( ! FLIGHTS.containsKey(flight.getKey()) )
		{
			FLIGHTS.put(flight.getKey(), flight);
		}
		
		return true;
	}

	
	public static boolean open()
	{
		if(engine != null)
		{
			return true;
		}
		
		try
		{
			engine = MatlabEngine.startMatlab();
			return true;
		} 
		catch (EngineException | IllegalArgumentException | IllegalStateException | InterruptedException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean close()
	{
		if(engine != null)
		{
			try 
			{
				engine.close();
				return true;
			} 
			catch (EngineException e) 
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static boolean loadCSV(final String source, final String filepath)
	{
		try 
		{
			final String var = String.format("table_%d", next);
			
			engine.eval(String.format("opts = detectImportOptions(\'%s\');", filepath));
			engine.eval("opts.VariableNamesLine = 1;");
			engine.eval("opts.Delimiter = \',\';");
			engine.eval("opts.VariableNamingRule = \'preserve\';");
			engine.eval(String.format("%s = readtable(\'%s\', opts, \'ReadVariableNames\', true);", var, filepath));	
			engine.eval("clear opts;");
			//smoothTable(var);
			
			TABLES.put(source, var);
			++next;
			
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			OptionPane.showError("Failed to load data from: " + filepath);
			return false;
		}
	}
	
	public static boolean loadLocalTable(final String source, final String table)
	{
		try 
		{
			final String var = String.format("table_%d", next);
			
			engine.eval(String.format("conn = sqlite(\'%s\');", LOCAL_DB));
			engine.eval(String.format("%s = sqlread(conn, \'%s\');", var, table));
			engine.eval("close(conn);");
			engine.eval("clear conn;");
			
			engine.eval(String.format("%s = convertvars(%s,   1,\'double\');", var, var));
			engine.eval(String.format("%s = convertvars(%s, 3:7,\'double\');", var, var));
			engine.eval(String.format("%s = convertvars(%s, 13:16,\'double\');", var, var));
			engine.eval(String.format("%s = convertvars(%s, 17:18,\'double\');", var, var));
			//smoothTable(var);
			TABLES.put(source, var);
			++next;			
		
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			OptionPane.showError("Failed to load data from: " + table);
			return false;
		}
	}
	
	protected static void smoothTable(final String table) throws MatlabExecutionException, MatlabSyntaxException, CancellationException, EngineException, InterruptedException, ExecutionException
	{

		engine.eval("newSize = 0;");
		engine.eval(String.format("icaos = unique(%s.icao24);", table));
		final StringBuilder sb = new StringBuilder();
		sb.append("for i = 1:height(icaos) ");
		sb.append(String.format("flight = %s(strcmp(%s{:, \'icao24\'},icaos{i}), :); ", table, table));
		sb.append("newSize = newSize + flight{end, \'time\'} - flight{1, \'time\'} + 1;");
		sb.append("end");
		engine.eval(sb.toString());

		engine.eval(String.format("tempTable = table(\'Size\', [newSize, 18],"
				+ "\'VariableTypes\', {\'double\', \'cell\', \'double\', \'double\', \'double\', \'double\', \'double\', \'cell\', \'cell\', \'cell\', \'cell\', \'double\', \'double\', \'double\', \'double\', \'double\', \'double\', \'uint8\'},"
				+ "\'VariableNames\', {\'time\', \'icao24\', \'lat\', \'lon\', \'velocity\', \'heading\', \'vertrate\', \'callsign\', \'onground\', \'alert\', \'spi\', \'squawk\', \'baroaltitude\', \'geoaltitude\', \'lastposupdate\', \'lastcontact\', \'hour\', \'class\'});"));
		
		
		sb.delete(0, sb.length());
		sb.append("ci = 1;");
		sb.append("for i = 1:height(icaos)");
		sb.append(String.format("flight = %s(strcmp(%s{:, \'icao24\'},icaos{i}), :);", table, table));
		sb.append("tempTable(ci,:) = flight(1, :); ci = ci + 1;");
		sb.append("for t = 2:height(flight)");
		sb.append("start = flight{t - 1, \'time\'};");
		sb.append("stop = flight{t, \'time\'};");
		sb.append("dif = stop - start;");
		sb.append("if dif > 1");
		sb.append("for n = 1:dif-1");
		sb.append("tempFlight = flight(t-1,:);");
		sb.append("tempFligth{1,\'time\'} = start + n;");
		sb.append("tempTable(ci,:) = tempFlight(1,:); ci=ci+1;");
		sb.append("end end");
		sb.append("tempTable(ci,:) = flight(t,:); ci=ci+1;");
		sb.append("end end");
		
		engine.eval(sb.toString());
		
		engine.eval(String.format("%s = tempTable(:,:);", table));
		engine.eval("clear newSize; clear flight; clear tempFlight; clear tempTable; clear ci; clear icaos; clear t; clear n; clear i; clear start; clear stop; clear dif;");
		
	}
	
	public static boolean loadSource(final Source source)
	{
		if(engine == null)
		{
			if( ! open() )
			{
				return false;
			}
		}

		switch(source.type)
		{
		case IMPORT_FILE:		
			return loadCSV(source.toString(), source.location);

		case LOCAL_DATABASE:
			return loadLocalTable(source.toString(), source.location);
			
		case OPENSKY_NETWORK:
			OptionPane.showMessage("Cannot load data from OpenSky.");
		}
		
		return false;
	}
	
	public static boolean removeSource(final String source)
	{
		if(TABLES.containsKey(source))
		{
			try 
			{
				engine.eval(String.format("clear %s;", TABLES.get(source)));
				TABLES.remove(source);

				return true;
			} 
			catch (CancellationException | InterruptedException | ExecutionException e) 
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	
	
	protected static MatlabEngine getEngine()
	{
		return engine;
	}
	
	public static boolean clearWorkspace()
	{
		try 
		{
			engine.eval("clearvars");
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeFlights(final List<Flight> flights)
	{
		System.out.println("Flights size: " + FLIGHTS.size());
		
		try
		{
			for(final Flight f: flights)
			{
				if(FLIGHTS.keySet().contains(f.getKey()))
				{
					FLIGHTS.remove(f.getKey());
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
}
