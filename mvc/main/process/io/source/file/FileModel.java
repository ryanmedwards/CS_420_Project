package mvc.main.process.io.source.file;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.custom.RootPanel;
import components.text.TextField;

public class FileModel 
{
	public final JPanel rootPanel = new RootPanel().get();
	
	
	
	
	protected final JPanel northPanel = new JPanel(new BorderLayout());
	
	
	protected final JPanel filePanel = new JPanel(new GridBagLayout());
	
	protected String filepath = "";
	
	protected final JLabel fileLabel = new JLabel("File:");
	
	protected final JTextField fileField = new TextField().initEditable(false).initColumns(30).get();
	
	protected final JButton selectButton = new JButton("Select");
	
	protected final JFileChooser fileChooser = new JFileChooser();
	
	
	public final FileView view = new FileView(this);
	public final FileController control = new FileController(this);
}
