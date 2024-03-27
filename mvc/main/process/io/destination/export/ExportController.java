package mvc.main.process.io.destination.export;

public class ExportController 
{
	private final ExportModel model;
	
	protected ExportController(final ExportModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		
	}
	
	public String getTable()
	{
		return model.fileField.getText();
	}
}
