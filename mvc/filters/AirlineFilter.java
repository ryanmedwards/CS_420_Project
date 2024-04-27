package mvc.filters;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import application.Application;
import guiutil.MyTableModel;
import components.Panel;
import components.Table;
import data.Airline;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;
import sql.LocalSQL;

public class AirlineFilter extends Filter 
{
	
	private static final List<Airline> airlines = LocalSQL.getAirlinesList();
	
	private final Table<Airline> airlineTable = new Table<Airline>()
			.initModel(new MyTableModel("Airline", "ICAO"))
			.initSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
			.initColumnsWidths(250, 250)
			.initPreferredSize(500, 400)
			.initItems(airlines);
	
	
	private int[] selectedRows = new int[0];
	
	
	
	public AirlineFilter(final int id) 
	{
		super(Filter.Type.AIRLINES, id);
		
		super.drawRootPanel();

	}

	@Override
	protected String getDescription() 
	{
		return "<html>"
			 + "<p><p>"
			 + "Select entries from the selected airlines."
			 + "<p><p>"
			 + "<html>";
	}

	@Override
	public void draw(JPanel panel) 
	{
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		panel.add(
			new Panel().toScrollPane().add(
					airlineTable.toScrollPane()));
	}

	@Override
	public void revertState() 
	{
		this.airlineTable.setSelectedRows(selectedRows);
	}



	@Override
	public void saveState() 
	{
		this.selectedRows = this.airlineTable.get().getSelectedRows();
	}

	@Override
	public boolean filter(StateVectorList data) 
	{
		return StateVectorOP.filterAirlines(data, this.airlineTable.getSelectedItems());
	}




	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		final List<Airline> selectedAirlines = airlineTable.getSelectedItems();
		
		final StringBuilder sb = new StringBuilder();
		
		for(final Airline airline: selectedAirlines)
		{
			sb.append(String.format("insert into %s values (%d, %d, \'%s\', \'%s\');", 
					this.getDBTable(),
					id,
					order,
					applied.toString(),
					airline.code));
		}

		return sb.toString();
	}
	
	@Override
	public String getQueryCondition()
	{
		this.saveState();
		
		return FilterQuery.getAirlinesQuery(this.airlineTable.getSelectedItems());
	}

	@Override
	public String getHistory()
	{
		this.saveState();
		
		return FilterHistory.getAirlinesHistory(this.airlineTable.getSelectedItems());
	}

}


















