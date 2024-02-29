package opensky.statevector;

public class StateVector 
{
	
	public final static int SIZE = 18;

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
	public final static int CLASS		    = 17;

	public final static int AUTHENTIC         = 0;
	public final static int PATH_MODIFICATION = 1;
	public final static int GHOST_INJECTION   = 2;
	public final static int VELOCITY_DRIFT    = 3;
	
	public final Object[] values;
	
	public StateVector(final Object[] values)
	{
		if(values.length != SIZE)
		{
			System.out.println(values.length);
			//System.err.println("ERROR! String array provided is not the correct size.");
			this.values = new Object[SIZE];
		}
		else
		{
			this.values = values;
		}
	}
	
	public Object get(final int index)
	{
		return values[index];
	}
}
