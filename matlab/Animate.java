package matlab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

import application.Application;
import data.Flight;
import guiutil.OptionPane;
import mvc.main.simulation.AnimationSettings;

public class Animate 
{
	private static MatlabEngine engine = null;
	
	private static boolean hasData = false; 
	
	private static boolean isCombined = false;
	
	private static final String LOCAL_DB = "D:\\Java_Workspace\\CS_420_Project\\database\\LocalDB.db";
	


	public static boolean start()
	{
		if(Matlab.open())
		{
			engine = Matlab.getEngine();
			return true;
		}
		
		return false;
	}

	
	

	

	
	private static boolean combineData()
	{
		try 
		{
			
			engine.eval("data = [];");
			
			for(final Flight f: Matlab.FLIGHTS.values())
			{
				final String table = Matlab.TABLES.get(f.source.toString());
				
				//                            table     table         icao          table         callsign  table       class
				engine.eval(String.format("fl = %s(strcmp(%s.icao24, \'%s\') & strcmp(%s.callsign, \'%s\') & %s.class == %d, [\"time\" \"lat\" \"lon\" \"heading\" \"class\"]);", 
						table, table, f.icao24, table, f.callsign, table, f.type.toInt()));
				
				engine.eval("data = [data; fl];");
			}
			
			engine.eval("clear fl;");
			
			engine.eval("data = convertvars(data, 1:4,\'double\');");

			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean saveFigure(final AnimationSettings settings)
	{
		if( ! start() )
		{
			return false;
		}

		if( ! combineData() )
		{
			OptionPane.showError("Failed to combine datasets.");
			return false;
		}

			
		if( ! setSettings(settings) )
		{
			OptionPane.showError("Failed to set settings.");
			return false;
		}
		
		if( ! loadBasemap() )
		{
			OptionPane.showError("Failed to load basemap.");
			return false;
		}
		
		if( ! loadPlaneImages() )
		{
			OptionPane.showError("Failed to load plane images.");
			return false;
		}
		

		try 
		{
			// Initialize Figure
			engine.eval("fig = figure(\'Visible\', \'off\');");
			engine.eval("fig.Position = [0 0 rasterWidth rasterHeight];");
			engine.eval("axis off;");
			engine.eval("hold on;");
			engine.eval("title(titleOfFig);");
			engine.eval("t = \"Basemap Attribution: \" + attrib;");
			engine.eval("t = string(textwrap(t,70));");
			engine.eval("subtitle(t,FontSize=9)");
			engine.eval("mapshow(A, RA);");
			engine.eval("zoom on");
			engine.eval("zoom(figZoom)");
			engine.eval("data = sortrows(data, \'time\');");
			// Plot Coordinates
			final StringBuilder sb = new StringBuilder();
			
			// For time = 1 to time = end with time += timeInterval
			sb.append("for i = 1:timeInterval:height(data);");
			
			// Get lat, lon, and heading values
			sb.append("heading = data{i,\'heading\'} * -1; ");
			sb.append("lat = data{i, \'lat\'}; ");
			sb.append("lon = data{i, \'lon\'}; ");    
	            
			// Select Yellow Plane or Red Plane
	        sb.append("if data{i,\'class\'} == 0;");
            sb.append("planeImage = imrotate(plane_y, heading);");
            sb.append("planeAlpha = imrotate(alpha_y, heading);");
            sb.append("pcmap = imrotate(cmap_y, heading);");
            sb.append(" else ");
            sb.append("planeImage = imrotate(plane_r, heading);");
            sb.append("planeAlpha = imrotate(alpha_r, heading);");
            sb.append("pcmap = imrotate(cmap_r, heading);");
            sb.append("end;");
	            
			// Translate Geo Coordinates to Figure
			sb.append("[x,y] = projfwd(RA.ProjectedCRS,lat,lon); ");
			sb.append("sz = size(planeImage);");
			sb.append("xlimits = [x (x + sz(2)*RA.CellExtentInWorldX)];");
			sb.append("ylimits = [y (y + sz(1)*RA.CellExtentInWorldY)];");
			sb.append("RM = maprefcells(xlimits,ylimits,sz,ColumnsStartFrom=\"north\");");
			sb.append("mapshow(planeImage, pcmap, RM, 'AlphaData', planeAlpha); ");
			
			sb.append("end;");
			engine.eval(sb.toString());
			
			engine.eval("exportgraphics(fig, strcat(titleOfFig, \'.png\'),\'Resolution\',dpi);");
			
			return true;			
		} 
		catch (IllegalStateException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
			
	
	public static boolean testSettings(final AnimationSettings settings)
	{
		if( ! start() )
		{
			return false;
		}

		if( ! combineData() )
		{
			OptionPane.showError("Failed to combine datasets.");
			return false;
		}

			
		if( ! setSettings(settings) )
		{
			OptionPane.showError("Failed to set settings.");
			return false;
		}
		
		if( ! loadBasemap() )
		{
			OptionPane.showError("Failed to load basemap.");
			return false;
		}
		
		if( ! loadPlaneImages() )
		{
			OptionPane.showError("Failed to load plane images.");
			return false;
		}
		

		try 
		{
			// Initialize Figure
			engine.eval("fig=figure;");
			//engine.eval("fig.Position = [0 0 rasterWidth rasterHeight];");
			engine.eval("axis off;");
			engine.eval("hold on;");
			engine.eval("title(titleOfFig);");
			engine.eval("subtitle({\'Map Attributes:\', attrib})");
			engine.eval("mapshow(A, RA);");
			engine.eval("zoom on");
			engine.eval("zoom(figZoom)");
			engine.eval("data = sortrows(data, \'time\');");
			// Plot Coordinates
			final StringBuilder sb = new StringBuilder();
			
			// For time = 1 to time = end with time += timeInterval
			sb.append("for i = 1:timeInterval:height(data);");
			
			// Get lat, lon, and heading values
			sb.append("heading = data{i,\'heading\'} * -1; ");
			sb.append("lat = data{i, \'lat\'}; ");
			sb.append("lon = data{i, \'lon\'}; ");    
	            
			// Select Yellow Plane or Red Plane
	        sb.append("if data{i,\'class\'} == 0;");
            sb.append("planeImage = imrotate(plane_y, heading);");
            sb.append("planeAlpha = imrotate(alpha_y, heading);");
            sb.append("pcmap = imrotate(cmap_y, heading);");
            sb.append(" else ");
            sb.append("planeImage = imrotate(plane_r, heading);");
            sb.append("planeAlpha = imrotate(alpha_r, heading);");
            sb.append("pcmap = imrotate(cmap_r, heading);");
            sb.append("end;");
	            
			// Translate Geo Coordinates to Figure
			sb.append("[x,y] = projfwd(RA.ProjectedCRS,lat,lon); ");
			sb.append("sz = size(planeImage);");
			sb.append("xlimits = [x (x + sz(2)*RA.CellExtentInWorldX)];");
			sb.append("ylimits = [y (y + sz(1)*RA.CellExtentInWorldY)];");
			sb.append("RM = maprefcells(xlimits,ylimits,sz,ColumnsStartFrom=\"north\");");
			sb.append("mapshow(planeImage, pcmap, RM, 'AlphaData', planeAlpha); ");
			
			sb.append("end;");
			engine.eval(sb.toString());
			
			return true;			
		} 
		catch (IllegalStateException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	

	private static boolean setSettings(final AnimationSettings settings)
	{		
		try
		{
			engine.eval("bmi = \'streets-light\';");
			engine.eval("titleOfFig = \'" + settings.title + "\';");
			engine.eval("basemapZoom = " + settings.basemapZoom + ";");
			engine.eval("rasterWidth = " + settings.rasterWidth + ";");
			engine.eval("rasterHeight = " + settings.rasterHeight + ";");
			engine.eval("planeScale = " + settings.planeScale + ";");
			engine.eval("figZoom = " + settings.figureZoom + ";");
			engine.eval("dpi = " + settings.dpi + ";");
			engine.eval("latOffset = " + settings.latitudeOffset + ";");
			engine.eval("lonOffset = " + settings.longitudeOffset + ";");
			engine.eval("timeInterval = " + settings.timeInterval + ";");	
			
			return true;
		}
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadPlaneImages()
	{
		try 
		{
			engine.eval("[plane_y, cmap_y, alpha_y] = imread('AirplaneN.png');");
			engine.eval("[plane_y, cmap_y] = imresize(plane_y, planeScale);");
			engine.eval("alpha_y = imresize(alpha_y, planeScale);");
			
			engine.eval("[plane_r, cmap_r, alpha_r] = imread('AirplaneN_Attack.png');");
			engine.eval("[plane_r, cmap_r] = imresize(plane_r, planeScale);");
			engine.eval("alpha_r = imresize(alpha_r, planeScale);");

			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	private static boolean loadBasemap()
	{
		try 
		{
			engine.eval("clat = ((max(data.lat) + min(data.lat)) / 2) + latOffset;");
			engine.eval("clon = ((max(data.lon) + min(data.lon)) / 2) + lonOffset;");
			engine.eval("[A,RA, attrib] = readBasemapImage(bmi,[clat clon], basemapZoom, [rasterHeight rasterWidth]);");
			
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean animate(final AnimationSettings settings)
	{
		if( ! start() )
		{
			return false;
		}
		
		if( ! combineData() )
		{
			OptionPane.showError("Failed to combine datasets.");
			return false;
		}
		
		
		if( ! setSettings(settings) )
		{
			OptionPane.showError("Failed to set settings.");
			return false;
		}
		
		if( ! loadBasemap() )
		{
			OptionPane.showError("Failed to load basemap.");
			return false;
		}
		
		if( ! loadPlaneImages() )
		{
			OptionPane.showError("Failed to load plane images.");
			return false;
		}
		
		
		
		
		try
		{
			engine.eval("close all;");
			
			// Create Figure
			engine.eval("fig = figure(\'Visible\', \'off\');");
			engine.eval("fig.Position = [0 0 rasterWidth rasterHeight];");
			engine.eval("zoom on");
			engine.eval("zoom(figZoom)");
			
			// Create VideoWriter
			engine.eval("vw = VideoWriter(titleOfFig);");
			engine.eval("open(vw);");
			
			// Sort Data by time
			engine.eval("data = sortrows(data, \'time\');");	
		}
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			OptionPane.showError("Failed to create animation.");
			return false;
		}
		
		final String[] cmds = new String[]
		{

			// Plot based on time interval
			"for t = data{1, \'time\'}:timeInterval:data{end, \'time\'};",
				
				"flights = data(data.time == t, :);",
				"hold off;",
				"mapshow(A, RA);",
				"hold on;",
			
				// For each flight at time = t
				"for i = 1:height(flights);",

					// Get flight heading, lat, and lon values
					"heading = flights{i, \'heading\'} * -1;",
					"lat = flights{i, \'lat\'};",
					"lon = flights{i, \'lon\'};",
					
					// Select Yellow Plane if authentic, Red Plane if attack
					"if flights{i,\'class\'} == 0;",
						"planeImage = imrotate(plane_y, heading);",
						"planeAlpha = imrotate(alpha_y, heading);",
						"pcmap = imrotate(cmap_y, heading);",
					"else;",
						"planeImage = imrotate(plane_r, heading);",
						"planeAlpha = imrotate(alpha_r, heading);",
						"pcmap = imrotate(cmap_r, heading);",
					"end;",

					// Spatially Reference Icon
					"[x,y] = projfwd(RA.ProjectedCRS,lat,lon);",
					"sz = size(planeImage);",
					"xlimits = [x (x + sz(2)*RA.CellExtentInWorldX)];",
					"ylimits = [y (y + sz(1)*RA.CellExtentInWorldY)];",
					"RM = maprefcells(xlimits,ylimits,sz,ColumnsStartFrom=\"north\");",
					"mapshow(planeImage,pcmap,RM, \'AlphaData\', planeAlpha);",
					
				"end;",
				"fig.Position = [0 0 rasterWidth rasterHeight];",
				// Write frame to video
				"writeVideo(vw, getframe);",
				
			"end;",
			
			// Close VideoWriter
			"close(vw);"
		};
		
		
		final StringBuilder sb = new StringBuilder();
		
		for(final String str: cmds)
		{
			sb.append(str);
		}
		
		
		try 
		{
			engine.eval(sb.toString());
			return true;
		} 
		catch (CancellationException | InterruptedException | ExecutionException e) 
		{
			if(Application.DEBUG) e.printStackTrace();
			OptionPane.showError("Failed to create animation.");
			return false;
		}
	}	
}











