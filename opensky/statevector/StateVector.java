package opensky.statevector;

import java.util.List;

import application.Application;

 
public class StateVector 
{
	public static final String[] FEATURES = 
	{
		"time", "icao24", "lat", "lon", "velocity", "heading", "vertrate", 	
		"callsign", "onground", "alert", "spi", "squawk", "baroaltitude",
		"geoaltitude", "lastposupdate", "lastcontact", "hour", "class"
	};
	
	
	public final static int TIME 			=  0;
	public final static int ICAO24 			=  1;
	public final static int LAT 			=  2;
	public final static int LON				=  3;
	public final static int VELOCITY 		=  4;
	public final static int HEADING 		=  5;
	public final static int VERTRATE 		=  6;
	public final static int CALLSIGN 		=  7;
	public final static int ONGROUND 		=  8;
	public final static int ALERT 			=  9;
	public final static int SPI 			= 10;
	public final static int SQUAWK 			= 11;
	public final static int BAROALTITUDE 	= 12;
	public final static int GEOALTITUDE 	= 13;
	public final static int LASTPOSUPDATE 	= 14;
	public final static int LASTCONTACT 	= 15;
	public final static int HOUR 			= 16;
	public final static int CLASSIFICATION  = 17;
	
	public final static int SIZE = 18;
	
	public final static int AUTHENTIC = 0;
	public final static int PATH_MODIFICATION = 1;
	public final static int GHOST_INJECTION = 2;
	public final static int VELOCITY_DRIFT = 3;

	private final String[] values;

	public StateVector(String[] values)
	{
		if(values.length == SIZE)
		{
			this.values = values;
		}
		else if(values.length == SIZE - 1)
		{
			this.values = new String[SIZE];
			for(int i = 0; i < SIZE - 1; ++i)
			{
				this.values[i] = values[i];
			}
			this.values[SIZE - 1] = "0";
		}
		else
		{
			this.values = new String[SIZE];
		}
	}


	public String[] toArray()
	{
		return values;
	}

	
	/**
	 * Used for writing to file.
	 * 
	 * 
	 * @return
	 */
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < SIZE - 1; ++i)
		{
			result.append(values[i] +",");
		}
		
		result.append(values[SIZE - 1]);
		
		return result.toString();
	}	
		
	public int time() throws NumberFormatException
	{
		return Integer.parseInt(values[TIME]);
	}
	
	public String icao24()
	{
		return values[ICAO24];
	}
	
	public double lat() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[LAT]);
	}
	
	public double lon() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[LON]);
	}
	
	public double velocity() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[VELOCITY]);
	}
	
	public double heading() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[HEADING]);
	}
	
	public double vertrate() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[VERTRATE]);
	}
	
	public String callsign()
	{
		return values[CALLSIGN];
	}

	public String onground()
	{
		return values[ONGROUND];
	}
	
	public String alert()
	{
		return values[ALERT];
	}
	
	public String spi()
	{
		return values[SPI];
	}
	
	public double baroaltitude() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[BAROALTITUDE]);
	}
	
	public double geoaltitude() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[GEOALTITUDE]);
	}
	
	public double lastposupdate() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[LASTPOSUPDATE]);
	}
	
	public double lastcontact() throws NumberFormatException, NullPointerException
	{
		return Double.parseDouble(values[LASTCONTACT]);
	}
	
	public int hour() throws NumberFormatException
	{
		return Integer.parseInt(values[HOUR]);
	}
	
	public int classification() throws NumberFormatException
	{
		return Integer.parseInt(values[CLASSIFICATION]);
	}
	
	
	public String getFlightKey()
	{
		return values[ICAO24] + "," + values[CALLSIGN] + "," + values[CLASSIFICATION];
	}
	

	
}














