package mvc.main.process;

import java.util.ArrayList;
import java.util.List;

import application.Log;
import mvc.destination.Destination;
import mvc.filters.Filter;
import mvc.source.Source;
import opensky.OpenSkyTable;
import opensky.Trino;
import opensky.statevector.StateVectorIO;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;
import sql.LocalSQL;

public class ProcessExecutor 
{
	
	private final ProcessModel model;
	
	
	protected ProcessExecutor(final ProcessModel model)
	{
		this.model = model;
	}
	
	protected boolean execute()
	{
		final List<Source> sources = model.getSources();

		
		Log.append("Checking For Sources...   ");
		if( sources.size() == 0 )
		{
			Log.append("No sources selected.");
			return false;
		}
		Log.append(sources.size() + " sources.");
		
		
		Log.append("\nChecking Validity of Destination...   ");
		if( ! model.ioModel.destinationSelector.hasValidDestination() )
		{
			Log.append("Invalid Desination.");
			return false;
		}
		Log.append("Valid Desination");
		

		final Destination destination = model.ioModel.destinationSelector.getDestination();
		
		final StateVectorList data = new StateVectorList(destination.toSource());
		
		Log.append("\nChecking for the OpenSky-Network...   ");
		int osIndex = -1;
		if((osIndex = Source.contains(sources, Source.Type.OPENSKY_NETWORK)) != -1)
		{
			Log.append("Found the OpenSky-Network.");
			Log.append("\nExecuting query to the OpenSky-Network...   ");
			
			final StateVectorList osSV = this.executeOpenSky(sources.get(osIndex));
			if(osSV == null)
			{
				Log.append("Failed.");
				return false;
			}
			
			data.addAll(osSV);
		}
		Log.append("Success.");

		
		if(sources.size() > 0)
		{
			Log.append("\nCombining sources...   ");
			int next = 0;
			for(final Source s: sources)
			{
				Log.append(String.format("\n\t%d. %s: %s", ++next, s.type, s.location));
				data.addAll(StateVectorIO.loadSource(s));
			}
			Log.append(String.format("\nSuccessfully loaded %d source(s).", --next));
		}
		
		return this.executeLocalSource(data);
	}
	
	private boolean executeLocalSource(StateVectorList sv)
	{
		// Get Filters
		Log.append("\nGetting selected filters for local processing...   ");
		final List<Filter> filters = model.getLocalFilters();
		Log.append(filters.size() + " filter(s) selected.");
		
		
		// Apply Filters
		if(filters.size() > 0)
		{
			Log.append("\nApplying filters...   ");
			int i = 1;
			for(final Filter f: filters)
			{
				Log.append("\n\t" + i++ + ". " + f.type.name + "...   ");
				if( ! f.filter(sv) )
				{
					Log.append("Failed: " + StateVectorOP.errorMessage);
					return false;
				}
				Log.append("Successful. Data set size: " + sv.size());
			}
		}
		
		final Destination destination = model.ioModel.destinationSelector.getDestination();

		
		Log.append("\nSelected Destination: " + destination.type);
		Log.append("\nSelected Location: " + destination.location);
		
		switch(destination.type)
		{
		
		case FILE: return StateVectorIO.writeCSV(sv, destination.location);

			
		case LOCAL_DATABASE:
			
			// Add OpenSky filters to Log
			final List<Filter> list = model.getOpenSkyFilters();
			list.addAll(filters);
			
			if( ! LocalSQL.add(sv, model.getSources(), list, destination.location) )
			{
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * 
	 * @return true if successful, false otherwise
	 */
	private StateVectorList executeOpenSky(final Source source)
	{
		final List<Filter> filters = model.getOpenSkyFilters();

		final String query = Filter.buildQuery(filters, source.location);
		
		if( query == null )
		{
			return null;
		}
		
		switch(OpenSkyTable.getEnum(source.location))
		{
		case STATE_VECTORS_DATA4:
		
			return Trino.queryStateVectorData4(query);
		}
		
		return null;
	}
	
}
