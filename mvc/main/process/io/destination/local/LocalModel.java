package mvc.main.process.io.destination.local;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.custom.RootPanel;
import components.text.FormatField;
import components.text.TextField;

public class LocalModel
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	public final JPanel northPanel = new JPanel(new BorderLayout());
	
	protected final JPanel localPanel = new JPanel(new GridBagLayout());
	
	
	
	protected final JLabel destinationLabel = new JLabel("Destination:");
	protected final JTextField destinationField = new TextField()
												  .initText("Local Database")
											   	  .initEditable(false)
											   	  .initColumns(15)
											   	  .get();
	
	protected final JLabel tableLabel = new JLabel("Table:");
	protected final JFormattedTextField tableField = new FormatField()
											   	         .initColumns(15)
											   	         .get();
	
	
	
	
	public final LocalView view = new LocalView(this);
	public final LocalController control = new LocalController(this);
}
