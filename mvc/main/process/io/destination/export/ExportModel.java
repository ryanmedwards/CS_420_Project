package mvc.main.process.io.destination.export;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.ComboBox;
import components.Panel;
import components.custom.RootPanel;
import components.text.FormatField;
import components.text.TextField;

public class ExportModel 
{
	
	
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final JPanel northPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground())).get();
	
	
	protected final JPanel contentPanel = new JPanel(new GridBagLayout());
	
	
	
	protected final JLabel formatLabel = new JLabel("Format:");
	protected final JComboBox<String> formatBox = new ComboBox<String>().initItems("CSV").get();
	
	
	
	protected final JLabel pathLabel = new JLabel("Folder:");
	
	protected final JTextField pathField = new TextField().initEditable(false).initColumns(25).get();
	
	protected final JButton pathButton = new JButton("Select");

	
	
	protected final JLabel fileLabel = new JLabel("File Name:");
	
	protected final JFormattedTextField fileField = new FormatField().initColumns(15).get();
	
	
	
	
	

	
	
	public final ExportView view = new ExportView(this);
	public final ExportController control = new ExportController(this);
}
