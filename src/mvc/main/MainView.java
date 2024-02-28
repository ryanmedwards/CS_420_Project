package mvc.main;

import java.awt.BorderLayout;


import mvc.main.collect.CollectModel;



public class MainView
{
	private final MainModel model;
	
	protected MainView(final MainModel model)
	{
		this.model = model;
	}
	
	protected void draw()
	{
		model.menuPanel.addTab("Process", model.processModel.rootPanel);
		model.menuPanel.addTab("Collect", model.collectModel.rootPanel);

		model.rootPanel.add(model.menuPanel, BorderLayout.CENTER);
	}
}
