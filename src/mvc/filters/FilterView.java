package mvc.filters;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class FilterView 
{
	private final FilterModel model;
	
	protected FilterView(final FilterModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		
		model.rootPanel.add(model.tabPane, BorderLayout.CENTER);
		
		model.tabPane.addTab("Home", model.homePanel);
		model.tabPane.addTab("", new JPanel());
		
		model.tabPane.setEnabledAt(1, false);
		
		for(final Filter filter: model.filters)
		{
			model.tabPane.addTab(filter.name, filter.getRootPanel());
			System.out.println(filter.getID() + " " +  filter.name);
		}
		
		model.tabPane.setSelectedIndex(0);
		
		
		model.dialog.addContent(model.rootPanel);
	}
}
