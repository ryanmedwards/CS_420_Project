package matlab;

import java.util.HashMap;
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
import mvc.main.simulation.GeoplotSettings;
import opensky.statevector.MessageClassification;

public class GeoPlot 
{
	private static MatlabEngine engine = null;

	
	
	private static final String AU_STYLE ="auStyle";
	private static final String PM_STYLE ="pmStyle";
	private static final String GI_STYLE ="giStyle";
	private static final String VD_STYLE ="vdStyle";
	
	private static final String AU_MARKER ="auMarker";
	private static final String PM_MARKER ="pmMarker";
	private static final String GI_MARKER ="giMarker";
	private static final String VD_MARKER ="vdMarker";
	
	private static final String AU_COLOR ="auColor";
	private static final String PM_COLOR ="pmColor";
	private static final String GI_COLOR ="giColor";
	private static final String VD_COLOR ="vdColor";
	
	

	

	public static boolean start()
	{
		if(Matlab.open())
		{
			engine = Matlab.getEngine();
			return true;
		}
		
		return false;
	}

	
	public static boolean setAppearance(final GeoplotSettings apr)
	{
		try 
		{
			engine.eval(String.format("%s = \'%s\';", AU_STYLE, apr.auStyle));
			engine.eval(String.format("%s = \'%s\';", PM_STYLE, apr.pmStyle));
			engine.eval(String.format("%s = \'%s\';", GI_STYLE, apr.giStyle));
			engine.eval(String.format("%s = \'%s\';", VD_STYLE, apr.vdStyle));
			
			engine.eval(String.format("%s = \'%s\';", AU_MARKER, apr.auMarker));
			engine.eval(String.format("%s = \'%s\';", PM_MARKER, apr.pmMarker));
			engine.eval(String.format("%s = \'%s\';", GI_MARKER, apr.giMarker));
			engine.eval(String.format("%s = \'%s\';", VD_MARKER, apr.vdMarker));

			engine.eval(String.format("%s = \'%s\';", AU_COLOR, apr.auColor));
			engine.eval(String.format("%s = \'%s\';", PM_COLOR, apr.pmColor));
			engine.eval(String.format("%s = \'%s\';", GI_COLOR, apr.giColor));
			engine.eval(String.format("%s = \'%s\';", VD_COLOR, apr.vdColor));
			
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			OptionPane.showError("Failed to set plot apperance settings.");
			return false;
		}			
	}
	
	
	private static void plotFlights() throws MatlabExecutionException, MatlabSyntaxException, CancellationException, EngineException, InterruptedException, ExecutionException
	{
		for(final Flight f: Matlab.FLIGHTS.values())
		{
			final String table = Matlab.TABLES.get(f.source.toString());

			//                            table     table         icao          table         callsign  table       class
			engine.eval(String.format("fl = %s(strcmp(%s.icao24, \'%s\') & strcmp(%s.callsign, \'%s\') & %s.class == %d, [\"lat\" \"lon\"]);"
					, table, table, f.icao24, table, f.callsign, table, f.type.toInt()));				
			
			
			switch(f.type)
			{
			case AUTHENTIC:
				
				engine.eval(String.format(
						"geoplot(fl{:,\'lat\'}, fl{:,\'lon\'}, \'LineStyle\', %s, \'Marker\', %s, \'Color\', %s);",
						AU_STYLE, AU_MARKER, AU_COLOR));
				
				break;
				
			case GHOST_INJECTION:
				
				engine.eval(String.format(
						"geoplot(fl{:,\'lat\'}, fl{:,\'lon\'}, \'LineStyle\', %s, \'Marker\', %s, \'Color\', %s);",
						GI_STYLE, GI_MARKER, GI_COLOR));
				
				break;
				
			case PATH_MODIFICATION:
				
				engine.eval(String.format(
						"geoplot(fl{:,\'lat\'}, fl{:,\'lon\'}, \'LineStyle\', %s, \'Marker\', %s, \'Color\', %s);",
						PM_STYLE, PM_MARKER, PM_COLOR));
				
				break;
				
			case VELOCITY_DRIFT:
				
				engine.eval(String.format(
						"geoplot(fl{:,\'lat\'}, fl{:,\'lon\'}, \'LineStyle\', %s, \'Marker\', %s, \'Color\', %s);",
						VD_STYLE, VD_MARKER, VD_COLOR));
				
				break;
			}
			engine.eval("hold on");
		}
		
		engine.eval("clear fl;");
	}
	
	
	
	
	public static boolean geoplot(final GeoplotSettings settings, final boolean save)
	{		
		if( ! start() )
		{
			return false;
		}
		
		if(Matlab.TABLES.isEmpty())
		{
			return false;
		}
		
		if( ! setAppearance(settings) )
		{
			return false;
		}
		
		try 
		{	
			if(save)
			{
				engine.eval("fig = figure;");
			}
			
			
			plotFlights();

			
			engine.eval(String.format("title(\'%s\')", settings.title));
			engine.eval(String.format("subtitle(\'%s\')", settings.subtitle));

			if(save)
			{
				engine.eval("exportgraphics(fig, strcat(\'test.png\'),\'Resolution\',600);");
			}
			
			
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
}


















