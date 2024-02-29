package mvc.main;



public class MainController
{
	private final MainModel model;
	
	protected MainController(final MainModel model)
	{
		this.model = model;
		
		model.view.draw();
		
		this.assign();
	}
	
	private void assign()
	{
		
	}
}
