package mvc.filters.boundary;

import java.awt.BorderLayout;

import application.Application;
import components.Panel;
import components.TabPanel;
import mvc.filters.boundary.airport.AirportModel;
import mvc.filters.boundary.box.BoxModel;

public class BoundaryModel 
{
	public final Panel rootPanel = Application.createRootPanel();
	
	public final TabPanel menuPanel = new TabPanel(TabPanel.RIGHT);
	
	public final AirportModel airportModel = new AirportModel();
	public final BoxModel     boxModel     = new BoxModel();
	
	public final BoundaryView       view    = new BoundaryView(this);
	public final BoundaryController control = new BoundaryController(this);
}