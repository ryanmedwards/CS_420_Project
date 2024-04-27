package guiutil;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel
{
	private final String[] columns;
	
	public MyTableModel(final String... columns)
	{
		this.columns = columns;
	}
	
	@Override
    public int getColumnCount() 
	{
         return columns.length;
    }
	
	@Override
	public String getColumnName(int index)
	{
		return columns[index];
	}
	
	@Override
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
}
