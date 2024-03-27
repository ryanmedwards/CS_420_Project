package mvc.main.process.io.source.local;

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
		
		model.labelPanel.add(model.tablesLabel);
		
		model.togglePanel.add(model.tablesBar, BorderLayout.NORTH);
		
		model.tablesPanel.add(model.labelPanel, BorderLayout.NORTH);
		model.tablesPanel.add(model.togglePanel, BorderLayout.CENTER);
		
		
		
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.NORTHWEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		model.descLabelPanel.add(model.descLabel);
		
		model.descPanel.add(model.sourceLabel, grid.set(x, y));
		model.descPanel.add(model.sourceField, grid.set(++x, y));
		
		model.descPanel.add(model.tableLabel, grid.set(--x, ++y));
		model.descPanel.add(model.tableField, grid.set(++x, y));
		
		model.descPanel.add(model.countLabel, grid.set(--x, ++y));
		model.descPanel.add(model.countField, grid.set(++x, y));
		
		model.descNPanel.add(model.descPanel, BorderLayout.NORTH);
		
		model.decriptionPanel.add(model.descLabelPanel, BorderLayout.NORTH);
		model.decriptionPanel.add(model.descNPanel, BorderLayout.CENTER);
		
		
		
		
		model.historyLabelPanel.add(model.historyLabel);
		
		model.historyPanel.add(model.historyLabelPanel, BorderLayout.NORTH);
		model.historyPanel.add(model.historyScrollPane, BorderLayout.CENTER);
		
		
		
		
		
		
		model.rootPanel.add(model.tablesPanel, BorderLayout.WEST);
		model.rootPanel.add(model.historyPanel, BorderLayout.CENTER);
		model.rootPanel.add(model.decriptionPanel, BorderLayout.EAST);
	}
}
