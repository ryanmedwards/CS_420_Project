package mvc.main.process.io.destination.local;

import java.awt.BorderLayout;

import guiutil.Grid;

public class LocalView 
{
	private final LocalModel model;
	
	protected LocalView(final LocalModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(5, 5, 5, 5);
		
		int x = 0;
		int y = 0;
		
		model.localPanel.add(model.destinationLabel, grid.set(  x, y));
		model.localPanel.add(model.destinationField, grid.set(++x, y));
		
		model.localPanel.add(model.tableLabel, grid.set(--x, ++y));
		model.localPanel.add(model.tableField, grid.set(++x, y));
		
		model.northPanel.add(model.localPanel, BorderLayout.WEST);
		
		model.rootPanel.add(model.northPanel, BorderLayout.NORTH);
		
		
	}
}
