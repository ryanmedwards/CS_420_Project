package mvc.main.process.io.source;

public class SourceController 
{
	private SourceModel model;
	
	protected SourceController(final SourceModel model) 
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		
	}
	
	public boolean hasValidTable()
	{
		if(this.getSource() == Source.LOCAL_DATABASE)
		{
			return model.localModel.control.hasTables();
		}
		
		return true;
	}
	
	public Source getSource()
	{
		final int index = model.tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return Source.OPENSKY_NETWORK;
		
		case 1: return Source.LOCAL_DATABASE;
		
		case 2: return Source.CSV_FILE;
		}
		
		return null;
	}
	
	public String getTable() throws IndexOutOfBoundsException
	{
		final int index = model.tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return model.openSkyModel.control.getTable();
		
		case 1: return model.localModel.control.getTable();
			
		default: return "Error";
		}
		
	}
	
	public boolean launch()
	{
		return model.dialog.launch();
	}
	
	public void save()
	{
		model.savedSource = getSource();
		
		if(this.hasValidTable())
		{
			model.savedTable = getTable();
		}
	}
	
	public void revert()
	{
		if(model.savedSource.equals(Source.OPENSKY_NETWORK))
		{
			model.tabPane.setSelectedIndex(0);		
		}
		else if(model.savedSource.equals(Source.LOCAL_DATABASE))
		{
			model.tabPane.setSelectedIndex(1);
			
			if(this.hasValidTable())
			{
				model.localModel.control.setSelectedTable(getTable());
			}
		}
		else
		{
			System.err.println("Failed to save Source Dialog.");
		}
	}
	
}
