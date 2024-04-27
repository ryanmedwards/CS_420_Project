package mvc.source;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import components.IList;
import components.Label;
import components.Panel;
import components.Table;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import components.text.TextArea;
import components.text.TextField;
import guiutil.Grid;
import guiutil.TableFactory;
import mvc.filters.Filter;
import sql.LocalSQL;
import sql.LocalSVTableLoader;

public class LocalDBSourceModel 
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
		+ "<p><p>"
		+ "Select a table in the local database as the source of data."
		+ "<p><p>"
		+ "<html>"
	);
	
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.RIGHT);
	
	// List of Tables

	protected final JPanel tablesPanel = new RootPanel().get();
	protected final IList<LocalTable> tableList = new IList<LocalTable>().initModel(new DefaultListModel<LocalTable>());
	
	
	
	// Selected Table History

	protected final Table<Source> sourceTable = TableFactory.getSourceTable();
	
	protected final JTextArea queryTextArea = new TextArea().initEditable(false).get();
	protected final JScrollPane queryScrollPane = new JScrollPane(queryTextArea);
	
	protected final JPanel historyLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel historyLabel = new Label("Information").initFontSize(18).get();
	
	protected final JPanel historyPanel = new RootPanel().get();
	
	protected final JTextArea historyTextArea = new TextArea().initEditable(false).get();
	protected final JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
	
	
	
	
	

	protected LocalDBSourceModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;

		// Draw List

		this.tablesPanel.add(
				new Label("Tables").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 150, 10), 
				BorderLayout.NORTH);
		this.tablesPanel.add(this.tableList.toScrollPane(), BorderLayout.CENTER);
		
		
		
		// Draw History Panel
		
		this.historyLabelPanel.add(this.historyLabel);
		
		this.historyPanel.add(this.historyLabelPanel, BorderLayout.NORTH);
		this.historyPanel.add(this.tabPane, BorderLayout.CENTER);
		
		this.tabPane.addTab("Sources", this.sourceTable.toScrollPane());
		this.tabPane.addTab("Filters", this.historyScrollPane);
		this.tabPane.addTab("Query", this.queryScrollPane);
		
		
		
		// Draw Root Panel
		
		this.descriptionPanel.add(this.descriptionLabel, BorderLayout.CENTER);
		
		this.controlsPanel.add(this.tablesPanel, BorderLayout.WEST);
		this.controlsPanel.add(this.historyPanel, BorderLayout.CENTER);
		
		this.rootPanel.add(this.descriptionPanel, BorderLayout.NORTH);
		this.rootPanel.add(this.controlsPanel, BorderLayout.CENTER);
		
	}
	
	
	private void assign()
	{
		this.tableList.addItems(LocalSVTableLoader.LOCAL_TABLES);
		
		
		this.tableList.get().addListSelectionListener(e ->
		{
			this.update();
		});

		this.update();
	}
	
	private void update()
	{
		if(this.tableList.getSelectedItem() != null)
		{
			final LocalTable selectedTable = this.tableList.getSelectedItem();
			
			this.historyTextArea.setText(selectedTable.getHistory());
			
			this.queryTextArea.setText(selectedTable.getQuery());
			
			this.sourceTable.clear();
			
			this.sourceTable.addItems(selectedTable.getSources());
		}
	}

	
	public Source getSource()
	{
		return new Source(Source.Type.LOCAL_DATABASE, this.tableList.getSelectedItem().name);
	}
}











