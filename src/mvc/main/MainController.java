package mvc.main;

import mvc.Controller;
import mvc.Model;



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
