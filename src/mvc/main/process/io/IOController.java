package mvc.main.process.io;

public class IOController 
{
	private final IOModel model;
	
	protected IOController(final IOModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.sourceSelectButton.addActionListener(e -> 
		{
			model.sourceDialog.save();
			
			final boolean confirm = model.sourceDialog.launch();
			
			if(confirm)
			{
				model.sourceField.setText(model.sourceDialog.getSource());
				model.sourceTableField.setText(model.sourceDialog.getTable());
			}
			else
			{
				model.sourceDialog.revert();
			}
		});
		
		model.destinationSelectButton.addActionListener(e ->
		{
			final boolean confirm = model.destinationDialog.launch();
			
			if(confirm)
			{
				model.destinationField.setText("Local Database");
				model.destinationTableField.setText(model.destinationDialog.getTable());
			}
		});
		
		
		model.sourceField.setText(model.sourceDialog.getSource());
		model.sourceTableField.setText(model.sourceDialog.getTable());
	}
	
	
}
