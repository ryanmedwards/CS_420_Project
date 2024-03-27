package mvc.main.process.io.source.opensky;

import java.util.ArrayList;
import java.util.List;

public class OpenSkyTables 
{
	private static List<Object[]> statevectorsData4 = new ArrayList<>();

	static
	{
		statevectorsData4.add(new Object[]{"time", "int"});
		statevectorsData4.add(new Object[]{"icao24", "string"});
		statevectorsData4.add(new Object[]{"lat", "double"});
		statevectorsData4.add(new Object[]{"lon", "double"});
		statevectorsData4.add(new Object[]{"velocity", "double"});
		statevectorsData4.add(new Object[]{"heading", "double"});
		statevectorsData4.add(new Object[]{"vertrate", "double"});
		statevectorsData4.add(new Object[]{"callsign", "string"});
		statevectorsData4.add(new Object[]{"onground", "boolean"});
		statevectorsData4.add(new Object[]{"alert", "boolean"});
		statevectorsData4.add(new Object[]{"spi", "boolean"});
		statevectorsData4.add(new Object[]{"squawk", "string"});
		statevectorsData4.add(new Object[]{"baroltitude", "double"});
		statevectorsData4.add(new Object[]{"geoaltitude", "double"});
		statevectorsData4.add(new Object[]{"lastposupdate", "double"});
		statevectorsData4.add(new Object[]{"lastcontact", "double"});
		statevectorsData4.add(new Object[]{"hour", "int"});

	};
	
	
	
	protected static List<Object[]> getStatevectorsData4()
	{
		return statevectorsData4;
	}
	
	
	
}
