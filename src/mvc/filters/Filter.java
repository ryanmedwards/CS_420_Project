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
import javax.swing.UIManager;

import application.Application;
import components.buttons.Button;
import components.custom.Dialog;
import components.custom.OrderPanel;
import components.custom.OrderPanel.DragPanel;
import components.custom.RootPanel;
import components.Label;
import components.Panel;
import guiutil.Grid;
import mvc.filters.boundary.BoundaryModel;
import mvc.main.process.builder.BuilderModel;

public abstract class Filter
{

	private static int next = 2;
	
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
	
	public void enable()
	{
		model.control.enable(this);
	}
	
	
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
	 * 
	 */
	public abstract void execute();
	
	
	public abstract String getDetails();
	
	

}


















