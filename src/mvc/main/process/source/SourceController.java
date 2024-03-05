package mvc.main.process.source;

public class SourceController 
{
	private final SourceModel model;
	
	protected SourceController(final SourceModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.selectButton.addActionListener(e -> model.dialog.launch());
	}
}
