package mvc.main.process.io.destination;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import application.Application;
import components.custom.dialog.Dialog;
import components.custom.dialog.DialogMode;
import mvc.main.process.io.destination.export.ExportModel;
import mvc.main.process.io.destination.local.LocalModel;

public class DestinationModel 
{
	
	
	
	protected final JPanel rootPanel = new JPanel(new BorderLayout());
	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Destination Selector", true).initMode(DialogMode.CANCEL_CONFIRM);
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	
	
	protected final LocalModel localModel = new LocalModel();  
	
	protected final ExportModel exportModel = new ExportModel();
	
	
	
	public final DestinationView       view    = new DestinationView(this);
	public final DestinationController control = new DestinationController(this);
}
