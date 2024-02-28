package mvc.filters;

import java.util.List;

import components.custom.Dialog.DialogMode;

public class FilterController 
{
	private final FilterModel model;
	
	protected FilterController(final FilterModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.tabPane.addChangeListener(e ->
		{
			if(model.tabPane.getSelectedIndex() == model.tabPane.getTabCount() - 1)
			{
				model.dialog.setMode(DialogMode.CLOSE);
			}
			else
			{
				model.dialog.setMode(DialogMode.CANCEL_CONFIRM);
			}
		});
		
		model.dialog.setMode(DialogMode.CLOSE);
	}
	

	public boolean add()
	{
		final boolean add = model.dialog.launch();
		
		if(add)
		{
			model.selectedFilter = model.tabPane.getSelectedIndex();
			
			model.tabPane.setEnabledAt(model.selectedFilter, false);
			
			model.tabPane.setSelectedIndex(model.tabPane.getTabCount() - 1);
			
			getSelectedFilter().setEnabled(false);
		}
		
		return add;
	}
	
	public Filter getSelectedFilter()
	{
		return model.filters[model.selectedFilter];
	}
	
	
	protected void edit(final Filter filter)
	{
		final int index = filter.getID();
		
		disableTabs();
		
		model.tabPane.setEnabledAt(index, true);
		
		model.tabPane.setSelectedIndex(index);
		
		filter.copy();
		
		final boolean save = model.dialog.launch();
		
		if( ! save )
		{
			filter.revert();
		}
		
		model.tabPane.setSelectedIndex(model.tabPane.getTabCount() - 1);
		
		enableTabs();
	}
	
	
	/**
	 * Adds the filter back to the available filters shown by the filter dialog.
	 * <br>
	 * Called when filter is no longer wanted or needed.
	 * 
	 * @param filter 
	 */
	protected void remove(final Filter filter)
	{
		model.tabPane.setEnabledAt(filter.getID(), true);
	}

	
	
	private void disableTabs()
	{
		for(int i = 0; i < model.tabPane.getTabCount(); ++i)
		{
			model.tabPane.setEnabledAt(i, false);
		}
	}
	
	private void enableTabs()
	{
		for(int i = 0; i < model.filters.length; ++i)
		{
			model.tabPane.setEnabledAt(i, model.filters[i].isEnabled());
		}
		
		model.tabPane.setEnabledAt(model.tabPane.getTabCount() - 1, true);
	}
}
