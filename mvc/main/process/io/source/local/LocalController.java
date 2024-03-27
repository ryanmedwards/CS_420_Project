package mvc.main.process.io.source.local;

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
		model.tablesBar.addUpdateFunctions(() ->
		{
			this.update();
		});

		this.update();
	}
	
	private void update()
	{
		if(model.tablesBar.hasItems())
		{
			final LocalTable selectedTable = model.tablesBar.getSelectedItem();
			
			updateHistory(selectedTable);
			
			updateDescription(selectedTable);
		}
	}
	
	
	private void updateHistory(final LocalTable table)
	{
		model.historyTextArea.setText(table.getHistory());
		
		model.historyTextArea.revalidate();
		model.historyTextArea.repaint();
	}
	
	private void updateDescription(final LocalTable table)
	{
		model.sourceField.setText(table.source);
		model.tableField.setText(table.table);
		model.countField.setText(table.count + "");
		
		model.decriptionPanel.revalidate();
		model.decriptionPanel.repaint();
	}
	
	public boolean hasTables()
	{
		return model.tablesBar.hasItems();
	}
	
	public String getTable() throws IndexOutOfBoundsException
	{
		return model.tablesBar.getSelectedItem().name;
	}
	
	public void setSelectedTable(final String name)
	{
		for(final LocalTable table: model.localTables)
		{
			if(table.name.equals(name))
			{
				model.tablesBar.setSelectedIndex(table.toggleBarID);
				return;
			}
		}
		
		System.err.println("Could not find the specified table: " + name);
	}
}
