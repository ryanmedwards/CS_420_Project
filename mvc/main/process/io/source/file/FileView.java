package mvc.main.process.io.source.file;

import java.awt.BorderLayout;

import guiutil.Grid;

public class FileView
{
	private final FileModel model;
	
	protected FileView(final FileModel model)
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
		
		model.filePanel.add(model.fileLabel   , grid.set(  x, y));
		model.filePanel.add(model.fileField   , grid.set(++x, y));
		model.filePanel.add(model.selectButton, grid.set(++x, y));
		
		
		model.northPanel.add(model.filePanel, BorderLayout.WEST);
		
		model.rootPanel.add(model.northPanel, BorderLayout.NORTH);
		
	}
}
