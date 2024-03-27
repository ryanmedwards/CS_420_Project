package mvc.filters.datetime;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicProgressBarUI;

import components.Panel;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;
import sql.FilterTable;
import sql.LocalSQL;

public class DateTimeFilter extends Filter
{
	protected final DateTimeModel model = new DateTimeModel();

	
	public DateTimeFilter(final FilterModel model, final String name) 
	{
		super(model, name);

		super.drawDialog();
	}

	@Override
	protected String getDescription() 
	{
		return "<html>"
			 + "This filter will create a time range for the data set."
			 + "<br><br>"
			 + "The date and time selected will represent earliest possible time and the "
			 + "duration will determine the lastest possible time."
			 + "<html>";
	}

	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new BorderLayout());
		panel.add(model.rootPanel, BorderLayout.CENTER);
	}

	@Override
	public void revert() 
	{

		
	}

	@Override
	public void reset() 
	{

	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean filter(final String table, final JTextArea log)
	{
		log.append("Removing entries outside selected time range...");
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("delete from %s where ", table));
		

		final long start = model.control.getStartTime();
		final long duration = model.control.getDuration();	
		
		
		if(start % 3600 == 0)
		{
			if(duration == 3600)
			{
				sb.append(String.format("hour = %d ", start));
			}
			else
			{
				final long stop = start + duration;
				
				sb.append(String.format("hour >= %d AND ", start));
				sb.append(String.format("hour < %d "     , stop ));
			}
		}
		else
		{
			final long hour = start - (start % 3600);
			
			if(duration == 3600)
			{
				
			}
			else
			{
				
			}
		}
		
		sb.append(";");
		
		return LocalSQL.update(sb.toString());
	}
	
	
	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSQLiteTable()
	{
		return FilterTable.DATETIME;
	}
	
	@Override
	public boolean logFilter(final int processID, final int filterID)
	{
		return true;
		
	}
	
}
