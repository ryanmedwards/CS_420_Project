package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import application.Application;
import application.Log;
import components.Table;
import components.custom.RootPanel;
import guiutil.MyTableModel;
import guiutil.OptionPane;
import guiutil.TableFactory;
import mvc.destination.Destination;
import mvc.destination.DestinationSelector;
import mvc.filters.Filter;
import mvc.source.Source;
import mvc.source.SourceModel;
import mvc.source.SourceSelector;
import opensky.OpenSkyTable;
import opensky.Trino;
import opensky.statevector.StateVectorIO;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;
import sql.LocalSQL;



public class ProcessModel
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel westPanel = new JPanel(new BorderLayout());
	protected final JPanel centerPanel = new JPanel(new BorderLayout());
	
	
	protected final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	protected final JButton startButton = new JButton("Start");
	
	

	protected final IOModel ioModel = new IOModel();
	protected final BuilderModel builderModel = new BuilderModel();
	protected final FeedbackModel feedbackModel = new FeedbackModel();
	
	protected final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ioModel.rootPanel, feedbackModel.rootPanel);
	
	
	public ProcessModel()
	{
		this.draw();
		this.assign();
	}
	
	
	private void draw()
	{
		
		westPanel.add(builderModel.rootPanel, BorderLayout.WEST);
		
		//centerPanel.add(ioModel.rootPanel, BorderLayout.NORTH);
		//centerPanel.add(feedbackModel.rootPanel, BorderLayout.CENTER);
		

		southPanel.add(startButton);
		
		
		rootPanel.add(westPanel, BorderLayout.WEST);
		rootPanel.add(splitPane, BorderLayout.CENTER);
		rootPanel.add(southPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void assign()
	{
		startButton.addActionListener(e ->
		{
			Log.setLog(feedbackModel.getLog());
			
			Log.clear();
			
			if( ! new ProcessExecutor(this).execute() )
			{
				OptionPane.showError("Failed Execution.");
			}
			else
			{
				OptionPane.showMessage("Successful Execution.");
			}
		});
		
		
		
		ioModel.addSourceButton.addActionListener(e ->
		{
			if( ioModel.sourceSelector.launch() )
			{
				if(ioModel.sourceSelector.hasValidSource())
				{
					final Source source = ioModel.sourceSelector.getSource();
					
					if(source.type.equals(Source.Type.OPENSKY_NETWORK)
							&& ! ioModel.hasOpenSkySource())
					{
						builderModel.setOpenSky();
					}
					
					if( ! ioModel.sourceTable.contains(source) )
					{
						ioModel.sourceTable.addItem(source);
					}
				}
			}
		});
		
		ioModel.removeSourceButton.addActionListener(e ->
		{
			ioModel.sourceTable.removeSelectedRows();
			
			if( ! ioModel.hasOpenSkySource() )
			{
				builderModel.setLocal();
			}
		});
		
		
		
		
		
	}
	
	protected List<Source> getSources()
	{
		return ioModel.sourceTable.getItems();
	}

	protected List<Filter> getOpenSkyFilters()
	{
		final List<Filter> filters = builderModel.getOpenSkyFilters();
		
		for(final Filter f: filters)
		{
			f.applied = Filter.Applied.QUERY;
		}
		
		return filters;
	}
	
	protected List<Filter> getLocalFilters()
	{
		final List<Filter> filters = builderModel.getLocalFilters();
		
		for(final Filter f: filters)
		{
			f.applied = Filter.Applied.JAVA;
		}
		
		return filters;
	}
	
}
