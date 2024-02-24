package mvc.filters.boundary.box;

public class BoxController 
{
	private final BoxModel model;
	
	protected BoxController(final BoxModel model)
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
		final double north = (double) model.northField.getValue();
		final double south = (double) model.southField.getValue();
		final double west  = (double) model. westField.getValue();
		final double east  = (double) model. eastField.getValue();
		
		return String.format("[NSEW] = [ %.2f, %.2f, %.2f, %.2f ]", north, south, west, east);
	}
	
	public double[] getBoundary()
	{
		final double north = (double) model.northField.getValue();
		final double south = (double) model.southField.getValue();
		final double east  = (double) model. eastField.getValue();
		final double west  = (double) model. westField.getValue();
		
		return new double[] { north, south, east, west };
	}
}
