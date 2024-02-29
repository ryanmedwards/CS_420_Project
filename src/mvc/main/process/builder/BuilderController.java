package mvc.main.process.builder;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.buttons.Button;
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
				
				model.filterPanels.add(new BuilderPanel(model, filter));
				
				model.view.refresh();
			}
		});
	}
	
	protected void removeFilter(final Filter filter)
	{
		final int id = filter.getID();
		
		for(int i = 0; i < model.filterPanels.size(); ++i)
		{
			if(model.filterPanels.get(i).filter.getID() == id)
			{
				model.filterPanels.remove(i);
				
				break;
			}
		}
		
		model.view.refresh();
	}

	
	
}


