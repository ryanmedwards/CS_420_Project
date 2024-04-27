package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.Label;
import components.custom.RootPanel;
import components.text.TextArea;

public class FeedbackModel 
{
	
	// This could probably be moved to ProcessModel
	
	public final JPanel rootPanel = new RootPanel().get();

	
	protected final JPanel labelPanel = new Label("Log").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 10, 10);
	
	protected final JTextArea localLog = new TextArea().initEditable(false).get();
	protected final JScrollPane logScrollPane = new JScrollPane(localLog);
	
	
	protected FeedbackModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		rootPanel.add(labelPanel, BorderLayout.NORTH);
		rootPanel.add(logScrollPane, BorderLayout.CENTER);
	}
	
	private void assign()
	{

	}
	
	public JTextArea getLog()
	{
		return localLog;
	}
}
