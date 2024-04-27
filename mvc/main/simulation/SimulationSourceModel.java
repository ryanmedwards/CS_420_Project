package mvc.main.simulation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import application.Application;
import components.Label;
import components.Panel;
import components.Table;
import components.buttons.Button;
import components.custom.FlightSelector;
import components.custom.FlightTable;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import data.Flight;
import guiutil.MyTableModel;
import guiutil.OptionPane;
import matlab.Matlab;
import mvc.source.Source;
import mvc.source.SourceModel;
import mvc.source.SourceSelector;
import opensky.statevector.MessageClassification;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorIO;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;
import sql.LocalSQL;

public class SimulationSourceModel 
{

	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	private final SourceSelector sourceSelector = new SourceSelector(Application.getApp(),
			Set.of(Source.Type.LOCAL_DATABASE, Source.Type.IMPORT_FILE));
	


	protected Map<String, FlightSelector> flightSelectorMap = new HashMap<>();
	
	protected final JPanel sourcePanel = new Panel(new BorderLayout()).initMinimumSize(10, 250).get();
	protected final JLabel sourceLabel = new Label("Data Sources").initFontSize(18).get();
	
	
	Table<Source> sourceTable = new Table<Source>()
			.initModel(new MyTableModel("Source", "Location"))
			.initColumnsWidths(250, 500);
	
	protected final JButton addSourceButton = new JButton("Add Source");
	protected final JButton selectFlightsButton = new JButton("Select Flights");
	protected final JPanel sourceNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	protected final JPanel sourceSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	
	
	
	protected final JPanel flightNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	protected final JLabel flightLabel = new Label("Flights").initFontSize(18).get();
	protected final JPanel flightPanel = new Panel(new BorderLayout()).initMinimumSize(10, 250).get();
	protected final FlightTable flightTable = new FlightTable();
	protected final JScrollPane flightTableScrollPane = new JScrollPane(flightTable);
	protected final JButton removeFlightsButton = new JButton("Remove Flights");
	protected final JPanel flightSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); 
	
	protected final JSplitPane tableSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sourcePanel, flightPanel);

	
	protected final SimulationModel parent;
	
	
	protected SimulationSourceModel(final SimulationModel parent)
	{
		this.parent = parent;
		this.draw();
		this.assign();
	}
	private void draw()
	{
		sourceNorthPanel.add(sourceLabel);
		sourceSouthPanel.add(selectFlightsButton);
		sourceSouthPanel.add(addSourceButton);
		
		sourcePanel.add(sourceNorthPanel, BorderLayout.NORTH);
		sourcePanel.add(sourceTable.toScrollPane(), BorderLayout.CENTER);
		sourcePanel.add(sourceSouthPanel, BorderLayout.SOUTH);

		
		flightNorthPanel.add(flightLabel);
		flightSouthPanel.add(removeFlightsButton);
		
		flightPanel.add(flightNorthPanel, BorderLayout.NORTH);
		flightPanel.add(flightTableScrollPane, BorderLayout.CENTER);
		flightPanel.add(flightSouthPanel, BorderLayout.SOUTH);

		rootPanel.add(tableSplitPane, BorderLayout.CENTER);
		
	}
	
	
	private void assign()
	{
		addSourceButton.addActionListener(e -> addSource());
		
		selectFlightsButton.addActionListener(e -> selectFlights());
		
		removeFlightsButton.addActionListener(e -> removeFlights());

	}
	
	// Inefficient
	private void addSource()
	{
		if(sourceSelector.launch())
		{
			if(sourceSelector.hasValidSource())
			{
				final Source source = sourceSelector.getSource();
				
				final StateVectorList data = StateVectorIO.loadSource(source);
				
				final List<Flight> flights = StateVectorOP.getFlights(data);
				
				sourceTable.addItem(source);
				
				flightSelectorMap.put(source.toString(), new FlightSelector(flights));
				
				if( ! Matlab.loadSource(source) )
				{
					OptionPane.showError("Failed to load source in Matlab.");
				}
	
				parent.visualModel.geoplotModel.addClasses(StateVectorOP.getUniqueClassifications(data));
				
			}
			else
			{
				
			}
		}
	}
	
	private void selectFlights()
	{
		final int row = sourceTable.get().getSelectedRow();

		if(row == -1)
		{
			OptionPane.showError("No source selected.");
			return;
		}
		
		final String key = sourceTable.get().getValueAt(row, 0) + "::" + sourceTable.get().getValueAt(row, 1);
		
		final FlightSelector flightSelector = flightSelectorMap.get(key); 
		
		if(flightSelector.launch())
		{
			final List<Flight> flights = flightSelector.getSelectedFlights();
			
			flights.removeAll(flightTable.getFlights());

			flightTable.addFlights(flights);
			
			for(final Flight f: flights)
			{
				if( Matlab.addFlight(f) )
				{
					
				}
			}
		}
		
	}
	
	private void removeFlights()
	{
		if(Matlab.removeFlights(flightTable.getSelectedFlights()))
		{
			flightTable.removeSelectedRows();
		}
	}
}

































