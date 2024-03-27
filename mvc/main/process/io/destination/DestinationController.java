package mvc.main.process.io.destination;

public class DestinationController 
{
	private DestinationModel model;
	
	protected DestinationController(final DestinationModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		
	}	
	
	public String getDestination()
	{
		final int index = model.tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return "Local Database";
		case 1: return "Export";
		
		default: return "error";
		}
				
	}
	
	public String getTable()
	{
		final int index = model.tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return model.localModel.control.getTable();
		
		case 1: return model.exportModel.control.getTable();
		
		default: return "No Table Selected";
		}
	}
	
	
	public boolean launch()
	{
		return model.dialog.launch();
	}
}
