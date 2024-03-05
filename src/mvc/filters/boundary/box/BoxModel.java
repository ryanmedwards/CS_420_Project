package mvc.filters.boundary.box;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import components.Label;
import components.custom.LatitudeField;
import components.custom.LongitudeField;
import components.custom.RootPanel;

public class BoxModel 
{
	public final JPanel rootPanel = new RootPanel().initLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)).get();
	
	protected final JPanel latitudePanel = new JPanel(new GridLayout(0, 2, 10, 10));
	protected final JPanel longitudePanel = new JPanel(new GridLayout(0, 2, 10, 10));
	
	public final JPanel boxPanel = new JPanel(new GridBagLayout());
	
	public final JLabel latitudeLabel  = new JLabel("Latitude");
	public final JLabel longitudeLabel = new JLabel("Longitude");
	public final JLabel northLabel     = new Label("North:").initBorder(BorderFactory.createEmptyBorder(3, 50, 3, 50)).get();
	public final JLabel southLabel     = new Label("South:").initBorder(BorderFactory.createEmptyBorder(3, 50, 3, 50)).get();
	public final JLabel eastLabel      = new Label("East:").initBorder(BorderFactory.createEmptyBorder(3, 50, 3, 50)).get();
	public final JLabel westLabel      = new Label("West:").initBorder(BorderFactory.createEmptyBorder(3, 50, 3, 50)).get();
	
	public final JSpinner northField = new LatitudeField().get();
	public final JSpinner southField = new LatitudeField().get();
	
	public final JSpinner eastField  = new LongitudeField().get();
	public final JSpinner westField  = new LongitudeField().get();
	
	public final BoxView       view    = new BoxView(this);
	public final BoxController control = new BoxController(this);
}
