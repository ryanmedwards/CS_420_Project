package mvc.main.process.feedback;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.custom.RootPanel;
import components.text.TextArea;

public class FeedbackModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();

	
	
	public final JTextArea localLog = new TextArea().initEditable(false).get();
	

	
	
	
	
	
	
	
	
	
	public final FeedbackView view = new FeedbackView(this);
	public final FeedbackController control = new FeedbackController(this);
}
