package components;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import application.Application;
import guiutil.Displayable;

public class Table<T extends Displayable<T>> extends JInitializer<JTable>
{

	
	private final List<T> items = new LinkedList<>();
	
	
	public Table<T> initSelectionMode(final int mode)
	{
		get().setRowSelectionAllowed(true);
		get().setSelectionMode(mode);
		return this;
	}
	
	public Table<T> initColumnsReordering(final boolean reordering)
	{
		get().getTableHeader().setReorderingAllowed(false);
		return this;
	}
	
	public Table<T> initGridLines(final boolean showGrid)
	{
		get().setShowGrid(showGrid);
		return this;
	}
	
	public Table<T> initHideColumns(final int... cols)
	{
		try
		{
			final TableColumnModel tcm = get().getColumnModel();
			
			for(int i = 0; i < cols.length; ++i)
			{
				tcm.removeColumn(tcm.getColumn(cols[i]));
			}
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
		}
		
		return this;
	}
	
	public Table<T> initModel(final DefaultTableModel model)
	{
		get().setModel(model);
		return this;
	}
	
	public Table<T> initItems(final List<T> items)
	{
		addItems(items);
		return this;
	}
	
	public Table<T> initColumnsWidths(final int... widths)
	{
		try
		{
			final TableColumnModel cm = get().getColumnModel();
			
			for(int i = 0; i < widths.length; ++i)
			{
				cm.getColumn(i).setPreferredWidth(widths[i]);
			}
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
		}
		
		return this;
	}
	
	public void addItem(final T item)
	{
		this.items.add(item);
		
		final DefaultTableModel model = (DefaultTableModel) get().getModel();
		
		model.addRow(item.toArray());
		
		model.fireTableDataChanged();
		get().revalidate();
	}
	
	public void addItems(final List<T> items)
	{
		this.items.addAll(items);
		
		final DefaultTableModel model = (DefaultTableModel) get().getModel();
		
		for(final T item: items)
		{
			model.addRow(item.toArray());
		}
		
		model.fireTableDataChanged();
		get().revalidate();
	}

	public Table<T> initResizable(final int resizable)
	{
		get().setAutoResizeMode(resizable);
		return this;
	}
	
	public List<T> getItems()
	{
		return items;
	}
	
	public T getSelectedItem()
	{
		try
		{
			return items.get(get().getSelectedRow());
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return null;
		}
	}
	
	public List<T> getSelectedItems()
	{
		try
		{
			final List<T> selectedItems = new ArrayList<>();
			
			final int[] selectedRows = get().getSelectedRows();
			
			for(final Integer i: selectedRows)
			{
				selectedItems.add(items.get(i));
			}
			
			return selectedItems;
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public boolean contains(final T item)
	{
		for(final T i: items)
		{
			if(item.equals(i))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	public void setSelectedRows(final int[] rows)
	{
		Arrays.sort(rows);
		
		get().clearSelection();
		
		if(rows.length > 0)
		{
			final ListSelectionModel m = get().getSelectionModel();
			
			m.setSelectionInterval(rows[0], rows[rows.length-1]);

			for(int i = 0; i < rows.length-1; ++i)
			{
				final int dif = rows[i+1] - rows[i];
				if(dif > 1)
				{
					m.removeSelectionInterval(rows[i]+1, rows[i] + dif - 1);
				}
			}
		}
	}
	
	public void removeSelectedRows()
	{
		final int[] rows = get().getSelectedRows();
		
		final DefaultTableModel model = (DefaultTableModel) get().getModel();
		
		for(int i = rows.length - 1; i >= 0; --i)
		{
			this.items.remove(i);
			model.removeRow(rows[i]);
		}
		
		model.fireTableDataChanged();
		
		get().revalidate();
	}
	
	public void clear()
	{
		final DefaultTableModel model = (DefaultTableModel) get().getModel();
		
		model.setRowCount(0);
		
		model.fireTableDataChanged();
		
		get().revalidate();
	}
	
	@Override
	public JScrollPane toScrollPane()
	{
		final JScrollPane pane = new JScrollPane(get());
		
		pane.setPreferredSize(get().getPreferredSize());
		
		return pane;
	}
	
	public Table<T> initPreferredSize(final int x, final int y)
	{
		get().setPreferredSize(new Dimension(x, y));
		return this;
	}
	
	
	@Override
	public JTable create() 
	{
		return new JTable();
	}
}















