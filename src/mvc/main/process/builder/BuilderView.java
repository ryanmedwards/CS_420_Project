package mvc.main.process.builder;

import java.awt.BorderLayout;

import guiutil.Grid;
import mvc.filters.Filter;

public class BuilderView 
{
	private final BuilderModel model;
	
	protected BuilderView(final BuilderModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{		
		model.excludedPanel.add(model.excludedFiltersPanel, BorderLayout.NORTH);
		model.includedPanel.add(model.includedFiltersPanel, BorderLayout.NORTH);
		
		model.excludedHeaderPanel.add(model.optionsLabel);
		model.includedHeaderPanel.add(model.processLabel);
		
		
		model.westPanel.add(model.excludedHeaderPanel, BorderLayout.NORTH);
		model.westPanel.add(model.excludedPanel, BorderLayout.CENTER);
		
		
		model.centerPanel.add(model.includedHeaderPanel, BorderLayout.NORTH);
		model.centerPanel.add(model.includedPanel, BorderLayout.CENTER);
		
		
		model.optionPanel.add(model.westPanel, BorderLayout.WEST);
		model.optionPanel.add(model.centerPanel, BorderLayout.CENTER);	
		
		model.rootPanel.add(model.optionPanel, BorderLayout.CENTER);
	}
	
	protected void removeFromExcluded(final Filter filter)
	{
		model.excludedFiltersPanel.remove(filter.excludedPanel);
	}
	
	protected void removeFromIncluded(final Filter filter)
	{
		model.includedFiltersPanel.remove(filter.includedPanel);
	}	
	
	public void addToExcluded(final Filter filter)
	{
		final Grid grid = new Grid().initAnchor(Grid.WEST).initInset(3);
		
		final int y = filter.row;
		
		model.excludedFiltersPanel.add(filter.excludedPanel, grid.set(0, y));
		
		refresh();
	}
	
	protected void addToIncluded(final Filter filter)
	{
		model.includedFiltersPanel.add(filter.includedPanel);
		
		refresh();
	}
	

	
	
	private void refresh()
	{
		model.excludedFiltersPanel.revalidate();
		model.excludedFiltersPanel.repaint();
		
		model.includedFiltersPanel.revalidate();
		model.includedFiltersPanel.repaint();
	}
}
