package mvc.filters;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;

import components.ComboBox;
import components.CoordinateField;
import components.Label;
import components.Panel;
import components.Spinner;
import components.TabPanel;
import data.Airport;
import guiutil.Grid;
import sql.LocalSQL;

public class BoundaryMVC
{	
	
	public final TabPanel menuPanel = new TabPanel(TabPanel.RIGHT);

	
	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	

	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// ***	Boundary Box			 						***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	public final Panel boxPanel = new Panel(new GridBagLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	public final Label latitudeLabel  = new Label("Latitude");
	public final Label longitudeLabel = new Label("Longitude");
	public final Label northLabel     = new Label("North:");
	public final Label southLabel     = new Label("South:");
	public final Label eastLabel      = new Label("East:");
	public final Label westLabel      = new Label("West:");
	
	public final CoordinateField northField = new CoordinateField(CoordinateField.LATITUDE);
	public final CoordinateField southField = new CoordinateField(CoordinateField.LATITUDE);
	public final CoordinateField eastField  = new CoordinateField(CoordinateField.LONGITUDE);
	public final CoordinateField westField  = new CoordinateField(CoordinateField.LONGITUDE);
	
	
	public double[] getBoxBoundary()
	{
		return new double[] 
		{
			(double) northField.getValue(),
			(double) southField.getValue(),
			(double) eastField.getValue(),
			(double) westField.getValue()
		};
	}
	
	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// ***	Airport			     				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	
	public static final String[] unitAbbreviations = new String[] {"mi.", "km."};
	public static final double[] toKilometers      = new double[] {1.609,   1.0};
	public static final List<Airport> airportList = LocalSQL.getAirports();
	


	public final Panel airportPanel = new Panel(new GridLayout(0, 2, 10, 10)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	public final Label airportLabel   = new Label("Airport:");
	public final Label centerLatitudeLabel  = new Label("Latitude:");
	public final Label centerLongitudeLabel = new Label("Longitude:");
	public final Label distanceLabel  = new Label("Distance:");
	public final Label unitsLabel     = new Label("Unit:");
	
	public final ComboBox<Airport> airportBox = new ComboBox<>(airportList);
	public final ComboBox<String>  unitsBox   = new ComboBox<>(new String[] {"Miles", "Kilometers"});
	
	public final CoordinateField latitudeField  = new CoordinateField(CoordinateField.LATITUDE);
	public final CoordinateField longitudeField = new CoordinateField(CoordinateField.LONGITUDE);
	
	public final Spinner distanceSpinner = new Spinner(10, 0, 100, 1);
	
	
	
	public double[] getAirportBoundary()
	{	
		final double[] bounds = new double[4]; // NSEW
		
		final double degToRad = Math.PI / 180;
		final double radToDeg = 180 / Math.PI;
		
		final double lat1 = latitudeField.get() * degToRad;
		final double lon1 = longitudeField.get() * degToRad;
		
		final double earthPolarRadiusKM = 6371.0;
		
		final double conv = toKilometers[unitsBox.getSelectedIndex()];
		
		final double angDist = ((int) distanceSpinner.getValue()) * conv / earthPolarRadiusKM;
		
		final double sinLat1 = Math.sin(lat1);
		final double cosLat1 = Math.cos(lat1);
		
		final double sinAngDist = Math.sin(angDist);
		final double cosAngDist = Math.cos(angDist);
		
		bounds[0] = (Math.asin( sinLat1 * cosAngDist + cosLat1 * sinAngDist *  1)) * radToDeg;
		bounds[1] = (Math.asin( sinLat1 * cosAngDist + cosLat1 * sinAngDist * -1)) * radToDeg;
		bounds[2] = (lon1 + Math.atan2( 1 * sinAngDist * cosLat1 , cosAngDist - sinLat1 * sinLat1)) * radToDeg;
		bounds[3] = (lon1 + Math.atan2(-1 * sinAngDist * cosLat1 , cosAngDist - sinLat1 * sinLat1)) * radToDeg;
		
		if(bounds[0] > 90.0) bounds[0] = 90.0;
		
		if(bounds[1] < -90.0) bounds[1] = -90.0;
		
		if(bounds[2] >= 180.0) bounds[2] = -180.0 + (bounds[2] - 180.0);
		
		if(bounds[3] < -180.0) bounds[3] = 180.0 - (-1 * bounds[3] - 180);

		return bounds;
	}
	
	
	protected void updateCoordinates()
	{
		final Airport airport = airportList.get(airportBox.getSelectedIndex());
		
		latitudeField.setValue(airport.getLat());
		longitudeField.setValue(airport.getLon());
	}
	
	
	
	public BoundaryMVC()
	{
		draw();
		assign();
	}
	
	
	public String getAirportBoundaryString()
	{		
		final int dist = (int) distanceSpinner.getValue() * 2;
		
		final String unit = unitAbbreviations[unitsBox.getSelectedIndex()];
		
		final String airport = ((Airport) airportBox.getSelectedItem()).getName();
		
		return String.format("%d by %d %s box around %s Airport.", dist, dist, unit, airport);
	}
	
	public String getBoxBoundaryString()
	{
		final double north = (double) northField.getValue();
		final double south = (double) southField.getValue();
		final double west = (double) westField.getValue();
		final double east = (double) eastField.getValue();
		
		return String.format("[NSEW] = [ %.2f, %.2f, %.2f, %.2f ]", north, south, west, east);
	}
	
	public String getBoundaryString()
	{
		final int tab = menuPanel.getSelectedIndex();
		
		switch(tab)
		{
		case 0: return getBoxBoundaryString();
		
		case 1: return getAirportBoundaryString();
		}
		
		return "";
	}
	
	
	private void draw()
	{
		
		// Menu
		
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.CENTER);
		grid.setInset(10, 10, 10, 10);
		int x = 0;
		int y = 0;
		
		boxPanel.add(latitudeLabel, grid.set(  x,   y, 2, 1));
		boxPanel.add(northLabel   , grid.set(  x, ++y));
		boxPanel.add(northField   , grid.set(++x,   y));
		boxPanel.add(southLabel   , grid.set(--x, ++y));
		boxPanel.add(southField   , grid.set(++x,   y));
		
		boxPanel.add(longitudeLabel, grid.set(--x, ++y, 2, 1));
		boxPanel.add(eastLabel     , grid.set(  x, ++y));
		boxPanel.add(eastField     , grid.set(++x,   y));
		boxPanel.add(westLabel     , grid.set(--x, ++y));
		boxPanel.add(westField     , grid.set(++x,   y));

		
		airportPanel.add(airportLabel);
		airportPanel.add(airportBox);
		
		airportPanel.add(centerLatitudeLabel);
		airportPanel.add(latitudeField);
		
		airportPanel.add(centerLongitudeLabel);
		airportPanel.add(longitudeField);
		
		airportPanel.add(distanceLabel);
		airportPanel.add(distanceSpinner);
		
		airportPanel.add(unitsLabel);
		airportPanel.add(unitsBox);
		

		menuPanel.addTab("Boundary Box", boxPanel);
		menuPanel.addTab("Airport", airportPanel);
		
		
		rootPanel.add(menuPanel, BorderLayout.CENTER);
	}
	
	
	
	
	private void assign()
	{
		airportBox.addActionListener(e -> 
		{
			updateCoordinates();
			
		});
		
		
		updateCoordinates();
		
	}
	
}
