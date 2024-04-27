package data;

import mvc.filters.Filter.DBTable;

public enum Unit 
{
	MILES("Miles", 1.609),
	KILOMETERS("Kilometers", 1.0);
	
	final String name;
	final double convToKM;
	
	private Unit(final String name, final double convToKM)
	{
		this.name = name;
		this.convToKM = convToKM;
	}
	
	public static Unit getEnum(final String name)
	{
		for(Unit d: values())
		{
			if(d.name.equalsIgnoreCase(name))
			{
				return d;
			}
		}	
		throw new IllegalArgumentException();
	}
	
	public double toKilometers(final double distance)
	{
		return distance * this.convToKM;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
