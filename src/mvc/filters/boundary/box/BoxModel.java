package mvc.filters.boundary.box;

import java.awt.GridBagLayout;

import application.Application;
import components.CoordinateField;
import components.Label;
import components.Panel;

public class BoxModel 
{
	public final Panel rootPanel = Application.createRootPanel();
	
	public final Panel boxPanel = new Panel(new GridBagLayout());
	
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
	
	public final BoxView       view    = new BoxView(this);
	public final BoxController control = new BoxController(this);
}
