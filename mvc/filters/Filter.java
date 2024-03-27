package mvc.filters;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import application.Application;
import components.buttons.Button;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import components.Label;
import components.Panel;
import guiutil.Grid;
import mvc.filters.boundary.BoundaryModel;
import mvc.main.process.builder.BuilderModel;
import sql.LocalSQL;

public abstract class Filter
{
	public static final String DATE_TIME = "Date and Time";
	public static final String REDUCE_BOUNDARY = "Reduce Boundary";
	public static final String REMOVE_NULLS = "Remove Nulls";
	public static final String REMOVE_DUPLICATES = "Remove Duplicates";
	public static final String REMOVE_EXPIRED = "Remove Expired Entires";
	
	
	private static int next = 0;
	
	private final int id = next++;
	
	
	private final JPanel rootPanel = new RootPanel().get();
	
	private final JPanel descriptionPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 25))
												.initBorder(BorderFactory.createTitledBorder(
																BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), 
																"Description")).get();
	
	private boolean enabled = true;
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}
	
	
	public final int getID()
	{
		return id;
	}
	
	
	public JPanel getRootPanel()
	{
		return rootPanel;
	}
	
	public final String name;
	private final FilterModel model;
	
	public Filter(final FilterModel model, final String name)
	{ 
		this.model = model;
		this.name = name;
	}

	
	protected void drawDialog()
	{
		final JPanel panel = new Panel().initBorder(BorderFactory.createTitledBorder(
														BorderFactory.createMatteBorder(
																1, 0, 0, 0, rootPanel.getForeground()), "Controls")).get();
		
		draw(panel);
		
		descriptionPanel.add(new JLabel(getDescription()));
		
		rootPanel.add(descriptionPanel, BorderLayout.NORTH);
		rootPanel.add(panel, BorderLayout.CENTER);
	}


	
	public void edit()
	{
		model.control.edit(this);
	}
	
	public void remove()
	{
		model.control.remove(this);
	}
	
	
	
	public static boolean contains(final List<Filter> filters, final String name)
	{
		for(final Filter filter: filters)
		{
			if(filter.name.equals(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Get the name of the table in the local SQLite database<br>
	 * that represents this filter.
	 * 
	 * @return the name of the table in the local SQLite database
	 */
	public abstract String getSQLiteTable();
	
	
	
	
	/**
	 * This method returns a string that will be displayed to the user.
	 * The string will describe what the filter option will do to the data set.
	 * 
	 * @return a string describing what the filter will do to the data set
	 */
	protected abstract String getDescription();

	/**
	 * This method allows sub classes to place its components.
	 * <br>
	 * The filter controls should be placed on the panel parameter.
	 * 
	 * 
	 * @param panel 
	 */
	public abstract void draw(final JPanel panel);

	/**
	 * Called when user cancels parameter input
	 */
	public abstract void revert();
	
	
	/**
	 * Reset Filter to default values. (Not Necessary)
	 */
	public abstract void reset();
	
	
	/**
	 * Copy Filter Values in case user decides to cancel parameter input
	 */
	public abstract void copy();
	
	/**
	 * Removes entries from the given table in the Local SQLite Database.
	 * <br>
	 * Entries are removed based on the filters current settings.
	 * 
	 * @param table the table that the filter will be applied to
	 * @param log the text area for logging the filter process
	 * @return true if operation is successful, false if unsuccessful
	 */
	public abstract boolean filter(final String table, final JTextArea log);
	
	
	public abstract String getDetails();
	
	
	public abstract boolean logFilter(final int processID,final int filterID);

	/**
	 * Adds an entry to the local database table 'process'.
	 * <br>
	 * This table has the following schema:
	 * <br> int: process_id
	 * <br> int: filter_id
	 * <br> string: filter_table
	 * <br>
	 * This table tracks what filters have been applied to a dataset.
	 * 
	 * @param processID
	 * @param filterID
	 * @return true if operation is successful, false if unsuccessful
	 */
	public boolean logToProcess(final int processID, final int filterID)
	{
		return LocalSQL.update(
				String.format("insert into process values(%d, %d, %s)", 
						processID,
						filterID,
						this.getSQLiteTable()));
	}
	
}


















