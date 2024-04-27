package opensky.statevector;

public enum MessageClassification 
{
	AUTHENTIC("Authentic", 0),
	PATH_MODIFICATION("Path Modification", 1),
	GHOST_INJECTION("Ghost Injection", 2),
	VELOCITY_DRIFT("Velocity Drift", 3);
	
	private final String name;
	private final int id;
	
	private MessageClassification(final String name, final int id)
	{
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	public int toInt()
	{
		return id;
	}
	
	public static MessageClassification getEnum(final int id)
	{
		for(MessageClassification mc: values())
		{
			if(mc.id == id)
			{
				return mc;
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	public static MessageClassification getEnum(final String name)
	{
		for(MessageClassification mc: values())
		{
			if(mc.name.equalsIgnoreCase(name))
			{
				return mc;
			}
		}
		
		throw new IllegalArgumentException();
	}

}
