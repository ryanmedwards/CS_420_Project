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
		
		//model.startPanel.add(model.startLabel         , grid.set(x,   y));
		
		
		
		
		grid.setAnchor(Grid.WEST);
		model.infoPanel.add(model.startLabel, grid.set(  x, ++y));
		model.infoPanel.add(model.startDate , grid.set(++x,   y));
		model.infoPanel.add(model.startTime , grid.set(++x,   y));
		
		x = 0;
		
		model.infoPanel.add(model.stopLabel, grid.set(  x, ++y));
		model.infoPanel.add(model.stopDate , grid.set(++x,   y));
		model.infoPanel.add(model.stopTime , grid.set(++x,   y));


		
		
		x = 0;
		y = 0;
		
		model.startPanel.add(model.startDate, grid.set(x, y));
		model.startPanel.add(model.startTime, grid.set(++x, y));
		
		
		x = 0;
		y = 0;
		
		model.stopPanel.add(model.stopDate, grid.set(x,   y));
		model.stopPanel.add(model.stopTime, grid.set(++x, y));
		
		
		
		model.tabPanel.addTab("Start", model.startPanel);
		model.tabPanel.addTab("Stop", model.stopPanel);
		
		model.rootPanel.add(model.infoPanel, BorderLayout.NORTH);
		model.rootPanel.add(model.tabPanel, BorderLayout.CENTER);

	}
}
