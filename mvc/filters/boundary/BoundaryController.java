package mvc.filters.boundary;

public class BoundaryController 
{
	private final BoundaryModel model;
	
	public BoundaryController(final BoundaryModel model)
	{
		this.model = model;
		
		model.view.draw();
		
		this.assign();
	}
	
	private void assign()
	{
		
	}
	
	public String getDescription()
	{
		final int index = model.menuPanel.getSelectedIndex();
		
		switch(index)
		{
		case 0: return model.boxModel.control.getDescription();
		
		case 1: return model.airportModel.control.getDescription();
		
		default: return "Error!";
		}
	}
	
	
	public double[] getBoundary()
	{
		final int index = model.menuPanel.getSelectedIndex();
		
		switch(index)
		{
		case 0: return model.boxModel.control.getBoundary();
		
		case 1: return model.airportModel.control.getBoundary();
		
		default: return new double[] {0.0, 0.0, 0.0, 0.0};
		}
	}
}
