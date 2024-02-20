package opensky.statevector;

import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import components.Table;

public class StateVectorTable extends Table
{

	private final static String[] columns = loadColumns();
	
	public StateVectorTable() 
	{
		super(columns);
		this.getTableHeader().setReorderingAllowed(false);
		this.setEnabled(false);
		this.setFocusable(false);
		this.showHorizontalLines = true;
		this.showVerticalLines = true;
		
		//this.setPreferredSize(new Dimension(1000, 500));
		
		final TableColumnModel columnModel      = this.getColumnModel();
		
		final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		final int[] widths = new int[] { 40, 90, 65, 125, 130, 125, 130, 130, 75, 80, 70, 70, 70, 160, 135, 145, 145, 90, 50};
		
		for(int i = 0; i < columnModel.getColumnCount(); ++i)
		{
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
			columnModel.getColumn(i).setCellRenderer(renderer);
		}
		
		this.setAutoResizeMode(Table.AUTO_RESIZE_OFF);
	}
	

	public void addData(final List<Object[]> data)
	{
		
		final DefaultTableModel model = (DefaultTableModel) this.getModel();

		model.setRowCount(0);
		
		for(int i = 0; i < data.size(); ++i)
		{
			model.addRow(data.get(i));
		}
		model.fireTableDataChanged();
		
		this.revalidate();
	}
	
	private static String[] loadColumns()
	{
		final String filepath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "unfiltered_table_header.csv";

		try
		{
			final BufferedReader br = new BufferedReader(new FileReader(filepath));
			
			return br.readLine().split(",");
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return new String[0];
	}

}
