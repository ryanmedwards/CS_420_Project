package mvc.filters.datetime;

import java.awt.BorderLayout;

import guiutil.Grid;

public class DateTimeView 
{
	private final DateTimeModel model;
	
	protected DateTimeView(final DateTimeModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.NORTH);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		
		model.controlPanel.add(model.dateLabel, grid.set(  x, y));
		model.controlPanel.add(model.dateField, grid.set(++x, y));
		
		model.controlPanel.add(model.timeLabel, grid.set(++x, y));
		model.controlPanel.add(model.timeField, grid.set(++x, y));
		
		model.controlPanel.add(model.durationLabel, grid.set(++x, y));
		model.controlPanel.add(model.durationField, grid.set(++x, y));
		
		x = 0;
		y = 1;
		
		grid.setAnchor(Grid.NORTHEAST);
		grid.setInset(30, 10, 10, 10);
		
		model.controlPanel.add(model.dateSelector   , grid.set(  x, y, 2, 1)); ++x;
		model.controlPanel.add(model.timeSelector   , grid.set(++x, y, 2, 1)); ++x;
		model.controlPanel.add(model.durationSpinner, grid.set(++x, y, 2, 1));
		
		
		model.rootPanel.add(model.controlPanel, BorderLayout.NORTH);

	}
}
