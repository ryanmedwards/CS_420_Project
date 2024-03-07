package mvc.filters.datetime;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import components.Panel;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;

public class DateTimeFilter extends Filter
{
	protected final DateTimeModel model = new DateTimeModel();


	
	public DateTimeFilter(final FilterModel model) 
	{
		super(model, "Date and Time");

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
	public String getSQLite() 
	{
		return "";
	}

	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
