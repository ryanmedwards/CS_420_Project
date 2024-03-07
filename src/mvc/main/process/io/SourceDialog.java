package mvc.main.process.io;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import components.custom.Dialog;
import components.custom.RootPanel;
import mvc.main.process.source.local.LocalModel;
import mvc.main.process.source.opensky.OpenSkyModel;

public class SourceDialog extends Dialog 
{
	
	
	protected final JPanel dialogPanel = new RootPanel().get();
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	protected String savedSource = "";
	protected String savedTable = "";
	
	
	protected final OpenSkyModel openSkyModel = new OpenSkyModel();
	protected final LocalModel localModel = new LocalModel();

	public SourceDialog(final JFrame parent, final String title, final boolean setModal) 
	{
		super(parent, title, setModal);
		
		this.setMode(DialogMode.CANCEL_CONFIRM);
		this.setPreferredSize(new Dimension(1000, 600));
		
		tabPane.addTab("OpenSky-Network", openSkyModel.rootPanel);
		tabPane.addTab("Local Database", localModel.rootPanel);
		
		dialogPanel.add(tabPane, BorderLayout.CENTER);
		
		this.addContent(dialogPanel);
	}
	
	protected String getSource()
	{
		final int index = tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return "OpenSky-Network";
		
		case 1: return "Local Database";
		
		default: return "Error";
		}
	}
	
	protected String getTable()
	{
		final int index = tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return openSkyModel.control.getTable();
		
		case 1: return localModel.control.getTable();
			
		default: return "Error";
		}
		
	}
	
	protected void save()
	{
		savedSource = getSource();
		savedTable = getTable();
	}
	
	protected void revert()
	{
		if(savedSource.equals("OpenSky-Network"))
		{
			tabPane.setSelectedIndex(0);
			
			
		}
		else if(savedSource.equals("Local Database"))
		{
			tabPane.setSelectedIndex(1);
			
			localModel.control.setSelectedTable(getTable());
		}
		else
		{
			System.err.println("Failed to save Source Dialog. Source: " + getSource() + "\t Table: " + getTable());
		}
	}
	
	

}
