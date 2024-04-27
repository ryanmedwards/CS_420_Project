package opensky;

import java.util.ArrayList;
import java.util.List;

public enum OpenSkyTable 
{
	STATE_VECTORS_DATA4("minio.osky.state_vectors_data4");
	
	private final String table;
	
	private OpenSkyTable(final String table)
	{
		this.table = table;
	}
	
	@Override
	public String toString()
	{
		return table;
	}
	
	public List<Object[]> getSchema()
	{
		switch(this)
		{
		case STATE_VECTORS_DATA4: return getStateVectorsData4Schema();
		
		default: return new ArrayList<>();
		}
	}

	private static List<Object[]> getStateVectorsData4Schema() 
	{
		final List<Object[]> result = new ArrayList<>();
		
		result.add(new Object[]{"time", "int"});
		result.add(new Object[]{"icao24", "string"});
		result.add(new Object[]{"lat", "double"});
		result.add(new Object[]{"lon", "double"});
		result.add(new Object[]{"velocity", "double"});
		result.add(new Object[]{"heading", "double"});
		result.add(new Object[]{"vertrate", "double"});
		result.add(new Object[]{"callsign", "string"});
		result.add(new Object[]{"onground", "boolean"});
		result.add(new Object[]{"alert", "boolean"});
		result.add(new Object[]{"spi", "boolean"});
		result.add(new Object[]{"squawk", "string"});
		result.add(new Object[]{"baroltitude", "double"});
		result.add(new Object[]{"geoaltitude", "double"});
		result.add(new Object[]{"lastposupdate", "double"});
		result.add(new Object[]{"lastcontact", "double"});
		result.add(new Object[]{"hour", "int"});
		
		return result;
	}
	
	public static OpenSkyTable getEnum(final String name)
	{
		for(OpenSkyTable t: values())
		{
			if(t.table.equalsIgnoreCase(name))
			{
				return t;
			}
		}
		
		throw new IllegalArgumentException();
	}
}
