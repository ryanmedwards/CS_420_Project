package mvc.main.process.filters;

import java.awt.BorderLayout;

import guiutil.Grid;
import mvc.View;

public class FilterView
{
	public FilterView(final FilterModel model)
	{
		final Grid grid = new Grid().initAnchor(Grid.WEST).initInset(10);
		
		int x = 0;
		int y = 0;
		
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
	

	
	protected void removeFromIncluded(final FilterModel model, final Filter option)
	{
		model.includedFiltersPanel.remove(option.removeButton);
		model.includedFiltersPanel.remove(option.upButton);
		model.includedFiltersPanel.remove(option.downButton);
		model.includedFiltersPanel.remove(option.label);
		model.includedFiltersPanel.remove(option.editButton);
		refresh(model);
	}
	
	protected void removeFromExcluded(final FilterModel model, final Filter option)
	{
		model.excludedFiltersPanel.remove(option.addButton);
		model.excludedFiltersPanel.remove(option.label);
		refresh(model);
	}
	
	
	protected void addToExcluded(final FilterModel model, final Filter option)
	{	
		final int row = option.row;
		
		final Grid grid = new Grid().initAnchor(Grid.WEST).initInset(10);
		
		int x = 0;
		
		model.excludedFiltersPanel.add(option.addButton, grid.set(  x, row));
		model.excludedFiltersPanel.add(option.label    , grid.set(++x, row));
		
		refresh(model);
	}
	
	protected void repaintIncluded(final FilterModel model)
	{
		model.includedFiltersPanel.removeAll();
		
		int i = 0;
		
		for(final Filter option: Filter.getIncludedList())
		{
			addToIncluded(model, option, i++);
		}
	}
	
	protected void addToIncluded(final FilterModel model, final Filter option, final int row)
	{
		final Grid grid = new Grid().initAnchor(Grid.WEST);
		
		int x = 0;

		grid.setInset(10, 5, 10, 10);
		model.includedFiltersPanel.add(option.removeButton, grid.set(  x, row));
		
		grid.setInset(10, 15, 10, 5);
		model.includedFiltersPanel.add(option.upButton    , grid.set(++x, row));
		
		grid.setInset(10, 5, 10, 15);
		model.includedFiltersPanel.add(option.downButton  , grid.set(++x, row));
		
		grid.setInset(10, 10, 10, 15);
		model.includedFiltersPanel.add(option.label       , grid.set(++x, row));
		
		grid.setInset(10, 10, 10, 5);
		model.includedFiltersPanel.add(option.editButton  , grid.set(++x, row));
		
		refresh(model);
	}
	
	private void refresh(final FilterModel model)
	{
		model.excludedFiltersPanel.revalidate();
		model.excludedFiltersPanel.repaint();
		
		model.includedFiltersPanel.revalidate();
		model.includedFiltersPanel.repaint();
	}
	
}
