package mvc.filters;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.Application;
import data.Unit;
import mvc.filters.BoundaryPointFilter.Shape;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class DateTimeFilter extends Filter
{
	protected final DateTimeModel model;

	long start = 0;
	long duration = 0;
	

	
	public DateTimeFilter(final  int id) 
	{
		super(Filter.Type.DATE_TIME, id);
		
		this.model = new DateTimeModel();
		
		super.drawRootPanel();
	}

	@Override
	protected String getDescription() 
	{
		return "<html>"
			 + "<p><p>"
			 + "This filter will create a time range for the data set."
			 + "<br><br>"
			 + "The date and time selected will represent earliest possible time and the "
			 + "duration will determine the lastest possible time."
			 + "<p><p>"
			 + "<html>";
	}

	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new BorderLayout());
		
		final JScrollPane pane = new JScrollPane(model.rootPanel);
		
		pane.setBorder(BorderFactory.createEmptyBorder());
		
		panel.add(pane, BorderLayout.CENTER);
	}

	@Override
	public void revertState() 
	{
		
	}

	@Override
	public void saveState() 
	{
		start = model.getStartTime();
		duration = model.getDuration();
	}


	
	@Override
	public boolean filter(StateVectorList data)
	{
		this.saveState();
		
		return StateVectorOP.filterDateTime(data, start, start + duration);
	}
	

	
	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		return String.format(
				"insert into %s values ( %d, %d, \'%s\', %d, %d );", 
				this.getDBTable(),
				id,
				order,
				applied.toString(),
				start,
				duration);
	}
	
	@Override
	public String getQueryCondition()
	{
		this.saveState();
		
		return FilterQuery.getDateTimeQuery(start, duration);
	}
	
	
	@Override 
	public String getHistory()
	{
		this.saveState();
		
		return FilterHistory.getDateTimeHistory(start, duration);
	}


	
	
}
