package components.custom.datetime;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import components.Label;
import components.Panel;
import components.Spinner;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import guiutil.Function;
import guiutil.Grid;





public class TimeSelector extends JPanel
{
	private final RootPanel rootPanel = new RootPanel();
	
	private final Label hourLabel   = new Label("Hour:");
	private final Label minuteLabel = new Label("Minute:");
	
	private final Spinner hourSpinner   = new Spinner().initModel(12, 1, 12, 1).initNumberFormat( "0");
	private final Spinner minuteSpinner = new Spinner().initModel( 0, 0, 59, 1).initNumberFormat("00");

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
		rootPanel.get().setLayout(new GridBagLayout());
		
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		
		rootPanel.get().add(  hourLabel.get(), grid.set(  x, y));
		rootPanel.get().add(hourSpinner.get(), grid.set(++x, y));
		
		rootPanel.get().add(minuteLabel.get()  , grid.set(--x, ++y));
		rootPanel.get().add(minuteSpinner.get(), grid.set(++x, y));
		
		grid.setAnchor(Grid.EAST);
		rootPanel.get().add(meridiemBar, grid.set(x, ++y));
	}
	
	private void assign()
	{
		this.hourSpinner.get()  .addChangeListener(e -> executeFunctions());
		this.minuteSpinner.get().addChangeListener(e -> executeFunctions());
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
		return (int) minuteSpinner.get().getValue();
	}
	
	public final int getHour()
	{
		return (int) hourSpinner.get().getValue();
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
