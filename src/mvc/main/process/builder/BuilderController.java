package mvc.main.process.builder;

import mvc.filters.Filter;


public class BuilderController
{
	private final BuilderModel model;
	
	protected BuilderController(final BuilderModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{

	}
	
	public void add(final Filter filter)
	{
		final boolean add = filter.launch();
		
		if(add)
		{
			model.view.removeFromExcluded(filter);
			model.view.addToIncluded(filter);
		}
	}
	
	public void remove(final Filter filter)
	{
		model.view.removeFromIncluded(filter);
		model.view.addToExcluded(filter);
		filter.reset();
	}
	
	/**
	 * Allow user to edit the Filter's settings. 
	 * <p>
	 * The settings are saved or reverted based on result of the JDialog.
	 * 
	 * @param filter The filter to be edited.
	 */
	public void edit(final Filter filter)
	{
		filter.copy();
		
		final boolean save = filter.launch();
		
		if( ! save )
		{
			filter.revert();
		}
	}
}
