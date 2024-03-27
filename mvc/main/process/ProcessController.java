package mvc.main.process;

import java.util.List;

import javax.swing.JOptionPane;

import application.Application;
import mvc.filters.Filter;
import mvc.filters.datetime.DateTimeFilter;
import mvc.main.process.io.source.Source;
import sql.FilterTable;
import sql.LocalSQL;

import guiutil.OptionPane;

public class ProcessController
{
	private final ProcessModel model;
	
	public ProcessController(final ProcessModel model)
	{
		this.model = model;

		this.assign();
	}

	private void assign()
	{
		model.startButton.addActionListener(e -> executeLocalProcess());
	}
	
	private void executeLocalProcess()
	{
		if( ! model.ioModel.control.hasValidSource() )
		{
			OptionPane.showError("Invalid Source.");
			return;
		}
		
		final Source source = model.ioModel.control.getSource();
		
		switch(source)
		{
		case OPENSKY_NETWORK:
			
			break;
			
		case LOCAL_DATABASE: this.executeLocalSource();
			
			break;
			
		case CSV_FILE:
			
			break;
		
		}	
	}
	
	private void executeLocalSource()
	{
		final Source source = model.ioModel.control.getSource();
		
		final String destinationTable = String.format("\"%s\"",
				model.ioModel.control.getDestinationTable());
		
		final String sourceTable = model.ioModel.control.getSourceTable();
		
		final List<Filter> filters = model.builderModel.control.getFilterList();
		
		final int processID = LocalSQL.getNextProcessID();
		
		if(processID == -1)
		{
			OptionPane.showError("Failed to get a valid process id.");
			return;
		}
		
		// Check if no filters have been added
		if(filters.size() == 0)
		{
			OptionPane.showError("No Filters Selected.");
			return;
		}

		
		// Create the new Table in the Local Database
		if( ! LocalSQL.createAs(destinationTable, sourceTable) )
		{
			OptionPane.showError(String.format("Failed to create table: ", destinationTable));			
			return;
		}
		
		// Add the table to sv_tables
		if( ! LocalSQL.update(
				String.format("insert into sv_tables values(%s, %s, %s, %d, %d);",
						destinationTable,
						source,
						sourceTable,
						processID,
						1)))
		{
			OptionPane.showError("Failed to add table to sv_tables.");
			return;
		}

		
		int filterID = 0;
		
		// Apply the filters to the data set
		for(final Filter filter: filters)
		{
			++filterID;
			
			// If filter encounters an SQL Exception, then stop
			if( ! filter.filter(destinationTable, model.feedbackModel.localLog) )
			{
				OptionPane.showError(String.format("Encountered an SQL Exception while executing filter: ", filter.name));
				
				// Try to drop the table
				if( ! LocalSQL.drop(destinationTable) )
				{
					OptionPane.showError(String.format("Failed to drop table: ", destinationTable));
				}
				
				return;
			}
			else // The filter was successfully applied
			{
				// Log this filter in the process in the database
				if( ! filter.logToProcess(processID, filterID) )
				{
					OptionPane.showError(String.format("Failed to add filter: %s to process.", filter.name));
					return;
				}
				
				// Log this filter to its specific filter table
				if( ! filter.logFilter(processID, filterID) )
				{
					OptionPane.showError(String.format("Failed to log filter: %s to %s.", filter.name, filter.getSQLiteTable()));
					return;
				}
			}
			
		}	
		
		// Clean up the data base
		if( ! LocalSQL.vacuum() )
		{
			OptionPane.showError("Failed to clean the database.");
		}
	}
}










