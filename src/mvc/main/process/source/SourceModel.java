package mvc.main.process.source;

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
import components.TextField;
import components.custom.Dialog;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import mvc.main.process.source.local.LocalModel;
import mvc.main.process.source.opensky.OpenSkyModel;

public class SourceModel 
{
	
	
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	
	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Source Selector", true);
	
	
	protected final JPanel sourcePanel = new JPanel(new GridBagLayout());
	
	protected final JLabel sourceLabel = new JLabel("Source:");
	protected final JLabel tableLabel  = new JLabel("Table:");
	
	protected final JTextField sourceField = new TextField().initEditable(false).initColumns(15).get();
	protected final JTextField tableField  = new TextField().initEditable(false).initColumns(15).get();
	
	protected final JButton selectButton = new JButton("Select");
//	
	
	
	protected final JPanel dialogPanel = new RootPanel().get();
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	
	
	protected final OpenSkyModel openSkyModel = new OpenSkyModel();
	protected final LocalModel localModel = new LocalModel();
	
	
	
	
	public final SourceView view = new SourceView(this);
	public final SourceController control = new SourceController(this);
}
