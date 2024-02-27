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
		model.addButton.addActionListener(e ->
		{
			final boolean add = model.filterModel.control.add();
			
			if(add)
			{
				final Filter filter = model.filterModel.control.getSelectedFilter();
				
				new FilterPanel(model.filtersPanel, filter, true);
			}
		});
	}
	

	
	
}
