package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import application.Application;
import components.IList;
import components.Label;
import components.Panel;
import components.buttons.Button;
import components.custom.FlightTable;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import mvc.filters.DateTimeFilter;
import mvc.filters.DuplicatesFilter;
import mvc.filters.ExpiredFilter;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.filters.NullsFilter;


public class BuilderModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	

	private final FilterModel queryFilterModel = new FilterModel();
	private final FilterModel localFilterModel = new FilterModel();
	


	private final JPanel queryPanel = new Panel(new BorderLayout()).initMinimumSize(10, 250).get();
	private final JLabel queryLabel = new Label("Query Filters").initFontSize(18).get();
	
	private final IList<Filter> queryList = new IList<Filter>().initModel(new DefaultListModel<Filter>());
	
	private final JButton addQueryFilterButton = new JButton("Add");
	private final JButton editQueryFilterButton = new JButton("Edit");
	private final JButton removeQueryFilterButton = new JButton("Remove");
	private final JPanel queryNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	private final JPanel querySouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	
	
	
	private final JPanel localPanel = new Panel(new BorderLayout()).initMinimumSize(10, 250).get();
	private final JLabel localLabel = new Label("Local Filters").initFontSize(18).get();
	
	private final IList<Filter> localList = new IList<Filter>().initModel(new DefaultListModel<Filter>());
	
	private final JButton addLocalFilterButton = new JButton("Add");
	private final JButton editLocalFilterButton = new JButton("Edit");
	private final JButton removeLocalFilterButton = new JButton("Remove");
	private final JPanel localNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	private final JPanel localSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	
	
	private final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, queryPanel, localPanel);
	
	
	
	protected BuilderModel()
	{
		this.draw();
		this.assign();
	}
	
	

	
	private void draw()
	{	
		
		queryNorthPanel.add(queryLabel);
		
		querySouthPanel.add(addQueryFilterButton);
		querySouthPanel.add(editQueryFilterButton);
		querySouthPanel.add(removeQueryFilterButton);
		
		queryPanel.add(queryNorthPanel, BorderLayout.NORTH);
		queryPanel.add(queryList.toScrollPane(), BorderLayout.CENTER);
		queryPanel.add(querySouthPanel, BorderLayout.SOUTH);
		
		
		
		
		localNorthPanel.add(localLabel);
		
		localSouthPanel.add(addLocalFilterButton);
		localSouthPanel.add(editLocalFilterButton);
		localSouthPanel.add(removeLocalFilterButton);
		
		localPanel.add(localNorthPanel, BorderLayout.NORTH);
		localPanel.add(localList.toScrollPane(), BorderLayout.CENTER);
		localPanel.add(localSouthPanel, BorderLayout.SOUTH);
		
		
		
		rootPanel.add(splitPane, BorderLayout.CENTER);
	

	}
	
	private void assign()
	{
		
		// Add Filter
		
		addQueryFilterButton.addActionListener(e -> 
		{
			final Filter filter;
			
			if((filter = queryFilterModel.getSelectedFilter()) != null)
			{
				queryList.addItem(filter);
			}
		});

		addLocalFilterButton.addActionListener(e -> 
		{
			final Filter filter;
			
			if((filter = localFilterModel.getSelectedFilter()) != null)
			{
				localList.addItem(filter);
			}
		});
		
		
		
		// Edit Filter
		
		editQueryFilterButton.addActionListener(e -> 
		{
			queryFilterModel.editFilter(queryList.getSelectedItem());
		});
		
		editLocalFilterButton.addActionListener(e -> 
		{
			localFilterModel.editFilter(localList.getSelectedItem());
		});
		

		
		// Remove Filter
		
		removeQueryFilterButton.addActionListener(e -> 
		{
			queryFilterModel.returnFilter(queryList.getSelectedItem());
			
			queryList.removeSelectedItem();
		});
		
		removeLocalFilterButton.addActionListener(e -> 
		{
			localFilterModel.returnFilter(localList.getSelectedItem());
			
			localList.removeSelectedItem();
		});
		
	}
	
	

	protected void setOpenSky()
	{
		splitPane.setLeftComponent(queryPanel);
		splitPane.revalidate();
	}
	

	protected void setLocal()
	{
		final DefaultListModel<Filter> lm = (DefaultListModel<Filter>) queryList.get().getModel();
		
		lm.removeAllElements();
		
		System.err.println("BuilderController.setLocal(): UPDATE REMOVING ELEMENTS");
		
		splitPane.setLeftComponent(null);
		splitPane.revalidate();
	}
	
	
	
	
	
	public List<Filter> getOpenSkyFilters()
	{
		return queryList.getAllItems();
	}
	
	public List<Filter> getLocalFilters()
	{
		return localList.getAllItems();
	}
	
}
