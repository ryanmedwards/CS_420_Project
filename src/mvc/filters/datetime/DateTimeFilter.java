package mvc.filters.datetime;

import components.Panel;
import mvc.filters.Filter;
import mvc.main.process.builder.BuilderModel;

public class DateTimeFilter extends Filter
{

	final DateTimeModel model = new DateTimeModel();
	
	public DateTimeFilter(BuilderModel model) 
	{
		super(model, "Date and Time");

		super.drawDialog();
	}

	@Override
	protected String getDescription() 
	{

		return null;
	}

	@Override
	public void drawPanel(Panel panel) 
	{

		panel.add(model.rootPanel);
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
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
