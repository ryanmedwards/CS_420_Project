package mvc.filters.datetime;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import components.custom.RootPanel;
import components.custom.datetime.DateSelector;
import components.custom.datetime.TimeSelector;

public class DateTimeModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	public final JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.LEFT);
	
	public final JPanel infoPanel = new JPanel(new GridBagLayout());
	
	
	public final JLabel startLabel = new JLabel("Start:");
	public final JLabel stopLabel  = new JLabel("Stop:");
	
	public final DateSelector startDate = new DateSelector(2010, 2055);
	public final DateSelector stopDate = new DateSelector(2010, 2024);
	
	public final TimeSelector startTime = new TimeSelector();
	public final TimeSelector stopTime = new TimeSelector();
	
	public final JPanel startPanel = new JPanel(new GridBagLayout());
	public final JPanel stopPanel  = new JPanel(new GridBagLayout());
	
	
	public final DateTimeView       view    = new DateTimeView(this);
	public final DateTimeController control = new DateTimeController(this);
}
