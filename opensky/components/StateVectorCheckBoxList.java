package opensky.components;

import components.custom.checkbox.CheckBoxID;
import opensky.statevector.StateVector;


public class StateVectorCheckBoxList 
{
	public static CheckBoxID[] getList()
	{
		return new CheckBoxID[]
		{
			new CheckBoxID("time"         , StateVector.TIME),
			new CheckBoxID("icao24"      , StateVector.ICAO24),
			new CheckBoxID("lat"         , StateVector.LAT),
			new CheckBoxID("lon"         , StateVector.LON),
			new CheckBoxID("velocity"    , StateVector.VELOCITY),
			new CheckBoxID("heading"     , StateVector.HEADING),
			new CheckBoxID("vertrate"    , StateVector.VERTRATE),
			new CheckBoxID("callsign"    , StateVector.CALLSIGN),
			new CheckBoxID("onground"    , StateVector.ONGROUND),
			new CheckBoxID("alert"        , StateVector.ALERT),
			new CheckBoxID("spi"          , StateVector.SPI),
			new CheckBoxID("squawk"       , StateVector.SQUAWK),
			new CheckBoxID("baroaltitude", StateVector.BAROALTITUDE),
			new CheckBoxID("geoaltitude" , StateVector.GEOALTITUDE),
			new CheckBoxID("lastposupdate", StateVector.LASTPOSUPDATE),
			new CheckBoxID("lastcontact"  , StateVector.LASTCONTACT),
			new CheckBoxID("hour"         , StateVector.HOUR),
			new CheckBoxID("class"        , StateVector.CLASSIFICATION)
		};
	}
}
