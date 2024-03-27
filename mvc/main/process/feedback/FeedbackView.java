package mvc.main.process.feedback;

import java.awt.BorderLayout;

public class FeedbackView 
{
	private final FeedbackModel model;
	
	protected FeedbackView(final FeedbackModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		model.rootPanel.add(model.localLog, BorderLayout.CENTER);
	}
}
