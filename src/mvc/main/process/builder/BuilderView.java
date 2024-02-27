package mvc.main.process.builder;

import java.awt.BorderLayout;

import components.buttons.Button;
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

		model.headerPanel.add(model.processLabel);
		
		
		model.footerPanel.add(model.addButton);
		
		model.rootPanel.add(model.headerPanel, BorderLayout.NORTH);
		
		
		model.rootPanel.add(model.filtersPanel, BorderLayout.CENTER);
		
		
		model.rootPanel.add(model.footerPanel, BorderLayout.SOUTH);		
		

	}
	
	

}
