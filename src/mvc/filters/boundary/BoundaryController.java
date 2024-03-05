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
		final int tab = model.menuPanel.getSelectedIndex();
		
		switch(tab)
		{
		case 0: return model.boxModel.control.getDescription();
		
		case 1: return model.airportModel.control.getDescription();
		
		default: return "Error!";
		}
	}
}
