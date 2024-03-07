package mvc.main.process.source.opensky;

import java.util.List;

import javax.swing.table.DefaultTableModel;

public class OpenSkyController 
{
	private final OpenSkyModel model;
	
	protected OpenSkyController(final OpenSkyModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.tables.addUpdateFunctions(this::updateTable);
		
		updateTable();
	}
	
	private void updateTable()
	{
		final List<Object[]> data = OpenSkyTables.getStatevectorsData4();
		
		
		final DefaultTableModel tablemodel = (DefaultTableModel) model.table.getModel();

		tablemodel.setRowCount(0);

		for(final Object[] row: data)
		{
			tablemodel.addRow(row);
		}
		
		model.table.revalidate();
		model.table.repaint();
	}
	// Fix
	public String getTable()
	{
		return "state_vectors_data4";
	}
}
