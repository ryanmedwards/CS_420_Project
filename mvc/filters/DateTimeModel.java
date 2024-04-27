package mvc.filters;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import components.Spinner;
import components.custom.RootPanel;
import components.custom.datetime.DateSelector;
import components.custom.datetime.TimeSelector;
import components.text.TextField;
import guiutil.Grid;

public class DateTimeModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final JPanel controlPanel = new JPanel(new GridBagLayout());

	protected final JLabel dateLabel     = new JLabel("Date:");
	protected final JLabel timeLabel     = new JLabel("Time:");
	protected final JLabel durationLabel = new JLabel("Duration:");
	
	
	protected final JTextField dateField = new TextField()
											   .initColumns(10)
											   .initEditable(false)
											   .initHorizontalAlignment(JTextField.RIGHT)
											   .get();
	
	protected final JTextField timeField = new TextField()
											   .initColumns(10)
											   .initEditable(false)
											   .initHorizontalAlignment(JTextField.RIGHT)
											   .get();
	
	protected final JTextField durationField = new TextField()
												   .initColumns(10)
											       .initEditable(false)
												   .initHorizontalAlignment(JTextField.RIGHT)
												   .get();
											
	
	protected final DateSelector dateSelector    = new DateSelector(2010, LocalDateTime.now().getYear());
	protected final TimeSelector timeSelector    = new TimeSelector();
	protected final JSpinner     durationSpinner = new Spinner().initModel(1, 1, 24, 1).get();
	
	
	protected DateTimeModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.NORTH);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		
		controlPanel.add(dateLabel, grid.set(  x, y));
		controlPanel.add(dateField, grid.set(++x, y));
		
		controlPanel.add(timeLabel, grid.set(++x, y));
		controlPanel.add(timeField, grid.set(++x, y));
		
		controlPanel.add(durationLabel, grid.set(++x, y));
		controlPanel.add(durationField, grid.set(++x, y));
		
		x = 0;
		y = 1;
		
		grid.setAnchor(Grid.NORTHEAST);
		grid.setInset(30, 10, 10, 10);
		
		controlPanel.add(dateSelector   , grid.set(  x, y, 2, 1)); ++x;
		controlPanel.add(timeSelector   , grid.set(++x, y, 2, 1)); ++x;
		controlPanel.add(durationSpinner, grid.set(++x, y, 2, 1));
		
		
		rootPanel.add(controlPanel, BorderLayout.NORTH);

	}
	
	private void assign()
	{
		dateSelector.addFunction(this::updateDateField);
		
		timeSelector.addFunction(this::updateTimeField);
		
		durationSpinner.addChangeListener(e -> updateDurationField());
		
		updateDateField();
		updateTimeField();
		updateDurationField();
	}
	
	private void updateDateField()
	{
		dateField.setText(dateSelector.getDate());
	}
	
	private void updateTimeField()
	{
		timeField.setText(timeSelector.getTime());
	}
	
	private void updateDurationField()
	{
		final int duration = (int) durationSpinner.getValue();
		
		if(duration == 1)
		{
			durationField.setText(duration + " hour");
		}
		else
		{
			durationField.setText(duration + " hours");
		}
	}
	
	
	public long getStartTime()
	{
		return dateSelector.getUnixTime() + timeSelector.getSeconds();
	}
	
	
	public long getDuration()
	{
		return ((Integer)durationSpinner.getValue()) * 60 * 60;
	}
}
