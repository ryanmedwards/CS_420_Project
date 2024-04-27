package mvc.source;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import guiutil.Displayable;

public class Source implements Displayable<Source>
{
	public static final String SPLITTER = "::";
	
	public enum Type
	{
		OPENSKY_NETWORK("The OpenSky-Network"),
		LOCAL_DATABASE("Local Database"),
		IMPORT_FILE("Imported File");
		
		private final String name;
		
		private Type(final String name)
		{
			this.name = name;
		}

		
		@Override
		public String toString()
		{
			return name;
		}
		
		public static Type getEnum(final String name)
		{
			for(Type s: values())
			{
				if(s.name.equalsIgnoreCase(name))
				{
					return s;
				}
			}
			
			throw new IllegalArgumentException();
		}
	}
	
	public final Type type;
	public final String location;
	
	public Source(final Source.Type type, final String location)
	{
		this.type = type;
		this.location = location;
	}
	
	@Override
	public String toString()
	{
		return type.toString() + SPLITTER + location;
	}

	@Override
	public Object[] toArray() 
	{
		return new Object[] 
		{
			this.type, this.location
		};
	}

	@Override
	public Source toObject(final JTable table, final int row) 
	{
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		
		return new Source
		(
			(Source.Type)tableModel.getValueAt(row, 0),
			(String)tableModel.getValueAt(row, 1)
		);
	}
	
	
	@Override
	public boolean equals(final Object o)
	{
		if(o instanceof Source)
		{
			final Source s = (Source)o;
			
			return this.type.equals(s.type) && this.location.equals(s.location);
		}
		
		return false;
	}
	

	public static int contains(final List<Source> sources, final Source.Type type)
	{
		for(int i = 0; i < sources.size(); ++i)
		{
			if(sources.get(i).type.equals(type))
			{
				return i;
			}
		}
		return -1;
	}

}











