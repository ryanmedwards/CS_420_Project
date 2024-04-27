package mvc.filters;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import application.Application;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class BoundaryBoxFilter extends Filter
{
	private final BoundaryBoxModel model = new BoundaryBoxModel();
	
	private double north = 0.0;
	private double south = 0.0;
	private double east = 0.0;
	private double west = 0.0;
	
	
	public BoundaryBoxFilter(final int id) 
	{
		super(Filter.Type.BOUNDARY_BOX, id);
		
		super.drawRootPanel();
	}

	public JPanel getRawRootPanel()
	{
		return model.rootPanel;
	}


	@Override
	protected String getDescription() 
	{
		return "<html>"
		     + "<p><p>"
		     + "Reduce the boundary of the data set using north, south, east, and west limits."
		     + "<p><p>"
		     + "<html>";
	}

	@Override
	public void draw(JPanel panel)
	{
		panel.setLayout(new BorderLayout());
		
		panel.add(model.rootPanel, BorderLayout.WEST);
	}

	@Override
	public void revertState() 
	{
		model.northField.setValue(north);
		model.southField.setValue(south);
		model.eastField.setValue(east);
		model.westField.setValue(west);
	}


	@Override
	public void saveState() 
	{
		north = (double) model.northField.getValue();
		south = (double) model.southField.getValue();
		east  = (double) model. eastField.getValue();
		west  = (double) model. westField.getValue();
	}

	@Override
	public boolean filter(StateVectorList data) 
	{
		this.saveState();
		
		return StateVectorOP.filterBoundaryBox(data, new double[] {north, south, east, west});
	}
	


	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		return String.format(
				"insert into %s values ( %d, %d, \'%s\', %f, %f, %f, %f );", 
				this.getDBTable(),
				id, 
				order,
				applied.toString(),
				north,
				south,
				east,
				west);
	}

	@Override
	public String getQueryCondition()
	{
		this.saveState();
		
		return FilterQuery.getBoundaryBoxQuery(north, south, east, west);
	}

	@Override
	public String getHistory()
	{
		this.saveState();
		
		return FilterHistory.getBoundaryBoxHistory(north, south, east, west);
	}
}






















