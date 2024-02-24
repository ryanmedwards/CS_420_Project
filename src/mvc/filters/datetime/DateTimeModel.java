package mvc.filters.datetime;

import java.awt.GridBagLayout;

import application.Application;
import components.DateSelector;
import components.Label;
import components.Panel;
import components.TabPanel;
import components.TimeSelector;

public class DateTimeModel 
{
	
	public final Panel rootPanel = Application.createRootPanel();
	
	public final TabPanel tabPanel = new TabPanel(TabPanel.LEFT);
	
	public final Panel infoPanel = new Panel(new GridBagLayout());
	
	
	public final Label startLabel = new Label("Start:");
	public final Label stopLabel = new Label("Stop:");
	
	public final DateSelector startDate = new DateSelector(2010, 2024);
	public final DateSelector stopDate = new DateSelector(2010, 2024);
	
	public final TimeSelector startTime = new TimeSelector();
	public final TimeSelector stopTime = new TimeSelector();
	
	public final Panel startPanel = new Panel(new GridBagLayout());
	public final Panel stopPanel = new Panel(new GridBagLayout());
	
	
	public final DateTimeView       view    = new DateTimeView(this);
	public final DateTimeController control = new DateTimeController(this);
}
