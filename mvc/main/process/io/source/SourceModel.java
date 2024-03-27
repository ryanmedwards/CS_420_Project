package mvc.main.process.io.source;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import application.Application;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import components.custom.dialog.DialogMode;
import mvc.main.process.io.source.file.FileModel;
import mvc.main.process.io.source.local.LocalModel;
import mvc.main.process.io.source.opensky.OpenSkyModel;

public class SourceModel 
{

	protected final JPanel rootPanel = new JPanel(new BorderLayout());
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	protected Source savedSource = Source.OPENSKY_NETWORK;
	protected String savedTable = "state_vectors_data4";

	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Source Selector", true).initMode(DialogMode.CANCEL_CONFIRM);

	
	protected final OpenSkyModel openSkyModel = new OpenSkyModel();
	protected final LocalModel localModel = new LocalModel();
	protected final FileModel fileModel = new FileModel();

	
	public final SourceView       view    = new SourceView(this);
	public final SourceController control = new SourceController(this);
}
