package mvc.main.process.io;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import application.Application;
import components.custom.Dialog;
import components.custom.Dialog.DialogMode;
import components.text.TextField;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import mvc.main.process.source.local.LocalModel;
import mvc.main.process.source.opensky.OpenSkyModel;

public class IOModel 
{
	
	
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	
	
	protected final SourceDialog sourceDialog = new SourceDialog(Application.getApp(), "Source Selector", true);
	
	protected final DestinationDialog destinationDialog = new DestinationDialog(Application.getApp(), "Destination Selector", true);
	
	protected final JPanel ioPanel = new JPanel(new GridBagLayout());
	
	protected final JLabel sourceLabel = new JLabel("Source:");
	protected final JLabel sourceTableLabel  = new JLabel("Table:");
	
	protected final JTextField sourceField = new TextField().initEditable(false).initColumns(15).get();
	protected final JTextField sourceTableField  = new TextField().initEditable(false).initColumns(15).get();
	
	protected final JButton sourceSelectButton = new JButton("Select");
//	
	
	protected final JLabel destinationLabel = new JLabel("Destination:");
	protected final JLabel destinationTableLabel  = new JLabel("Table:");
	
	protected final JTextField destinationField = new TextField().initEditable(false).initColumns(15).get();
	protected final JTextField destinationTableField  = new TextField().initEditable(false).initColumns(15).get();
	
	protected final JButton destinationSelectButton = new JButton("Select");

	
	
	
	
	public final IOView view = new IOView(this);
	public final IOController control = new IOController(this);
}
