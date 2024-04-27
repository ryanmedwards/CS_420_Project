package components.custom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.Application;
import components.Panel;
import components.Table;
import components.custom.dialog.Dialog;
import data.Flight;
import guiutil.TableFactory;


public class FlightSelector extends Dialog
{
	
	
	private final JPanel rootPanel = new RootPanel().get();
	
	private final JPanel flightPanel = new JPanel(new BorderLayout());
	private final Table<Flight> flightTable = TableFactory.getFlightTable();

	protected final JPanel descriptionPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), "Description")).get();
	
	private final JLabel descriptionLabel = new JLabel
	(
		  "<html>"
		+ "<p><p>"
		+ "Select the flights to be simulated."
		+ "<p><p>"
		+ "<html>"
	);
	

	protected final List<Flight> flightList;

	
	public FlightSelector(final List<Flight> flights) 
	{
		super(Application.getApp(), "Flight Selector", true);
		this.flightList = flights;
			
		
		flightTable.get().setMinimumSize(new Dimension(750, 150));
		
		this.flightTable.addItems(flights);
		
		
		this.setMode(Dialog.Mode.CANCEL_CONFIRM);
		this.draw();
	}
	
	
	private void draw()
	{
		descriptionPanel.add(descriptionLabel);
		
		flightPanel.add(flightTable.toScrollPane(), BorderLayout.NORTH);
		
		rootPanel.add(descriptionPanel, BorderLayout.NORTH);
		rootPanel.add(flightPanel, BorderLayout.CENTER);

		this.addContent(rootPanel);
		this.setMinimumSize(new Dimension(1000, 400));
		this.pack();
	}

	
	public List<Flight> getSelectedFlights()
	{
		return flightTable.getSelectedItems();
	}
		
}






	
	























