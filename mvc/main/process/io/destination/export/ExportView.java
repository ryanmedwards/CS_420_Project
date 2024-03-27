package mvc.main.process.io.destination.export;

import java.awt.BorderLayout;

import guiutil.Grid;

public class ExportView 
{
	private final ExportModel model;
	
	protected ExportView(final ExportModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		
		
		int x = 0;
		int y = 0;
		
		model.contentPanel.add(model.pathLabel,  grid.set(  x, y));
		model.contentPanel.add(model.pathField,  grid.set(++x, y));
		model.contentPanel.add(model.pathButton, grid.set(++x, y));
		
		
		x = 0;
		y = 1;
		
		model.contentPanel.add(model.fileLabel, grid.set(  x, y));
		model.contentPanel.add(model.fileField, grid.set(++x, y));
		
		
		x = 0;
		y = 2;
		
		model.contentPanel.add(model.formatLabel, grid.set(  x, y));
		model.contentPanel.add(model.formatBox,   grid.set(++x, y));
		
		
		
		model.northPanel.add(model.contentPanel, BorderLayout.WEST);
		
		
		model.rootPanel.add(model.northPanel, BorderLayout.NORTH);
		
		
	}
}
