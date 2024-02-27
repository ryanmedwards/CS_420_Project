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
			if(model.tabPane.getSelectedIndex() == 0)
			{
				model.dialog.setMode(DialogMode.CLOSE);
			}
			else
			{
				model.dialog.setMode(DialogMode.CANCEL_CONFIRM);
			}
		});
	}
	
	public void enable(final Filter filter)
	{
		model.tabPane.setEnabledAt(filter.getID(), true);
	}
	
	public boolean add()
	{
		final boolean add = model.dialog.launch();
		
		if(add)
		{
			model.selectedFilter = model.tabPane.getSelectedIndex();
			
			model.tabPane.setEnabledAt(model.selectedFilter, false);
			
			model.tabPane.setSelectedIndex(0);
		}
		
		return add;
	}
	
	public Filter getSelectedFilter()
	{
		return model.filters[model.selectedFilter];
	}
	
	
	public void edit(final Filter filter)
	{
		final int index = filter.getID();
		
		disableTabsExcept(index);
		
		model.tabPane.setSelectedIndex(index);
		
		filter.copy();
		
		final boolean save = model.dialog.launch();
		
		if( ! save )
		{
			filter.revert();
		}
		
		enableTabs();
	}

	
	
	private void disableTabsExcept(final int index)
	{
		for(int i = 0; i < index; ++i)
		{
			model.tabPane.setEnabledAt(i, false);
		}
		
		for(int i = index + 1; i < model.tabPane.getTabCount(); ++i)
		{
			model.tabPane.setEnabledAt(i, false);
		}
	}
	
	private void enableTabs()
	{
		for(int i = 2; i < model.tabPane.getTabCount(); ++i)
		{
			model.tabPane.setEnabledAt(i, model.filters[i - 2].isEnabled());
		}
	}
}
