package data;

import javax.swing.JTable;

import application.Application;
import guiutil.Displayable;

public class CenterPoint implements Displayable<CenterPoint>
{
	public final int pointID;
	public final String name;
	public final String shortName;
	public final double lat;
	public final double lon;
	public final String city;
	public final String type;
	public final String state;
	public final String country;
	public final String timezone;
	public final int utcTimeDif;
	
	
	public CenterPoint(
			final int pointID,
			final String name,
			final String shortName,
			final double lat, 
			final double lon,
			final String city,
			final String type,
			final String state,
			final String country,
			final String timezone,
			final int utcTimeDif)
	{
		this.pointID = pointID;
		this.name = name;
		this.shortName = shortName;
		this.lat = lat;
		this.lon = lon;
		this.city = city;
		this.type = type;
		this.state = state;
		this.country = country;
		this.timezone = timezone;
		this.utcTimeDif = utcTimeDif;
	}


	@Override
	public Object[] toArray() 
	{
		return new Object[]
		{
			this.shortName, 
			(this.city + ", " + this.state), 
			this.country, 
			this.type	
		};
	}


	@Override
	public CenterPoint toObject(final JTable table, final int row) 
	{
		try
		{
			return new CenterPoint
			(
				(int)table.getValueAt(row, 0),
				(String)table.getValueAt(row, 1),
				(String)table.getValueAt(row, 2),
				(double)table.getValueAt(row, 3),
				(double)table.getValueAt(row, 4),
				(String)table.getValueAt(row, 5),
				(String)table.getValueAt(row, 6),
				(String)table.getValueAt(row, 7),
				(String)table.getValueAt(row, 8),
				(String)table.getValueAt(row, 9),
				(int)table.getValueAt(row, 10)
			);
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
			return new CenterPoint(-1, "", "", -1, -1, "", "", "", "", "", -1);
		}
	}
	
	
}
