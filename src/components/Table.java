package components;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable
{
	public Table(final String[] columns)
	{
		final DefaultTableModel model = new DefaultTableModel();
		
		for(final String column: columns)
		{
			model.addColumn(column);
		}
		
		this.setModel(model);
	}
	
	public void addData(final Object[][] data)
	{
		final DefaultTableModel model = (DefaultTableModel) this.getModel();

		model.setRowCount(0);
		
		for(final Object[] row: data)
		{
			model.addRow(row);
		}
		model.fireTableDataChanged();
	}
	
	public Table initButtonColumn(final int col)
	{
		this.getColumnModel().getColumn(col).setCellRenderer(new ButtonCellRenderer());
		
		return this;
	}
		
}	


class TableButton extends JButton
{
	public TableButton(String name)
	{
		super(name);
	}
}



class ButtonCellRenderer extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object o, boolean b1, boolean b2, int i1, int i2
			)
	{
		Component comp = super.getTableCellRendererComponent(table, o, b1, b2, i1, i2);
		
		Panel p = new Panel();
		
		p.setBackground(table.getBackground());
		
		p.add(new TableButton("Add"));
		
		return p;
	}
}

class ButtonCellEditor extends DefaultCellEditor
{
	public ButtonCellEditor(JCheckBox checkBox) {
		super(checkBox);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getTableCellEditorComponent(
			JTable table, Object o, boolean b1, int i1, int i2
			)
	{
		Component comp = super.getTableCellEditorComponent(table, o, b1, i1, i2);
		
		TableButton b = (TableButton) comp;
		
		
		
		return b;
	}
}







