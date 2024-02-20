package mvc.main.process.filters;

import java.awt.Dimension;
import java.awt.FlowLayout;

import components.Label;
import components.Panel;
import components.Spinner;

public class RemoveExpiredMessages extends Filter<RemoveExpiredMessages>
{

	private final Spinner delaySpinner = new Spinner();
	
	private int save = 0;
	
	public RemoveExpiredMessages(final FilterModel parent)
	{
		super(parent, "Remove Expired Messages");
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
	protected void revert() 
	{
		delaySpinner.setValue(save);
	}

	@Override
	protected void reset() 
	{
		delaySpinner.setValue(0);
	}

	@Override
	protected void copy() 
	{
		save = (int) delaySpinner.getValue();
	}

	@Override
	public void drawPanel(final Panel panel) 
	{
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		
		panel.add(new Label("Maximum Delay: "));
		panel.add(delaySpinner);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected String getName() {
		return "Remove Expired Messages";
	}
	
}
