package opensky.components;

import components.custom.checkbox.CheckBoxNode;
import components.custom.checkbox.CheckBoxTree;
import opensky.statevector.StateVector;

public class StateVectorCheckBoxTree extends CheckBoxTree
{
//	public StateVectorCheckBoxTree(final String rootName)
//	{
//		super(new CheckBoxNode(rootName, 0, new CheckBoxNode[] 
//		{		
//				new CheckBoxNode("Identification", 1, new CheckBoxNode[] 
//				{
//						new CheckBoxNode("icao24"      , StateVector.ICAO24),
//						new CheckBoxNode("callsign"    , StateVector.CALLSIGN)
//				}),
//				new CheckBoxNode("Position", 1, new CheckBoxNode[] 
//				{
//						new CheckBoxNode("lat"         , StateVector.LAT),
//						new CheckBoxNode("lon"         , StateVector.LON),
//						new CheckBoxNode("baroaltitude", StateVector.BAROALTITUDE),
//						new CheckBoxNode("geoaltitude" , StateVector.GEOALTITUDE),
//						new CheckBoxNode("onground"    , StateVector.ONGROUND)
//				}),
//				new CheckBoxNode("Velocity", 1, new CheckBoxNode[] 
//				{
//						new CheckBoxNode("velocity"    , StateVector.VELOCITY),
//						new CheckBoxNode("heading"     , StateVector.HEADING),
//						new CheckBoxNode("vertrate"    , StateVector.VERTRATE),
//
//				}),
//				new CheckBoxNode("Time", 1, new CheckBoxNode[] 
//				{
//						new CheckBoxNode("time"         , StateVector.TIME),
//						new CheckBoxNode("lastposupdate", StateVector.LASTPOSUPDATE),
//						new CheckBoxNode("lastcontact"  , StateVector.LASTCONTACT),
//						new CheckBoxNode("hour"         , StateVector.HOUR)
//				}),
//				new CheckBoxNode("Other", 1, new CheckBoxNode[]
//				{
//						new CheckBoxNode("spi"          , StateVector.SPI),
//						new CheckBoxNode("alert"        , StateVector.ALERT),
//						new CheckBoxNode("squawk"       , StateVector.SQUAWK)
//				})
//		}));
//	}
	
	
	public StateVectorCheckBoxTree(final String rootName)
	{
		super(new CheckBoxNode(rootName, 0, new CheckBoxNode[] 
		{		
			new CheckBoxNode("time"         , StateVector.TIME),
			new CheckBoxNode("icao24"      , StateVector.ICAO24),
			new CheckBoxNode("lat"         , StateVector.LAT),
			new CheckBoxNode("lon"         , StateVector.LON),
			new CheckBoxNode("velocity"    , StateVector.VELOCITY),
			new CheckBoxNode("heading"     , StateVector.HEADING),
			new CheckBoxNode("vertrate"    , StateVector.VERTRATE),
			new CheckBoxNode("callsign"    , StateVector.CALLSIGN),
			new CheckBoxNode("onground"    , StateVector.ONGROUND),
			new CheckBoxNode("alert"        , StateVector.ALERT),
			new CheckBoxNode("spi"          , StateVector.SPI),
			new CheckBoxNode("squawk"       , StateVector.SQUAWK),
			new CheckBoxNode("baroaltitude", StateVector.BAROALTITUDE),
			new CheckBoxNode("geoaltitude" , StateVector.GEOALTITUDE),
			new CheckBoxNode("lastposupdate", StateVector.LASTPOSUPDATE),
			new CheckBoxNode("lastcontact"  , StateVector.LASTCONTACT),
			new CheckBoxNode("hour"         , StateVector.HOUR)
		}));
	}
}
