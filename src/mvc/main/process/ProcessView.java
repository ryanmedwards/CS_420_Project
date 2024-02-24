package mvc.main.process;

import java.awt.BorderLayout;


public class ProcessView
{
	private final ProcessModel model;
	
	public ProcessView(final ProcessModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		model.westPanel.add(model.builderModel.rootPanel, BorderLayout.WEST);
		
		
		model.rootPanel.add(model.processTextArea, BorderLayout.CENTER);
		model.rootPanel.add(model.westPanel, BorderLayout.WEST);
	}
}
