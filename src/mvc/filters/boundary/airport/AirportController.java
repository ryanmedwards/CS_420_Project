package mvc.filters.boundary.airport;

import data.Airport;

public class AirportController 
{
	private final AirportModel model;
	
	protected AirportController(final AirportModel model)
	{
		this.model = model;
		
		model.view.draw();
		
		this.assign();
	}
	
	private void assign()
	{
		model.airportBox.addActionListener(e -> updateCoordinates());
		
		updateCoordinates();
	}
	
	private void updateCoordinates()
	{
		try
		{
			final Airport airport = AirportModel.airportList.get(model.airportBox.getSelectedIndex());
			
			model. latitudeField.setValue(airport.getLat());
			model.longitudeField.setValue(airport.getLon());
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getDescription()
	{
		final int dist = (int) model.distanceSpinner.getValue() * 2;
		
		final String unit = AirportModel.unitAbbreviations[model.unitsBox.getSelectedIndex()];
		
		final String airport = ((Airport) model.airportBox.getSelectedItem()).getName();
		
		return String.format("%d by %d %s box around %s Airport.", dist, dist, unit, airport);
	}
	
	public double[] getBoundary()
	{	
		final double[] bounds = new double[4]; // NSEW
		
		final double degToRad = Math.PI / 180;
		final double radToDeg = 180 / Math.PI;
		
		final double lat1 = model.latitudeField.getValue() * degToRad;
		final double lon1 = model.longitudeField.getValue() * degToRad;
		
		final double earthPolarRadiusKM = 6371.0;
		
		final double conv = AirportModel.toKilometers[model.unitsBox.getSelectedIndex()];
		
		final double angDist = ((int) model.distanceSpinner.getValue()) * conv / earthPolarRadiusKM;
		
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
}
