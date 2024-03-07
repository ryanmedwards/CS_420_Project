package mvc.main.process.io;

import java.awt.BorderLayout;
import java.awt.Dimension;

import guiutil.Grid;

public class IOView 
{
	private final IOModel model;
	
	protected IOView(final IOModel model)
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
		
		model.ioPanel.add(model.sourceLabel, grid.set(  x, y));
		model.ioPanel.add(model.sourceField, grid.set(++x, y));
		
		//model.ioPanel.add(model.sourceTableLabel, grid.set(++x, y));
		model.ioPanel.add(model.sourceTableField, grid.set(++x,   y));
		
		model.ioPanel.add(model.sourceSelectButton, grid.set(++x, y));
		
		
		
		x = 0;
		y = 1;
		
		model.ioPanel.add(model.destinationLabel, grid.set(  x, y));
		model.ioPanel.add(model.destinationField, grid.set(++x, y));
		
		//model.ioPanel.add(model.destinationTableLabel, grid.set(++x, y));
		model.ioPanel.add(model.destinationTableField, grid.set(++x,   y));
		
		model.ioPanel.add(model.destinationSelectButton, grid.set(++x, y));
		
		
		
		
		
		

		model.rootPanel.add(model.ioPanel, BorderLayout.CENTER);
		


		
		
		
	}
}
