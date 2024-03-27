package mvc.main.process.io;

import mvc.main.process.io.source.Source;

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
			model.sourceModel.control.save();
			
			final boolean confirm = model.sourceModel.control.launch();
			
			if(confirm)
			{
				this.updateSource();
			}
			else
			{
				model.sourceModel.control.revert();
			}
		});
		
		model.destinationSelectButton.addActionListener(e ->
		{
			final boolean confirm = model.destinationModel.control.launch();
			
			if(confirm)
			{
				model.destinationField.setText("Local Database");
				model.destinationTableField.setText(model.destinationModel.control.getTable());
			}
		});
		

		this.updateSource();
	}
	
	private void updateSource()
	{
		model.sourceField.setText(model.sourceModel.control.getSource().name);
		
		if(model.sourceModel.control.hasValidTable())
		{
			model.sourceTableField.setText(model.sourceModel.control.getTable());
		}
	}
	
	public boolean hasValidSource()
	{
		final boolean hasValidSource = getSource() != null;
		
		final boolean hasValidSourceTable = model.sourceModel.control.hasValidTable();
		
		return hasValidSource && hasValidSourceTable;
	}
	
	public Source getSource()
	{
		return model.sourceModel.control.getSource();
	}
	
	public String getSourceTable() throws IndexOutOfBoundsException
	{
		return model.sourceModel.control.getTable();
	}
	
	public String getDestination()
	{
		return model.destinationModel.control.getDestination();
	}
	
	public String getDestinationTable()
	{
		return model.destinationModel.control.getTable();
	}
	
}
