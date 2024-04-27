package mvc.main.simulation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.Application;
import components.Label;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;
import mvc.destination.Destination;
import mvc.destination.DestinationSelector;

public class SimulationDestinationModel 
{
	protected final JPanel rootPanel = new RootPanel().get();
	
	protected final DestinationSelector destinationSelector = DestinationSelector.image(Application.getApp());
	

	private final JPanel destLabelPanel = new Label("Destination").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 15, 15);
	private final JPanel destPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

	protected final JButton selectDestinationButton = new JButton("Select");
	

	protected final JTextField destinationField = new TextField().initEditable(false).initColumns(30).get();
	
	protected SimulationDestinationModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{

		destPanel.add(new JLabel("Location: "));
		destPanel.add(destinationField);
		destPanel.add(selectDestinationButton);
		
		rootPanel.add(destLabelPanel, BorderLayout.NORTH);
		rootPanel.add(destPanel, BorderLayout.CENTER);
	}
	
	private void assign()
	{
		this.selectDestinationButton.addActionListener(e ->
		{
			if( destinationSelector.launch() )
			{
				if( destinationSelector.hasValidDestination() )
				{
					final Destination dest = destinationSelector.getDestination();
					
					this.destinationField.setText(dest.location);
				}
			}
		});
	}
}
