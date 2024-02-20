package components;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import guiutil.Function;
import guiutil.Grid;


public class TimeSelector extends TextField
{
	private final Panel rootPanel = new Panel(new GridBagLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	private final Label hourLabel   = new Label("Hour:");
	private final Label minuteLabel = new Label("Minute:");
	
	private final Spinner hourSpinner   = new Spinner(12,  1, 12, 1).initNumberFormat("00");
	private final Spinner minuteSpinner = new Spinner( 0,  0, 59, 1).initNumberFormat("00");

	private final ToggleBar meridiemBar = new ToggleBar(5, "am", "pm").initUpdateFunctions(this::executeFunctions);
	
	private final List<Function> functionsList = new ArrayList<>();
	

	private FieldPopup popup = new FieldPopup(this, rootPanel);
	
	private void updateTextField()
	{
		this.setText(this.getTime());
	}
	
	public TimeSelector(final Function... functions)
	{
		functionsList.add(this::updateTextField);
		
		this.setColumns(8);
		
		for(final Function function: functions)
		{
			functionsList.add(function);
		}
		this.updateTextField();
		
		draw();
		assign();
	}
	
	private void draw()
	{
		rootPanel.setLayout(new GridBagLayout());
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		rootPanel.add(hourLabel  , grid.set(x, y));
		rootPanel.add(hourSpinner, grid.set(++x, y));
		
		rootPanel.add(minuteLabel  , grid.set(--x, ++y));
		rootPanel.add(minuteSpinner, grid.set(++x, y));
		
		rootPanel.add(meridiemBar, grid.set(x, ++y));
	}
	
	private void assign()
	{
		this.hourSpinner  .addChangeListener(e -> executeFunctions());
		this.minuteSpinner.addChangeListener(e -> executeFunctions());
	}
	
	private void executeFunctions()
	{
		for(final Function function: functionsList)
		{
			function.run();
		}
	}
	
	public final int getSeconds()
	{
		int seconds = getMinute() * 60;

		int hours = getHour();
		
		if(meridiemBar.getSelectedItem().equals("am"))
		{
			if(hours == 12)
			{
				hours = 0;
			}
		}
		else
		{
			hours += 12;
		}
		
		seconds += hours * 60 * 60;
		
		return seconds;
	}
	
	public final int getMinute()
	{
		return (int) minuteSpinner.getValue();
	}
	
	public final int getHour()
	{
		return (int) hourSpinner.getValue();
	}
	
	public final String getMeridiem()
	{
		return meridiemBar.getSelectedItem();
	}
	
	public final String getTime()
	{
		return String.format("%d:%02d %s", getHour(), getMinute(), getMeridiem());
	}

}
