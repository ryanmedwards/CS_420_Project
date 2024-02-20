package mvc.main.process.filters;

import mvc.Controller;

public class FilterController 
{
	public FilterController(final FilterModel model)
	{
		
	}
	
	protected void add(final FilterModel model, final Filter<?> option)
	{
		final boolean add = option.launch();
		
		if(add)
		{
			Filter.include(option);
			model.view.removeFromExcluded(model, option);
			model.view.repaintIncluded(model);
		}
	}
	
	protected void remove(final FilterModel model, final Filter<?> option)
	{
		Filter.exclude(option);
		option.reset();
		model.view.removeFromIncluded(model, option);
		model.view.addToExcluded(model, option);
	}
	
	protected void up(final FilterModel model, final Filter<?> option)
	{
		Filter.moveUp(option);
		model.view.repaintIncluded(model);
	}
	
	protected void down(final FilterModel model, final Filter<?> option)
	{
		Filter.moveDown(option);
		model.view.repaintIncluded(model);
	}
	
	protected void edit(final FilterModel model, final Filter<?> option)
	{
		option.launch();
	}
}
