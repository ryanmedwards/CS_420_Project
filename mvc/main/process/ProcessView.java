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
		
		model.ioPanel.add(model.ioModel.rootPanel);
		
		model.centerPanel.add(model.ioPanel, BorderLayout.NORTH);
		model.centerPanel.add(model.feedbackModel.rootPanel, BorderLayout.CENTER);
		

		model.southPanel.add(model.startButton);
		
		
		model.rootPanel.add(model.westPanel, BorderLayout.WEST);
		model.rootPanel.add(model.centerPanel, BorderLayout.CENTER);
		model.rootPanel.add(model.southPanel, BorderLayout.SOUTH);
	}
}
