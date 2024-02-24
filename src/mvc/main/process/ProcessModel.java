package mvc.main.process;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import components.Button;
import components.DragAndDrop;
import components.Panel;
import components.TextArea;
import mvc.main.process.builder.BuilderModel;

public class ProcessModel
{
	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	protected final Panel westPanel = new Panel(new BorderLayout());
	
	protected final TextArea processTextArea = new TextArea().initEditable(false);
	
	public final BuilderModel builderModel = new BuilderModel();

	public final ProcessView       view    = new ProcessView(this);
	public final ProcessController control = new ProcessController(this);
}
