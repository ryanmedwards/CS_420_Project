package data;

public class Airport
{
	
	private final String name;
	private final double lat;
	private final double lon;
	
	public Airport(final String name, final double lat, final double lon)
	{
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	public final String getName()
	{
		return name;
	}
	
	public final double getLat()
	{
		return lat;
	}
	
	public final double getLon()
	{
		return lon;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
