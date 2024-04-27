package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.Application;
import components.Label;
import components.Table;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;
import guiutil.TableFactory;
import mvc.destination.Destination;
import mvc.destination.DestinationSelector;
import mvc.source.Source;
import mvc.source.SourceSelector;

public class IOModel 
{
	protected final JPanel rootPanel = new RootPanel().get();
	
	
	// Source
	private final JPanel sourcePanel = new RootPanel().get();
	
	private final JPanel sourceLabelPanel = new Label("Sources").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 15, 15);
	private final JPanel sourceTablePanel = new JPanel(new BorderLayout());
	private final JPanel sourceButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	
	protected final SourceSelector sourceSelector = new SourceSelector(Application.getApp(),
			Set.of(Source.Type.OPENSKY_NETWORK, Source.Type.LOCAL_DATABASE, Source.Type.IMPORT_FILE));
	
	protected final Table<Source> sourceTable = TableFactory.getSourceTable();

	protected final JButton addSourceButton = new JButton("Add Source");
	protected final JButton removeSourceButton = new JButton("Remove Source");
	
	
	
	// Destination
	
	private final JPanel destPanel = new RootPanel().get();
	
	private final JPanel destLabelPanel = new Label("Destination").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 15, 15);
	private final JPanel destFieldPanel = new JPanel(new GridBagLayout());
	private final JPanel destButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	
	protected final DestinationSelector destinationSelector = DestinationSelector.stateVectors(Application.getApp());
	
	protected final JButton selectDestinationButton = new JButton("Select");
	

	protected final JTextField destinationField = new TextField().initEditable(false).initColumns(30).get();
	protected final JTextField locationField = new TextField().initEditable(false).initColumns(30).get();


	protected IOModel()
	{
		this.draw();
		this.assign();
	}
	
	
	private void draw()
	{
		// Draw Source 
		
		sourceTablePanel.add(sourceTable.toScrollPane());
		
		sourceButtonPanel.add(addSourceButton);
		sourceButtonPanel.add(removeSourceButton);
		
		sourcePanel.add(sourceLabelPanel, BorderLayout.NORTH);
		sourcePanel.add(sourceTablePanel, BorderLayout.CENTER);
		sourcePanel.add(sourceButtonPanel, BorderLayout.SOUTH);
		
		
		
		// Draw Destination
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		int x = 0;
		int y = 0;
		
		
		destFieldPanel.add(new JLabel("Destination: "), grid.set(x,   y));
		destFieldPanel.add(new JLabel("Location: ")   , grid.set(x, ++y));
		
		
		x = 1; y = 0;
		grid.setAnchor(Grid.EAST);
		
		destFieldPanel.add(destinationField, grid.set(x,   y));
		destFieldPanel.add(locationField   , grid.set(x, ++y));
		
		destButtonPanel.add(selectDestinationButton);
		
		destPanel.add(destLabelPanel, BorderLayout.NORTH);
		destPanel.add(destFieldPanel, BorderLayout.CENTER);
		destPanel.add(destButtonPanel, BorderLayout.SOUTH);
		
		
		rootPanel.add(sourcePanel, BorderLayout.CENTER);
		rootPanel.add(destPanel, BorderLayout.EAST);
	}
	
	
	
	private void assign()
	{
		selectDestinationButton.addActionListener(e ->
		{
			if( destinationSelector.launch() )
			{
				if( destinationSelector.hasValidDestination() )
				{
					final Destination dest = destinationSelector.getDestination();
					
					this.destinationField.setText(dest.type.toString());
					this.locationField.setText(dest.location);
				}
			}
		});
	}
	
	
	protected boolean hasOpenSkySource()
	{
		final List<Source> sources = sourceTable.getItems();
		
		for(final Source source: sources)
		{
			if(source.type.equals(Source.Type.OPENSKY_NETWORK))
			{
				return true;
			}
		}
		
		return false;
	}

	
}









