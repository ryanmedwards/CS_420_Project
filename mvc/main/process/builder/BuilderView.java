package mvc.main.process.builder;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;

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
		
		model.addPanel.add(model.addButton);
		
		model.bufferPanel.add(model.filtersPanel, BorderLayout.NORTH);
		
		model.headerPanel.add(model.processLabel);
		
		model.rootPanel.add(model.headerPanel, BorderLayout.NORTH);
		
		
		model.rootPanel.add(model.bufferPanel, BorderLayout.CENTER);

		refresh();

	}
	
	protected void refresh()
	{
		model.filtersPanel.removeAll();
		
		for(final BuilderPanel panel: model.filterPanels)
		{
			model.filtersPanel.add(panel);
		}
		
		model.filtersPanel.add(model.addPanel);
		
		model.filtersPanel.revalidate();
		model.filtersPanel.repaint();
	}
	
	

}
