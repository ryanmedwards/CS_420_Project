package mvc.filters;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import application.Application;
import components.IList;
import components.Label;
import components.Table;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import guiutil.MyTableModel;
import guiutil.OptionPane;

public class FilterModel 
{

	public final JPanel rootPanel = new JPanel(new BorderLayout());
	
	

	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Filter Editor", true)
											 .initMinimumSize(1200, 800)
											 .initPreferredSize(1200, 800)
											 .initMode(Dialog.Mode.CANCEL_CONFIRM);
	
	protected final JPanel filterPanel = new JPanel(new BorderLayout());
	
	protected final JPanel listPanel = new RootPanel().get();
	protected final JPanel labelPanel = new Label("Filters")
											.initFontSize(18)
											.toFlowPanel(FlowLayout.CENTER, 100, 15);


	
	protected final Filter[] filters = new Filter[]
	{
		new DateTimeFilter		(0),
		new BoundaryFilter		(1),
		new NullsFilter			(2),
		new DuplicatesFilter	(3),
		new ExpiredFilter		(4),
		new AirlineFilter		(5)
	};

	
	protected final IList<Filter> filterList = new IList<Filter>()
			.initModel(new DefaultListModel<Filter>())
			.initItems(filters);

	

	public FilterModel()
	{
		this.draw();
		this.assign();
	}
	
	
	private void draw()
	{		
		listPanel.add(labelPanel, BorderLayout.NORTH);
		listPanel.add(filterList.toScrollPane(), BorderLayout.CENTER);
		
		
		rootPanel.add(listPanel, BorderLayout.WEST);
		rootPanel.add(filterPanel, BorderLayout.CENTER);


		dialog.addContent(rootPanel);
	}
	
	protected void drawFilter(final Filter filter)
	{
		filterPanel.removeAll();
		
		filterPanel.add(filter.rootPanel, BorderLayout.CENTER);
		
		rootPanel.revalidate();
		rootPanel.repaint();
	}
	
	private void assign()
	{
		filterList.get().getSelectionModel().addListSelectionListener(e ->
		{
			final Filter filter;
			
			if((filter = filterList.get().getSelectedValue()) != null)
			{
				drawFilter(filter);
			}
		});
	}
	
	public Filter getSelectedFilter()
	{
		if(filterList.size() == 0)
		{
			OptionPane.showMessage("No more filters to choose from.");
			return null;
		}
		
		try
		{
			filterList.setSelectedIndex(0);
			
			if(dialog.launch())
			{
				final Filter filter = filterList.get().getSelectedValue();			
				
				final DefaultListModel<Filter> lm = (DefaultListModel<Filter>)filterList.get().getModel();
				
				lm.removeElement(filter);
				
				return filter;
			}
			
			return null;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
	}

	public void editFilter(final Filter filter)
	{
		if( filter != null )
		{
			final List<Filter> temp = filterList.getAllItems();
			
			filterList.removeAllItems();
			
			filterList.addItem(filter);
			
			filterList.setSelectedIndex(0);
			
			filter.saveState();
			
			if( ! dialog.launch() )
			{
				filter.revertState();
			}
			
			filterList.removeAllItems();
			
			filterList.addItems(temp);
			
			filterList.setSelectedIndex(0);
		}
	}
	
	public void returnFilter(final Filter filter)
	{
		if( filter != null )
		{
			filterList.addItem(filter);
		}
	}
	
	
}

