package data;

import javax.swing.JTable;

import application.Application;
import guiutil.Displayable;

public class Airline implements Displayable<Airline>
{
	public final String name;
	public final String code;
	
	public Airline(final String name, final String code)
	{
		this.name = name;
		this.code = code;
	}

	@Override
	public Object[] toArray() 
	{
		return new Object[] {this.name, this.code};
	}
	
	@Override
	public Airline toObject(final JTable table, final int row)
	{
		try
		{
			return new Airline
			(
				(String) table.getValueAt(row, 0), 
				(String) table.getValueAt(row, 1)
			);
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return new Airline("", "");
		}
	}
}
