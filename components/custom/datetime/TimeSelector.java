package components.custom.datetime;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import components.Spinner;
import components.custom.ToggleBar;
import guiutil.Function;
import guiutil.Grid;



public class TimeSelector extends JPanel
{
	private final JLabel hourLabel   = new JLabel("Hour:");
	private final JLabel minuteLabel = new JLabel("Minute:");
	
	private final JSpinner hourSpinner   = new Spinner().initModel(12, 1, 12, 1).initNumberFormat( "0").get();
	private final JSpinner minuteSpinner = new Spinner().initModel( 0, 0, 59, 1).initNumberFormat("00").get();

	private final ToggleBar<String> meridiemBar = new ToggleBar<String>(5, "am", "pm").initUpdateFunctions(this::executeFunctions);
	
	private final List<Function> functionsList = new ArrayList<>();

	
	public TimeSelector(final Function... functions)
	{

		for(final Function function: functions)
		{
			functionsList.add(function);
		}

		draw();
		assign();
	}
	
	private void draw()
	{
		this.setLayout(new GridBagLayout());
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		
		this.add(  hourLabel, grid.set(  x, y));
		this.add(hourSpinner, grid.set(++x, y));
		
		this.add(minuteLabel  , grid.set(--x, ++y));
		this.add(minuteSpinner, grid.set(++x, y));
		
		grid.setAnchor(Grid.EAST);
		this.add(meridiemBar, grid.set(x, ++y));
	}
	
	private void assign()
	{
		this.hourSpinner  .addChangeListener(e -> executeFunctions());
		this.minuteSpinner.addChangeListener(e -> executeFunctions());
	}
	
	
	public void addFunction(final Function function)
	{
		this.functionsList.add(function);
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
			if(hours != 12)
			{
				hours += 12;
			}
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
