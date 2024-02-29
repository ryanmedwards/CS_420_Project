package mvc.main;

import java.awt.BorderLayout;





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

		model.rootPanel.add(model.menuPanel, BorderLayout.CENTER);
	}
}
