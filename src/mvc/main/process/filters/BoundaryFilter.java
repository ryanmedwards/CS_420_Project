package mvc.main.process.filters;

import java.awt.Dimension;

import components.Panel;
import mvc.filters.BoundaryMVC;

public class BoundaryFilter extends Filter<BoundaryFilter>
{
	final BoundaryMVC boundaryMVC = new BoundaryMVC();
	
	
	// Defaults
	
	private int selectedTab = 0;
	private int airport = 0;
	private int unit = 0;
	private double north = 0.0;
	private double south = 0.0;
	private double east = 0.0;
	private double west = 0.0;
	
	public BoundaryFilter(final FilterModel parent)
	{
		super(parent, "Reduce Boundary");
	}
	
	@Override
	protected String getDescription() 
	{
		return  "<html>"
			  + "This filter will reduce the boundary of messages included in the data set.<br>"
			  + "Unlike the filter used while collecting data, this filter can also define a<br>"
			  + "circular boundary."
			  + "<html>";
	}

	@Override
	protected void revert() 
	{
		boundaryMVC.menuPanel.setSelectedIndex(selectedTab);
		boundaryMVC.airportBox.setSelectedIndex(airport);
		boundaryMVC.unitsBox.setSelectedIndex(unit);
		boundaryMVC.northField.setValue(north);
		boundaryMVC.southField.setValue(south);
		boundaryMVC.eastField.setValue(east);
		boundaryMVC.westField.setValue(west);
	}

	@Override
	protected void reset() 
	{
		
	}

	@Override
	protected void copy() 
	{
		selectedTab = boundaryMVC.menuPanel.getSelectedIndex();
		airport = boundaryMVC.airportBox.getSelectedIndex();
		unit = boundaryMVC.unitsBox.getSelectedIndex();
		north = (double)boundaryMVC.northField.getValue();
		south = (double)boundaryMVC.southField.getValue();
		east = (double)boundaryMVC.eastField.getValue();
		west = (double)boundaryMVC.westField.getValue();
	}

	@Override
	public void drawPanel(Panel panel) 
	{
		panel.add(boundaryMVC.rootPanel);
	}

	@Override
	public Dimension getPreferredSize() 
	{
		return null;
	}

	@Override
	public void execute() 
	{
		
	}

	@Override
	protected String getName() {
		return "Reduce Boundary";
	}
}
