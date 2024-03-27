package components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table extends JInitializer<JTable>
{

	
	public Table initColumns(final String... columns)
	{
		final DefaultTableModel model = (DefaultTableModel) get().getModel();
		
		for(final String column: columns)
		{
			model.addColumn(column);
		}
		
		return this;
	}
	
	
	
	@Override
	public JTable create() 
	{
		return new JTable();
	}

}
