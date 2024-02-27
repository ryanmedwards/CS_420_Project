package mvc.filters.expired;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import components.Label;
import components.Panel;
import components.Spinner;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;

public class ExpiredFilter extends Filter
{

	private final JSpinner delaySpinner = new Spinner().initModel(5, 1, 60, 1).get();
	
	private int save = 0;
	
	public ExpiredFilter(final FilterModel model)
	{
		super(model, "Remove Expired Messages");
		
		super.drawDialog();
	}
	
	
	
	
	
	
	@Override
	protected String getDescription() 
	{
		return  "<html>"	
	          + "This filter will remove 'expired' messages. It is possible for a State Vector<br>"
			  + "to contain old or inaccurate positional data. Other features may be updated while the<br>"
	          + "latitude and longitude are not. If simulated, this would make it seem as if the aircraft<br>"
			  + "is teleporting."
			  + "<br>"
	          + "<br>"
	          + "The following comparison will be performed:"
	          + "<br>"
	          + "<br>"
	          + "&emsp;&emsp;&emsp;time - lastposupdate &lt;= \'Maximum Delay\'"
	          + "<br>"
	          + "<br>"
	          + "Entries evaluted to false will be removed."
	          +"<html>";
	}

	@Override
	public void revert() 
	{
		delaySpinner.setValue(save);
	}

	@Override
	public void reset() 
	{
		delaySpinner.setValue(0);
	}

	@Override
	public void copy() 
	{
		save = (int) delaySpinner.getValue();
	}

	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		
		panel.add(new JLabel("Maximum Delay: "));
		panel.add(delaySpinner);
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
