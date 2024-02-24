package mvc.filters.boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;

import components.Label;
import components.Panel;
import components.TabPanel;
import components.ToggleBar;
import mvc.filters.Filter;
import mvc.main.process.builder.BuilderModel;

public class BoundaryFilter extends Filter
{
	final BoundaryModel boundaryModel = new BoundaryModel();
	
	final ToggleBar shapeBar = new ToggleBar(5, "Box", "Circle");
	
	// Defaults
	
	private int selectedTab = 0;
	private int airport = 0;
	private int unit = 0;
	private double north = 0.0;
	private double south = 0.0;
	private double east = 0.0;
	private double west = 0.0;
	
	public BoundaryFilter(final BuilderModel parent)
	{
		super(parent, "Reduce Boundary");
		
		super.drawDialog();
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
	public void revert() 
	{
		boundaryModel.menuPanel.setSelectedIndex(selectedTab);
		boundaryModel.airportModel.airportBox.setSelectedIndex(airport);
		boundaryModel.airportModel.unitsBox.setSelectedIndex(unit);
		boundaryModel.boxModel.northField.setValue(north);
		boundaryModel.boxModel.southField.setValue(south);
		boundaryModel.boxModel.eastField.setValue(east);
		boundaryModel.boxModel.westField.setValue(west);
	}

	@Override
	public void reset() 
	{
		
	}

	@Override
	public void copy() 
	{
		selectedTab = boundaryModel.menuPanel.getSelectedIndex();
		
		airport = boundaryModel.airportModel.airportBox.getSelectedIndex();
		unit    = boundaryModel.airportModel.  unitsBox.getSelectedIndex();
		
		north = (double) boundaryModel.boxModel.northField.getValue();
		south = (double) boundaryModel.boxModel.southField.getValue();
		east  = (double) boundaryModel.boxModel. eastField.getValue();
		west  = (double) boundaryModel.boxModel. westField.getValue();
	}

	@Override
	public void drawPanel(Panel panel) 
	{
		
		boundaryModel.airportModel.airportPanel.add(new Label("Shape:"));
		boundaryModel.airportModel.airportPanel.add(shapeBar);
		
		
		boundaryModel.menuPanel.setTabPlacement(TabPanel.LEFT);
		
		panel.add(boundaryModel.rootPanel);
	}


	
	@Override
	public String getDetails()
	{
		
		final StringBuilder sb = new StringBuilder("");
		
		final int tab = boundaryModel.menuPanel.getSelectedIndex();
		
		
		
		final double[] bounds = boundaryModel.boxModel.control.getBoundary();
		
		
		
		
		
		switch(tab)
		{
		case 0: // Box
			
			return "";
		}
		
		return "Failed to get Boundary Details.";
	}
	
	

	@Override
	public void execute() 
	{
		
	}


}
