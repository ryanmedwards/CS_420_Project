package mvc.filters;

import java.util.List;

import data.Airline;
import data.Unit;
import mvc.filters.BoundaryPointFilter.Shape;
import opensky.statevector.StateVector;

public class FilterHistory 
{
	public static String getDateTimeHistory(final long start, final long duration)
	{
		return String.format(
				"Limited the time range from ", 
				start);
	}
	
	public static String getBoundaryBoxHistory(final double n, final double s, final double e, final double w)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Remove entries outside of boundary box:");
		sb.append("\nNorth: " + n);
		sb.append("\nSouth: " + s);
		sb.append("\nEast: " + e);
		sb.append("\nWest: " + w);
		
		return sb.toString();
	}
	
	public static String getBoundaryPointHistory(final String name, final double distance, final Unit unit, final Shape shape)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Removed entries based on a center point:");
		
		sb.append("\nName: " + name);
		sb.append("\nDistance: " + distance);
		sb.append("\nUnit: " + unit.toString());
		sb.append("\nShape: " + shape.toString());
		
		return sb.toString();
	}
	
	public static String getNullsHistory(int selected)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Removed entries with null values in the following columns:");
		
		for(int i = 0; i < StateVector.SIZE; ++i)
		{
			if((selected & 0b1) == 1)
			{
				sb.append("\n" + StateVector.FEATURES[i]);
			}
			selected >>>= 1;
		}
		
		return sb.toString();
	}
	
	public static String getDuplicatesHistory(int selected)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Removed duplicate entries based on the following columns:");
		
		for(int i = 0; i < StateVector.SIZE; ++i)
		{
			if((selected & 0b1) == 1)
			{
				sb.append("\n" + StateVector.FEATURES[i]);
			}
			selected >>>= 1;
		}
		
		return sb.toString();
	}
	
	public static String getExpiredHistory(final int lcd, final int lpd)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Removed expired entries based on the following delay(s):");
		
		if(lcd > 0) sb.append("\nMaximum lastcontact delay: " + lcd);
		
		if(lpd > 0) sb.append("\nMaximum lastposupdate delay: " + lpd);

		return sb.toString();
	}

	public static String getAirlinesHistory(final List<Airline> airlines)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Kept entries from the following airlines:");
		for(final Airline airline: airlines)
		{
			sb.append(String.format("\n%s", airline.name));
		}
		
		return sb.toString();
	}
}




















