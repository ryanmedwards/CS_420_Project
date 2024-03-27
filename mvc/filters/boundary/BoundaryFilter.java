package mvc.filters.boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import components.Label;
import components.Panel;
import components.TabbedPane;
import components.custom.ToggleBar;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;
import sql.FilterTable;
import sql.LocalSQL;

public class BoundaryFilter extends Filter
{
	final BoundaryModel boundaryModel = new BoundaryModel();

	// Defaults
	
	private int selectedTab = 0;
	private int airport = 0;
	private int unit = 0;
	private int shape = 0;
	private double north = 0.0;
	private double south = 0.0;
	private double east = 0.0;
	private double west = 0.0;

	
	
	public BoundaryFilter(final FilterModel model, final String name)
	{
		super(model, name);

		super.drawDialog();
	}
	
	@Override
	protected String getDescription() 
	{
		return  "<html>"
			  + "This filter will reduce the boundary of messages included in the data set."
			  + "<br>"
			  + "If an entry's coordinates are outside of the boundary, it will be removed."
			  + "<html>";
	}

	
	
	@Override
	public void revert() 
	{
		boundaryModel.menuPanel.setSelectedIndex(selectedTab);
		boundaryModel.airportModel.airportBox.setSelectedIndex(airport);
		boundaryModel.airportModel.unitsBox.setSelectedIndex(unit);
		boundaryModel.airportModel.shapeBar.setSelectedIndex(shape);
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
		shape   = boundaryModel.airportModel.  shapeBar.getSelectedIndex();
		
		north = (double) boundaryModel.boxModel.northField.getValue();
		south = (double) boundaryModel.boxModel.southField.getValue();
		east  = (double) boundaryModel.boxModel. eastField.getValue();
		west  = (double) boundaryModel.boxModel. westField.getValue();
	}

	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new BorderLayout());

		panel.add(boundaryModel.rootPanel, BorderLayout.CENTER);
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
	public boolean filter(final String table, final JTextArea log)
	{
		log.append("Removing entries outside selected boundary...");
		
		final double[] boundary = boundaryModel.control.getBoundary(); //NSEW
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("delete from %s where ", table));
		sb.append(String.format("lat > %f AND ", boundary[0]));
		sb.append(String.format("lat < %f AND ", boundary[1]));
		sb.append(String.format("lon > %f AND ", boundary[2]));
		sb.append(String.format("lon < %f;"    , boundary[3]));
		
		return LocalSQL.update(sb.toString());
	}

	
	@Override
	public String getSQLiteTable()
	{
		final int index = boundaryModel.menuPanel.getSelectedIndex();
		
		switch(index)
		{
		case 0: return FilterTable.BOUNDARY_BOX;
			
		case 1: return FilterTable.BOUNDARY_AIRPORT;
		}
		
		return FilterTable.ERROR;
	}
	
	
	
	@Override
	public boolean logFilter(final int processID, final int filterID)
	{
		return true;
		
	}
	

}












