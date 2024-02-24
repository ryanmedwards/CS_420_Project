package mvc.filters.boundary.airport;

import java.awt.BorderLayout;

public class AirportView 
{
	private final AirportModel model;
	
	protected AirportView(final AirportModel model)
	{
		this.model = model;
	}
	
	protected void draw()
	{	
		model.airportPanel.add(model.airportLabel);
		model.airportPanel.add(model.airportBox);
		
		model.airportPanel.add(model.centerLatitudeLabel);
		model.airportPanel.add(model.latitudeField);
		
		model.airportPanel.add(model.centerLongitudeLabel);
		model.airportPanel.add(model.longitudeField);
		
		model.airportPanel.add(model.distanceLabel);
		model.airportPanel.add(model.distanceSpinner);
		
		model.airportPanel.add(model.unitsLabel);
		model.airportPanel.add(model.unitsBox);
		
		
		model.rootPanel.add(model.airportPanel, BorderLayout.NORTH);
	} 
}
