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
import javax.swing.UIManager;

import application.Application;
import components.Button;
import components.Dialog;
import components.DragAndDrop;
import components.DragAndDrop.DragPanel;
import components.Frame;
import components.Label;
import components.Panel;
import guiutil.Grid;
import mvc.filters.boundary.BoundaryModel;
import mvc.main.process.builder.BuilderModel;

public abstract class Filter
{
	private static int next = 0;
	
	/**
	 * Tracks order of Filter.
	 */
	public final int row = next++;
	
	
	private static List<Filter> filters = new ArrayList<>();

	
	private final Button addButton    = new Button("\u271A").initToolTop("Add");
	private final Button removeButton = new Button("\u2716").initToolTop("Remove");
	private final Button editButton   = new Button("\u2699").initToolTop("Edit");

	
	public final DragAndDrop<Filter>.DragPanel includedPanel;
	
	public final Panel excludedPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 10, 10))
			.initBackground(() -> UIManager.getColor("TextArea.background"));
	
	private final Dialog dialog;
	
	private final Panel rootPanel = new Panel(new BorderLayout(15, 15)).initBorder(BorderFactory.createEmptyBorder(15,  15,  15,  15));
	
	private final Panel descriptionPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 25))
			.initBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(rootPanel.getForeground(), 1), "Description"));


	public Filter(final BuilderModel model, final String name)
	{
		this.dialog = new Dialog(Application.getFrame(), name, true);
		
		filters.add(this);

		
		includedPanel = model.includedFiltersPanel.new DragPanel(this, false)
		{
			@Override
			public void addContent(final Panel panel)
			{
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
				
				panel.add(Box.createRigidArea(new Dimension(5, 5)));
				panel.add(editButton);
				panel.add(Box.createRigidArea(new Dimension(15, 5)));
				panel.add(new Label(name));
				panel.add(Box.createHorizontalGlue());
				panel.add(removeButton);
				panel.add(Box.createRigidArea(new Dimension(15, 5)));
			}
		};

		
		model.view.addToExcluded(this);
		
		excludedPanel.add(addButton);
		excludedPanel.add(new Label(name));
		
		
		addButton   .addActionListener( e -> model.control.add   (this) );
		removeButton.addActionListener( e -> model.control.remove(this) );
		editButton  .addActionListener( e -> model.control.edit  (this) );
		
	}

	protected void drawDialog()
	{
		final Panel panel = new Panel().initBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(rootPanel.getForeground(), 1), "Controls"));

		drawPanel(panel);
		
		descriptionPanel.add(new Label(getDescription()));
		
		rootPanel.add(descriptionPanel, BorderLayout.NORTH);
		rootPanel.add(panel, BorderLayout.CENTER);

		dialog.addContent(rootPanel);

		dialog.setResizable(false);
	}
	
	/**
	 * Opens a JDialog prompting the user to select the settings
	 * for the Filter.
	 * 
	 * @return true if confirm button is selected, false if not
	 */
	public boolean launch()
	{
		return dialog.launch();
	}

	
	/**
	 * This method returns a string that will be displayed to the user.
	 * The string will describe what the filter option will do to the data set.
	 * 
	 * @return a string describing what the filter will do to the data set
	 */
	protected abstract String getDescription();

	public abstract void drawPanel(Panel panel);

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


















