package mvc.main.process.io.destination.local;

public class LocalController 
{
	private final LocalModel model;
	
	protected LocalController(final LocalModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		
	}
	
	public String getTable()
	{
		return model.tableField.getText();
	}
}
