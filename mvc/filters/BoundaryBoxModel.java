package mvc.filters;

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
import guiutil.Grid;

public class BoundaryBoxModel 
{
	public final JPanel rootPanel = new RootPanel().initLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)).get();
	
	protected final JPanel latitudePanel = new JPanel(new GridLayout(0, 2, 10, 10));
	protected final JPanel longitudePanel = new JPanel(new GridLayout(0, 2, 10, 10));
	
	public final JPanel boxPanel = new JPanel(new GridBagLayout());
	
	public final JLabel latitudeLabel  = new Label("Latitude").initFontSize(18).get();
	public final JLabel longitudeLabel = new Label("Longitude").initFontSize(18).get();
	public final JLabel northLabel     = new JLabel("North:");
	public final JLabel southLabel     = new JLabel("South:");
	public final JLabel eastLabel      = new JLabel("East:");
	public final JLabel westLabel      = new JLabel("West:");
	
	public final JSpinner northField = new LatitudeField().get();
	public final JSpinner southField = new LatitudeField().get();
	
	public final JSpinner eastField  = new LongitudeField().get();
	public final JSpinner westField  = new LongitudeField().get();
	
	
	
	protected BoundaryBoxModel()
	{
		this.draw();
		this.assign();
	}
	
	
	protected void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.CENTER);
		grid.setInset(10, 10, 10, 10);
		int x = 0;
		int y = 0;
		
		latitudePanel.add(northLabel);
		latitudePanel.add(northField);
		latitudePanel.add(southLabel);
		latitudePanel.add(southField);

		longitudePanel.add(eastLabel);
		longitudePanel.add(eastField);
		longitudePanel.add(westLabel);
		longitudePanel.add(westField);

		
		boxPanel.add(latitudeLabel, grid.set(  x,   y, 2, 1));
		boxPanel.add(latitudePanel, grid.set(x, ++y));
		
		boxPanel.add(longitudeLabel, grid.set(  x,   ++y, 2, 1));
		boxPanel.add(longitudePanel, grid.set(x, ++y));
		
		
		rootPanel.add(boxPanel);
	}
	
	private void assign()
	{
		
	}

	
	public double[] getBoundary()
	{
		final double north = (double) northField.getValue();
		final double south = (double) southField.getValue();
		final double east  = (double)  eastField.getValue();
		final double west  = (double)  westField.getValue();
		
		return new double[] { north, south, east, west };
	}
	
}
