package mvc.main.process;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;


import components.Panel;
import components.custom.RootPanel;
import components.text.TextArea;
import mvc.main.process.builder.BuilderModel;
import mvc.main.process.source.SourceModel;



public class ProcessModel
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel westPanel = new JPanel(new BorderLayout());
	protected final JPanel centerPanel = new JPanel(new BorderLayout());
	
	protected final JTextArea processTextArea = new TextArea().initEditable(false).get();
	
	public final BuilderModel builderModel = new BuilderModel();
	public final SourceModel sourceModel = new SourceModel();
	
	public final ProcessView       view    = new ProcessView(this);
	public final ProcessController control = new ProcessController(this);
}
