package mvc.filters.boundary.airport;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import components.ComboBox;
import components.Spinner;
import components.custom.LatitudeField;
import components.custom.LongitudeField;
import components.custom.RootPanel;
import data.Airport;
import sql.LocalSQL;

public class AirportModel 
{
	// Maybe Create a Unit Conversion Class
	protected static final String[] unitAbbreviations = new String[] {"mi.", "km."};
	protected static final double[] toKilometers      = new double[] {1.609,   1.0};
	
	
	protected static final List<Airport> airportList  = LocalSQL.getAirports();

	public final JPanel rootPanel = new RootPanel().get();
	
	public final JPanel airportPanel = new JPanel(new GridLayout(0, 2, 10, 10));
	
	public final JLabel airportLabel         = new JLabel("Airport:");
	public final JLabel centerLatitudeLabel  = new JLabel("Latitude:");
	public final JLabel centerLongitudeLabel = new JLabel("Longitude:");
	public final JLabel distanceLabel        = new JLabel("Distance:");
	public final JLabel unitsLabel           = new JLabel("Unit:");
	
	public final JComboBox<Airport> airportBox = new  ComboBox<>(airportList).get();
	public final JComboBox<String>  unitsBox   = new JComboBox<>(new String[] {"Miles", "Kilometers"});
	
	public final JSpinner latitudeField  = new LatitudeField().get();
	public final JSpinner longitudeField = new LongitudeField().get();
	
	public final JSpinner distanceSpinner = new Spinner().initModel(10, 0, 100, 1).get();
	
	
	public final AirportView       view    = new AirportView(this);
	public final AirportController control = new AirportController(this);
}
