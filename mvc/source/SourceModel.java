package mvc.source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import application.Application;
import components.custom.dialog.Dialog;
import guiutil.OptionPane;

public class SourceModel 
{

	protected final JPanel rootPanel = new JPanel(new BorderLayout());
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	

	protected final OpenSkySourceModel openSkyModel = new OpenSkySourceModel();
	protected final LocalDBSourceModel localModel = new LocalDBSourceModel();
	protected final ImportFileModel importModel = new ImportFileModel();
	
	private final Map<Integer, Source.Type> tabMap = new HashMap<>();

	
	protected SourceModel(final Set<Source.Type> sources)
	{
		this.draw(sources);
		this.assign();
	}
	
	
	private void draw(final Set<Source.Type> sources)
	{
		int next = 0;
		
		for(final Source.Type source: sources)
		{
			switch(source)
			{
			case IMPORT_FILE:     this.tabPane.addTab(source.toString(), this.importModel.rootPanel);  break;
				
			case LOCAL_DATABASE:  this.tabPane.addTab(source.toString(), this.localModel.rootPanel);  break;
				
			case OPENSKY_NETWORK: this.tabPane.addTab(source.toString(), this.openSkyModel.rootPanel); break;
			}
			
			this.tabMap.put(next++, source);
		}
		
		this.rootPanel.add(this.tabPane, BorderLayout.CENTER);
		
	}
	
	private void assign()
	{
		
	}


	public boolean hasValidSource()
	{
		try
		{
			final Source source = getSource();
			
			if(source == null)
			{
				return false;
			}
			
			if(source.type == null || source.location == null)
			{
				return false;
			}
			
			if(source.location.isBlank())
			{
				return false;
			}
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}	
	}
	
	
	public Source getSource() throws IndexOutOfBoundsException, IllegalFormatException
	{
		final int index = this.tabPane.getSelectedIndex();
		
		switch(this.tabMap.get(index))
		{
		case OPENSKY_NETWORK: 
			return this.openSkyModel.getSource();
		
		case LOCAL_DATABASE: 
			return this.localModel.getSource();
		
		case IMPORT_FILE: 
			return this.importModel.getSource();
		}
		
		return null;
	}


}












