package mvc.main;

import java.awt.BorderLayout;

import mvc.Model;
import mvc.View;
import mvc.main.collect.CollectModel;



public class MainView
{
	public MainView(final MainModel model)
	{
		model.menuPanel.addTab("Process", model.processModel.rootPanel);
		model.menuPanel.addTab("Collect", model.collectModel.rootPanel);


		model.rootPanel.add(model.menuPanel, BorderLayout.CENTER);
	}
}
