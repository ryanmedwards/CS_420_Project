package mvc.main.process.source;

import java.awt.BorderLayout;
import java.awt.Dimension;

import guiutil.Grid;

public class SourceView 
{
	private final SourceModel model;
	
	protected SourceView(final SourceModel model)
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
		
		model.sourcePanel.add(model.sourceLabel, grid.set(  x, y));
		model.sourcePanel.add(model.sourceField, grid.set(++x, y));
		
		model.sourcePanel.add(model.tableLabel, grid.set(++x, y));
		model.sourcePanel.add(model.tableField, grid.set(++x,   y));
		
		model.sourcePanel.add(model.selectButton, grid.set(++x, y));
		
		
		model.dialog.setPreferredSize(new Dimension(1000, 500));
		
		model.rootPanel.add(model.sourcePanel, BorderLayout.CENTER);
		

		model.tabPane.addTab("OpenSky-Network", model.openSkyModel.rootPanel);
		model.tabPane.addTab("Local Database", model.localModel.rootPanel);
		
		model.dialogPanel.add(model.tabPane, BorderLayout.CENTER);
		
		model.dialog.addContent(model.dialogPanel);
		
		
		
	}
}
