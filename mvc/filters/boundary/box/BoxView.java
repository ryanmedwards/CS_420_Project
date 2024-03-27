package mvc.filters.boundary.box;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import guiutil.Grid;

public class BoxView 
{
	private final BoxModel model;
	
	protected BoxView(final BoxModel model)
	{
		this.model = model;
	}
	
	protected void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.CENTER);
		grid.setInset(10, 10, 10, 10);
		int x = 0;
		int y = 0;
		
		model.latitudePanel.add(model.northLabel);
		model.latitudePanel.add(model.northField);
		model.latitudePanel.add(model.southLabel);
		model.latitudePanel.add(model.southField);

		model.longitudePanel.add(model.eastLabel);
		model.longitudePanel.add(model.eastField);
		model.longitudePanel.add(model.westLabel);
		model.longitudePanel.add(model.westField);

		
		model.boxPanel.add(model.latitudeLabel, grid.set(  x,   y, 2, 1));
		model.boxPanel.add(model.latitudePanel, grid.set(x, ++y));
		
		model.boxPanel.add(model.longitudeLabel, grid.set(  x,   ++y, 2, 1));
		model.boxPanel.add(model.longitudePanel, grid.set(x, ++y));
		
		
		model.rootPanel.add(model.boxPanel);
	}
	
	
}
