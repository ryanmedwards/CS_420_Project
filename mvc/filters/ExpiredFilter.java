package mvc.filters;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import application.Application;
import components.Spinner;
import guiutil.Grid;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class ExpiredFilter extends Filter
{
	
	
	private final JSpinner lastcontactSpinner = new Spinner().initModel(5, 1, 60, 1).get();
	
	private final JSpinner lastposupdateSpinner = new Spinner().initModel(5, 1, 60, 1).get();
	
	private final JCheckBox lastcontactCheckBox = new JCheckBox();
	private final JCheckBox lastposupdateCheckBox = new JCheckBox();
	
	private int lastcontactDelay = 5;
	private int lastposupdateDelay = 5;
	private boolean lastcontactSelected = true;
	private boolean lastposupdateSelected = true;


	public ExpiredFilter(final int id)
	{
		super(Filter.Type.EXPIRED, id);
		
		super.drawRootPanel();
		
		
		lastcontactCheckBox.addActionListener(e ->
		{
			this.lastcontactSpinner.setEnabled(this.lastcontactCheckBox.isSelected());
		});
		
		lastposupdateCheckBox.addActionListener(e ->
		{
			this.lastposupdateSpinner.setEnabled(this.lastposupdateCheckBox.isSelected());
		});
		
		lastcontactCheckBox.setSelected(lastcontactSelected);
		lastposupdateCheckBox.setSelected(lastposupdateSelected);
	}
	

	@Override
	protected String getDescription() 
	{
		return  "<html>"	
	          + "This filter will remove 'expired' messages."
			  + "<br><br>"
	          + "The following comparisons will be performed:"
	          + "<br><br>"
	          + "&emsp;&emsp;&emsp;time - lastcontact &lt;= \'Maximum lastcontact Delay\'"
	          + "<br><br>"
	          + "&emsp;&emsp;&emsp;time - lastposupdate &lt;= \'Maximum lastposupdate Delay\'"
	          + "<p><p>"
	          +"<html>";
	}

	@Override
	public void revertState() 
	{
		lastcontactSpinner.setValue(lastcontactDelay);
		lastposupdateSpinner.setValue(lastposupdateDelay);
		
		lastcontactCheckBox.setSelected(lastcontactSelected);
		lastposupdateCheckBox.setSelected(lastposupdateSelected);
	}


	@Override
	public void saveState() 
	{
		lastcontactDelay = (int) lastcontactSpinner.getValue();
		lastposupdateDelay = (int) lastposupdateSpinner.getValue();
		
		lastcontactSelected = lastcontactCheckBox.isSelected();
		lastposupdateSelected = lastposupdateCheckBox.isSelected();
	}

	@Override
	public void draw(final JPanel panel) 
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		
		panel.setLayout(new BorderLayout());

		final JPanel northPanel = new JPanel(new GridBagLayout());
		final JPanel westPanel = new JPanel(new BorderLayout());
		

		northPanel.add(lastcontactCheckBox, grid.set(x, y));
		northPanel.add(new JLabel("Maximum lastcontact Delay: "), grid.set(++x, y));
		northPanel.add(lastcontactSpinner, grid.set(++x, y));
		
		x = 0;
		northPanel.add(lastposupdateCheckBox, grid.set(x, ++y));
		northPanel.add(new JLabel("Maximum lastposupdate Delay: "), grid.set(++x, y));
		northPanel.add(lastposupdateSpinner, grid.set(++x, y));
		
		
		westPanel.add(northPanel, BorderLayout.NORTH);
		
		
		panel.add(westPanel, BorderLayout.WEST);
	}

	@Override
	public boolean filter(StateVectorList data)
	{
		if(lastcontactSelected && lastposupdateSelected)
		{
			return StateVectorOP.filterExpired(data, lastcontactDelay) && StateVectorOP.filterPosExpired(data, lastposupdateDelay);
		}
		else if(lastcontactSelected)
		{
			return StateVectorOP.filterExpired(data, lastcontactDelay);
		}
		else if(lastposupdateSelected)
		{
			return StateVectorOP.filterPosExpired(data, lastposupdateDelay);
		}
		else
		{
			return true; // Probably should figure out a good way to prevent this, but this project needs to get done
		}
	}

	
	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		final int lcd = (lastcontactSelected) ? lastcontactDelay : -1;
		final int lpd = (lastposupdateSelected) ? lastposupdateDelay : -1;
		
		return String.format(
				"insert into %s values ( %d, %d, \'%s\', %d, %d);", 
				this.getDBTable(),
				id,
				order,
				applied.toString(),
				lcd,
				lpd);
	}
	
	@Override
	public String getQueryCondition()
	{
		this.saveState();

		if(this.lastcontactSelected && this.lastposupdateSelected) 
		{
			return FilterQuery.getExpiredContactQuery(lastcontactDelay)
					+ " and " +
					FilterQuery.getExpiredPositionQuery(lastposupdateDelay);
		}
		else if(this.lastcontactSelected)
		{
			return FilterQuery.getExpiredContactQuery(lastcontactDelay);
		}
		else if(this.lastposupdateSelected)
		{
			return FilterQuery.getExpiredPositionQuery(lastposupdateDelay);
		}
		
		return "";
	}
	
	@Override
	public String getHistory()
	{
		this.saveState();
		
		return FilterHistory.getExpiredHistory(lastcontactDelay, lastposupdateDelay);
	}

}
