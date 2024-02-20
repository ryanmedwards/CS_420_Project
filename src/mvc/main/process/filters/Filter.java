package mvc.main.process.filters;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import application.Application;
import components.Button;
import components.Dialog;
import components.Frame;
import components.Label;
import components.Panel;
import guiutil.Grid;

public abstract class Filter<T extends Filter<T>>
{
	private static int next = 1;
	
	private static List<Filter<?>> options = new ArrayList<>();
	
	private static List<Filter<?>> included = new ArrayList<>();

	protected final int row;
	
	protected final String name = getName();

	
	private boolean isIncluded = false;
	
	protected Label label = new Label();
	
	protected Button addButton    = new Button("\u271A");
	protected Button removeButton = new Button("\u2716");
	protected Button upButton     = new Button("\u23F6");
	protected Button downButton   = new Button("\u23F7");
	protected Button editButton   = new Button("\u2699");

	public Filter(final FilterModel model, final String text)
	{
		options.add(this);
		
		removeButton.setToolTipText("Remove");
		upButton    .setToolTipText("Up"    );
		downButton  .setToolTipText("Down"  );
		editButton  .setToolTipText("Edit"  );
		addButton   .setToolTipText("Add"   );
		
		row = next++;
		label.setText(text);
		
		addButton   .addActionListener( e -> model.control.add   (model, this) );
		removeButton.addActionListener( e -> model.control.remove(model, this) );
		upButton    .addActionListener( e -> model.control.up    (model, this) );
		downButton  .addActionListener( e -> model.control.down  (model, this) );
		editButton  .addActionListener( e -> model.control.edit  (model, this) );

		model.view.addToExcluded(model, this);

	}
	
	// Temp Filter Constructor
	protected Filter()
	{
		this.row = -1;
		
		
	}

	
	protected abstract String getName();
	
	protected boolean isIncluded()
	{
		return isIncluded;
	}
	
	protected static void include(final Filter<?> option)
	{
		if(option != null && ! included.contains(option))
		{
			option.isIncluded = true;
			included.add(option);
		}
	}
	
	protected static void exclude(final Filter<?> option)
	{
		if(option != null && included.contains(option))
		{
			option.isIncluded = false;
			included.remove(option);
		}
	}

	protected static int includedCount()
	{
		return included.size();
	}
	
	
	protected static void moveUp(final Filter<?> option)
	{
		if(option.isIncluded)
		{
			int index = -1;
			
			for(int i = 0; i < included.size(); ++i)
			{
				if(included.get(i).equals(option))
				{
					index = i;
					break;
				}
			}
			
			if(index > 0)
			{
				included.remove(index);
				included.add(index - 1, option);
			}
		}
	}
	
	protected static void moveDown(final Filter<?> option)
	{
		if(option.isIncluded)
		{
			int index = -1;
			
			for(int i = 0; i < included.size(); ++i)
			{
				if(included.get(i).equals(option))
				{
					index = i;
					break;
				}
			}
			
			if(index != -1 && index < included.size() - 1)
			{
				included.remove(index);
				included.add(index + 1, option);
			}
		}
	}
	
	protected static List<Filter<?>> getIncludedList()
	{
		return included;
	}
	
	private final Dialog frame = new Dialog(Application.getFrame(), getName(), true);
	
	private boolean dialogResult = false;
	
	private final Panel descriptionPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 25));
	
	private final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15,  15,  15,  15));
	
	private final Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	
	private final Button confirmButton = new Button("Confirm").initActionListener(e ->
	{
		dialogResult = true;
		frame.dispose();
	});
	
	
	private final Button cancelButton  = new Button("Cancel").initActionListener(e ->
	{
		dialogResult = false;
		frame.dispose();
	});
	
	private boolean dialogDrawn = false;
	
	protected void drawDialog()
	{
		
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
		
		final Panel p = new Panel();
		drawPanel(p);
		
		descriptionPanel.add(new Label(getDescription()));
		
		rootPanel.add(descriptionPanel, BorderLayout.NORTH);
		rootPanel.add(p, BorderLayout.CENTER);
		rootPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		
	//	frame.setPreferredSize(this.getPreferredSize());
		
		frame.getContentPane().add(rootPanel);
		
		frame.pack();
		frame.setLocationRelativeTo(Application.getFrame());
		frame.setResizable(false);
		dialogDrawn = true;
	}
	
	
	/**
	 * This method returns a string that will be displayed to the user.
	 * The string will describe what the filter option will do to the data set.
	 * 
	 * @return a string describing what the filter will do to the data set
	 */
	protected abstract String getDescription();

	/**
	 * Called when user cancels parameter input
	 */
	protected abstract void revert();
	
	
	/**
	 * Reset Filter to default values. (Not Necessary)
	 */
	protected abstract void reset();
	
	
	/**
	 * Copy Filter Values in case user decides to cancel parameter input
	 */
	protected abstract void copy();

	
	protected boolean launch()
	{
		if( ! dialogDrawn )
		{
			drawDialog();
		}
		
		copy();
		
		frame.setVisible(true);
		
		Application.refresh();

		if(!dialogResult)
		{
			this.revert();
		}
		
		return dialogResult;
	}

	
	
	public abstract void drawPanel(Panel panel);
	
	public abstract Dimension getPreferredSize();
	
	public abstract void execute();
	
	
}


















