package mvc.source;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import components.Label;
import components.Panel;
import components.Table;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import data.Airline;
import guiutil.MyTableModel;
import opensky.OpenSkyTable;
import opensky.components.StateVectorDescription;

public class OpenSkySourceModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();

	
	protected final JPanel descriptionPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), "Description")).get();
	
	
	protected final JPanel controlsPanel = new Panel()
			.initLayout(new BorderLayout())
			.initBorder(BorderFactory.createTitledBorder(
							BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), "Controls")).get();
	
	
	protected final JLabel descriptionLabel = new JLabel
	(
		  "<html>"
		+ "<br><p>"
		+ "Select a table from The OpenSky-Network as the source of data."
		+ "</p>"
		+ "<br>"
		+ "<p>"
		+ "Select the check box to save a copy of the unfiltered data set. The unfiltered set will only "
		+ "contain entries filtered by date, time, and boundary."
		+ "</p><br>"
		+ "<html>"
	);
	
	
	
	// Open Sky Tables
	
	protected final JPanel tablesPanel = new RootPanel().get();
	
	protected final JPanel tablesLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel tablesLabel = new Label("Tables").initFontSize(18).get();
	
	protected final JPanel tablesBarPanel = new JPanel(new BorderLayout());
	protected final ToggleBar<OpenSkyTable> tablesBar = new ToggleBar<>(7, 0, 1, OpenSkyTable.values());

	
	
	// OpenSky Table Schema
	
	protected final JPanel schemaPanel = new RootPanel().get();

	protected final JPanel schemaLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel schemaLabel = new Label("Table Schema").initFontSize(18).get();
	
	protected final Table<StateVectorDescription> schemaTable = new Table<StateVectorDescription>()
			.initModel(new MyTableModel(StateVectorDescription.COLUMNS))
			.initColumnsWidths(50, 50, 500)
			.initGridLines(true);
	
	
	protected OpenSkySourceModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		
		// Draw Tables Panel
		
		this.tablesLabelPanel.add(this.tablesLabel);
		
		this.tablesBarPanel.add(this.tablesBar, BorderLayout.NORTH);

		this.tablesPanel.add(this.tablesLabelPanel, BorderLayout.NORTH);
		this.tablesPanel.add(this.tablesBarPanel, BorderLayout.CENTER);

		
		
		
		
		// Draw Schema Panel
		
		this.schemaLabelPanel.add(this.schemaLabel);
		
		this.schemaPanel.add(this.schemaLabelPanel, BorderLayout.NORTH);
		this.schemaPanel.add(this.schemaTable.toScrollPane(), BorderLayout.CENTER);
		
		


		// Draw Root Panel
		
		this.descriptionPanel.add(this.descriptionLabel, BorderLayout.CENTER);

		
		this.controlsPanel.add(this.tablesPanel, BorderLayout.WEST);
		this.controlsPanel.add(this.schemaPanel, BorderLayout.CENTER);
		
		this.rootPanel.add(this.descriptionPanel, BorderLayout.NORTH);
		this.rootPanel.add(this.controlsPanel, BorderLayout.CENTER);
	}
	
	private void assign()
	{
		this.schemaTable.addItems(StateVectorDescription.list);
	}
	


	public Source getSource()
	{
		return new Source(Source.Type.OPENSKY_NETWORK, this.tablesBar.getSelectedItem().toString());
	}
}
