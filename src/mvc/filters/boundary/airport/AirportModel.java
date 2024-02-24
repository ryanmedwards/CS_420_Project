package mvc.filters.boundary.airport;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;

import application.Application;
import components.ComboBox;
import components.CoordinateField;
import components.Label;
import components.Panel;
import components.Spinner;
import data.Airport;
import sql.LocalSQL;

public class AirportModel 
{
	// Maybe Create a Unit Conversion Class
	protected static final String[] unitAbbreviations = new String[] {"mi.", "km."};
	protected static final double[] toKilometers      = new double[] {1.609,   1.0};
	
	
	protected static final List<Airport> airportList  = LocalSQL.getAirports();

	public final Panel rootPanel = Application.createRootPanel();
	
	public final Panel airportPanel = new Panel(new GridLayout(0, 2, 10, 10));
	
	public final Label airportLabel         = new Label("Airport:");
	public final Label centerLatitudeLabel  = new Label("Latitude:");
	public final Label centerLongitudeLabel = new Label("Longitude:");
	public final Label distanceLabel        = new Label("Distance:");
	public final Label unitsLabel           = new Label("Unit:");
	
	public final ComboBox<Airport> airportBox = new ComboBox<>(airportList);
	public final ComboBox<String>  unitsBox   = new ComboBox<>(new String[] {"Miles", "Kilometers"});
	
	public final CoordinateField latitudeField  = new CoordinateField(CoordinateField.LATITUDE);
	public final CoordinateField longitudeField = new CoordinateField(CoordinateField.LONGITUDE);
	
	public final Spinner distanceSpinner = new Spinner(10, 0, 100, 1);
	
	
	public final AirportView       view    = new AirportView(this);
	public final AirportController control = new AirportController(this);
}
