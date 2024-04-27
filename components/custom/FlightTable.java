package components.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import data.Flight;
import mvc.source.Source;
import opensky.statevector.MessageClassification;

public class FlightTable extends JTable
{
	
	private static final String[] columns = 
	{
		"ICAO Adress", "Callsign", "Start Time", "Stop Time", "Message Count", "Classification", "Source"
	};
	
	
	
	
	public FlightTable()
	{
		this.getTableHeader().setReorderingAllowed(false);
		
		this.showHorizontalLines = true;
		this.showVerticalLines = true;

		this.setModel(new DefaultTableModel()
		{
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
			
		});
		
		final TableColumnModel tcm = this.getColumnModel();
		tcm.removeColumn(tcm.getColumn(6));
		
		
		this.setRowSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	public FlightTable(final List<Flight> flights)
	{
		super(toMatrix(flights), columns);
		this.getTableHeader().setReorderingAllowed(false);
		
		this.showHorizontalLines = true;
		this.showVerticalLines = true;
		

		
		
//		final TableColumnModel columnModel      = this.getColumnModel();
//		
//		final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//		renderer.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		final int[] widths = new int[] { };
//		
//		for(int i = 0; i < columnModel.getColumnCount(); ++i)
//		{
//			//columnModel.getColumn(i).setPreferredWidth(widths[i]);
//			columnModel.getColumn(i).setCellRenderer(renderer);
//		}
		
		final TableColumnModel tcm = this.getColumnModel();
		tcm.removeColumn(tcm.getColumn(6));
		
		
		
		this.setRowSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	}
	
	@Override
	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
	
	
	
	public List<Flight> getSelectedFlights()
	{
		final int[] rows = this.getSelectedRows();
		
		final List<Flight> flights = new ArrayList<>(this.getSelectedRowCount());
		
		for(int i = 0; i < rows.length; ++i)
		{
			int row = rows[i];
			flights.add(new Flight
			(
			    (Source)this.getModel().getValueAt(row, 6),
				this.getValueAt(row, 0).toString(),
				this.getValueAt(row, 1).toString(),
				(int)this.getValueAt(row, 2),
				(int)this.getValueAt(row, 3),
				(int)this.getValueAt(row, 4),
				MessageClassification.getEnum(this.getValueAt(row, 5).toString())
			));
		}
		
		return flights;
	}
	
	public List<Flight> getFlights()
	{
		final List<Flight> flights = new ArrayList<>(this.getRowCount());
		
		for(int i = 0; i < this.getRowCount(); ++i)
		{
			flights.add(new Flight
			(
			    (Source)this.getModel().getValueAt(i, 6),
				this.getValueAt(i, 0).toString(),
				this.getValueAt(i, 1).toString(),
				(int)this.getValueAt(i, 2),
				(int)this.getValueAt(i, 3),
				(int)this.getValueAt(i, 4),
				MessageClassification.getEnum(this.getValueAt(i, 5).toString())
			));
		}
		
		return flights;
	}
	
	
	
	private static Object[][] toMatrix(final List<Flight> flights)
	{
		final Object[][] result = new Object[flights.size()][];
		
		for(int i = 0; i < flights.size(); ++i)
		{
			final Flight f = flights.get(i);
			
			result[i] = new Object[]
			{		
				f.icao24, f.callsign, f.start, f.stop, f.messageCount, f.type
			};
		}
		
		return result;
	}
	
	
	public void addFlights(final List<Flight> flights)
	{
		final DefaultTableModel model = (DefaultTableModel) this.getModel();

		for(Object[] row: toMatrix(flights))
		{
			model.addRow(row);
		}
		model.fireTableDataChanged();
		
		this.revalidate();
	}
	
	
	public void removeSelectedRows()
	{
		final int[] rows = this.getSelectedRows();
		
		final DefaultTableModel model = (DefaultTableModel) this.getModel();
		
		for(int i = rows.length - 1; i >= 0; --i)
		{
			model.removeRow(rows[i]);
		}
		
		model.fireTableDataChanged();
		
		this.revalidate();
	}
	
	
	
}
