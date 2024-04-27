package guiutil;

import javax.swing.ListSelectionModel;

import components.Table;
import data.Flight;
import mvc.source.Source;

public class TableFactory 
{
	
	
	
	public static Table<Source> getSourceTable()
	{
		return new Table<Source>()
				.initModel(new MyTableModel("Source", "Location"))
				.initColumnsWidths(250, 500);
	}
	
	
	public static Table<Flight> getFlightTable()
	{
		return new Table<Flight>()
				.initModel(new MyTableModel("ICAO Adress", "Callsign", "Start Time", "Stop Time", "Message Count", "Classification", "Source"))
				.initHideColumns(6)
				.initSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
				.initGridLines(true);
	}
	
	
	
	
	

}
