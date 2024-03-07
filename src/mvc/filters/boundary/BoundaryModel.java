package mvc.filters.boundary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import application.Application;
import components.Panel;
import components.TabbedPane;
import components.custom.RootPanel;
import mvc.filters.boundary.airport.AirportModel;
import mvc.filters.boundary.box.BoxModel;

public class BoundaryModel 
{
	public final JPanel rootPanel = new RootPanel().get();
	
	public final JTabbedPane menuPanel = new TabbedPane(JTabbedPane.RIGHT).get();
	
	public final AirportModel airportModel = new AirportModel();
	public final BoxModel     boxModel     = new BoxModel();
	
	public final BoundaryView       view    = new BoundaryView(this);
	public final BoundaryController control = new BoundaryController(this);
}