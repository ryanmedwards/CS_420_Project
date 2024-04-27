package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import application.Application;
import guiutil.Displayable;
import mvc.source.Source;
import opensky.statevector.MessageClassification;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class Flight implements Displayable<Flight>
{
	// Columns
	//"ICAO Adress", "Callsign", "Start Time", "Stop Time", "Message Count", "Classification"
	
	public final Source source;
	
	public final String icao24;
	
	public final String callsign;
	
	public final int messageCount;
	
	public final int start;
	
	public final int stop;
	
	public final MessageClassification type;
	
	public Flight(
			final Source source, 
			final String icao24, 
			final String callsign, 
			final int start, 
			final int stop, 
			final int messageCount,
			final MessageClassification type)
	{
		this.source = source;
		this.icao24 = icao24;
		this.callsign = callsign;
		this.messageCount = messageCount;
		this.start = start;
		this.stop = stop;
		this.type = type;
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if(o instanceof Flight)
		{
			final Flight f = (Flight) o;
			
			return this.icao24.equals(f.icao24) &&
				   this.callsign.equals(f.callsign) &&
				   this.start == f.start &&
				   this.stop == f.stop &&
				   this.messageCount == f.messageCount &&
				   this.type == f.type;
		}
		return false;
	}

	@Override
	public Object[] toArray()
	{
		return new Object[] 
		{
			this.icao24, this.callsign, this.start, this.stop, this.messageCount, this.type, this.source
		};
	}
	
	@Override
	public Flight toObject(final JTable table, final int row)
	{
		try
		{
			return new Flight
			(
				(Source)table.getModel().getValueAt(row, 6),
				table.getValueAt(row, 0).toString(),
				table.getValueAt(row, 1).toString(),
				(int)table.getValueAt(row, 2),
				(int)table.getValueAt(row, 3),
				(int)table.getValueAt(row, 4),
				MessageClassification.getEnum(table.getValueAt(row, 5).toString())
			);
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
	}
	
	public String getKey()
	{
		return icao24 + "," + callsign + "," + type.toInt();
	}
	
	
	public StateVectorList toStateVector()
	{
		return StateVectorOP.flightToStateVector(this);
	}
}
