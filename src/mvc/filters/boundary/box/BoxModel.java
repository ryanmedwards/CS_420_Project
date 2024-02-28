package mvc.filters.boundary.box;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import components.custom.LatitudeField;
import components.custom.LongitudeField;
import components.custom.RootPanel;

public class BoxModel 
{
	public final JPanel rootPanel = new RootPanel().get();
	
	public final JPanel boxPanel = new JPanel(new GridBagLayout());
	
	public final JLabel latitudeLabel  = new JLabel("Latitude");
	public final JLabel longitudeLabel = new JLabel("Longitude");
	public final JLabel northLabel     = new JLabel("North:");
	public final JLabel southLabel     = new JLabel("South:");
	public final JLabel eastLabel      = new JLabel("East:");
	public final JLabel westLabel      = new JLabel("West:");
	
	public final JSpinner northField = new LatitudeField().get();
	public final JSpinner southField = new LatitudeField().get();
	
	public final JSpinner eastField  = new LongitudeField().get();
	public final JSpinner westField  = new LongitudeField().get();
	
	public final BoxView       view    = new BoxView(this);
	public final BoxController control = new BoxController(this);
}
