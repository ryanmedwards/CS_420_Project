package mvc.filters.boundary.box;

import java.awt.BorderLayout;

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
		
		model.boxPanel.add(model.latitudeLabel, grid.set(  x,   y, 2, 1));
		model.boxPanel.add(model.northLabel   , grid.set(  x, ++y));
		model.boxPanel.add(model.northField   , grid.set(++x,   y));
		model.boxPanel.add(model.southLabel   , grid.set(--x, ++y));
		model.boxPanel.add(model.southField   , grid.set(++x,   y));
		
		model.boxPanel.add(model.longitudeLabel, grid.set(--x, ++y, 2, 1));
		model.boxPanel.add(model.eastLabel     , grid.set(  x, ++y));
		model.boxPanel.add(model.eastField     , grid.set(++x,   y));
		model.boxPanel.add(model.westLabel     , grid.set(--x, ++y));
		model.boxPanel.add(model.westField     , grid.set(++x,   y));
		
		
		model.rootPanel.add(model.boxPanel, BorderLayout.CENTER);
	}
	
	
}
