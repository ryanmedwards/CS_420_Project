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

		for(final Filter filter: model.filters)
		{
			model.tabPane.addTab(filter.name, filter.getRootPanel());
		}
		
		model.tabPane.addTab("", new JPanel());
		model.tabPane.addTab("About", model.aboutPanel);
		
		model.tabPane.setEnabledAt(model.tabPane.getTabCount() - 2, false);
		
		model.tabPane.setSelectedIndex(model.tabPane.getTabCount() - 1);
		
		
		model.dialog.addContent(model.rootPanel);
	}
}
