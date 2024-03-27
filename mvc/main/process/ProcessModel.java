package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import components.Panel;
import components.custom.RootPanel;
import components.text.TextArea;
import mvc.main.process.builder.BuilderModel;
import mvc.main.process.feedback.FeedbackModel;
import mvc.main.process.io.IOModel;



public class ProcessModel
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel westPanel = new JPanel(new BorderLayout());
	protected final JPanel centerPanel = new JPanel(new BorderLayout());
	
	protected final JPanel ioPanel = new JPanel(new GridLayout(0, 1, 5, 5));

	
	protected final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	protected final JButton startButton = new JButton("Start");
	
	
	
	public final BuilderModel builderModel = new BuilderModel();
	public final IOModel ioModel = new IOModel();
	public final FeedbackModel feedbackModel = new FeedbackModel();
	
	public final ProcessView       view    = new ProcessView(this);
	public final ProcessController control = new ProcessController(this);
}
