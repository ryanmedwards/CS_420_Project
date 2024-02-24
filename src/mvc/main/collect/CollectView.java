package mvc.main.collect;

import java.awt.BorderLayout;

import guiutil.Grid;


public class CollectView
{
	private final CollectModel model;
	
	protected CollectView(final CollectModel model)
	{
		this.model = model;
	}

	protected void draw()
	{
		model.buttonPanel.add(model.explainButton);
		model.buttonPanel.add(model.countButton);
		model.buttonPanel.add(model.executeButton);
		
		
		model.tabPanel.addTab("Query" , model.queryTextArea);
		model.tabPanel.addTab("Output", model.outputTextArea);
		model.tabPanel.addTab("Log"   , model.logTextArea);

		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		int x = 0;
		int y = 0;
		
		
		
		grid.setAnchor(Grid.CENTER);
		model.filtersPanel.add(model.dateTimeLabel, grid.set(x, y, 3, 1));
		
		grid.setAnchor(Grid.WEST);
		model.filtersPanel.add(model.startLabel        , grid.set(  x, ++y));
		model.filtersPanel.add(model.startDate, grid.set(++x,   y));
		model.filtersPanel.add(model.startTime, grid.set(++x,   y));
		
		x = 0;
		
		model.filtersPanel.add(model.stopLabel        , grid.set(  x, ++y));
		model.filtersPanel.add(model.stopDate, grid.set(++x,   y));
		model.filtersPanel.add(model.stopTime, grid.set(++x,   y));
		
		x = 0;

		grid.setAnchor(Grid.CENTER);
		model.filtersPanel.add(model.boundaryLabel, grid.set(x, ++y, 3, 1));
		
		grid.setAnchor(Grid.WEST);
		model.filtersPanel.add(model.boundsLabel      , grid.set(  x, ++y));
		model.filtersPanel.add(model.boundaryTextField, grid.set(++x,   y, 2, 1));
		
		model.westPanel.add(model.filtersScrollPanel, BorderLayout.NORTH);
		
		model.rootPanel.add(model.westPanel, BorderLayout.WEST);
		model.rootPanel.add(model.buttonPanel, BorderLayout.SOUTH);
		model.rootPanel.add(model.tabPanel   , BorderLayout.CENTER);
	}
	
	
	
	
}
