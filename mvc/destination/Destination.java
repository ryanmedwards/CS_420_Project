package mvc.destination;

import mvc.source.Source;

public class Destination 
{
	public static final String SPLITTER = "::";
	
	public enum Type
	{
		LOCAL_DATABASE("Local Database"),
		FILE("Export File");
		
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
		
		public static Destination.Type getEnum(final String name)
		{
			for(Destination.Type d: values())
			{
				if(d.name.equalsIgnoreCase(name))
				{
					return d;
				}
			}
			
			throw new IllegalArgumentException();
		}
	}
	
	public final Type type;
	public final String location;
	
	public Destination(final Destination.Type type, final String location)
	{
		this.type = type;
		this.location = location;
	}
	
	@Override
	public String toString()
	{
		return type.toString() + SPLITTER + location;
	}
	
	
	public Source toSource()
	{
		switch(this.type)
		{
		case FILE: return new Source(Source.Type.IMPORT_FILE, location);
		case LOCAL_DATABASE: return new Source(Source.Type.LOCAL_DATABASE, location);
		}
		return null;
	}	
}





