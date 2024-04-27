package mvc.filters;

import java.util.List;

import data.Airline;
import mvc.filters.BoundaryPointFilter.Shape;
import opensky.statevector.StateVector;

public class FilterQuery
{
	public static String getDateTimeQuery(final long start, final long duration)
	{
		final long stop = start + duration;
		
		return String.format(
				"time >= %d and time < %d and hour >= %d and hour < %d ", 
				start,
				stop,
				start - (start % 3600),
				stop - (stop % 3600));
	}
	
	public static String getBoundaryBoxQuery(final double n, final double s, final double e, final double w)
	{
		return String.format(
				"lat <= %f and lat >= %f and lon <= %f and lon >= %f ", 
				n, s, e, w);
	}
	
	public static String getBoundaryCircleQuery(final double clat, final double clon, final double distance)
	{
		
		final double clat_rad = clat * Math.PI / 180.0;
		final double clon_rad = clat * Math.PI / 180.0;

		return String.format(
				"acos( sin ( %f ) * sin ( radians(lat) ) + cos ( %f ) * cos( radians(lat) ) * cos( radians(lon) - %f) ) * 6371.0 <= %f ", 
				clat_rad, clat_rad, clon_rad, distance);
	}
	
	public static String getNullsQuery(int selected)
	{
		final StringBuilder sb = new StringBuilder();

		for(int i = 0; i < StateVector.SIZE; ++i)
		{
			if((selected & 0b1) == 1)
			{
				sb.append(String.format("%s != \'NULL\' AND ", StateVector.FEATURES[i]));
			}
			selected >>>= 1;
		}
		
		sb.replace(sb.length() - 4, sb.length(), "");
		
		return sb.toString();
	}
	
	public static String getDuplicatesQuery(int selected)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(" group by ");
		
		for(int i = 0; i < StateVector.SIZE; ++i)
		{
			if((selected & 0b1) == 1)
			{
				sb.append(String.format("%s,", StateVector.FEATURES[i]));
			}
			selected >>>= 1;
		}
		
		sb.delete(sb.length()-1, sb.length());
		
		return sb.toString();
	}
	
	public static String getExpiredContactQuery(final int delay)
	{
		return String.format("time - lastcontact <= %d ", delay);
	}
	
	public static String getExpiredPositionQuery(final int delay)
	{
		return String.format("time - lastposupdate <= %d ", delay);
	}

	public static String getAirlinesQuery(final List<Airline> airlines)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append("substring(callsign, 1, 3) in (");
		
		for(final Airline airline: airlines)
		{
			sb.append(String.format("\'%s\', ", airline.code));
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		sb.append(")");
		
		return sb.toString();
	}
}




















