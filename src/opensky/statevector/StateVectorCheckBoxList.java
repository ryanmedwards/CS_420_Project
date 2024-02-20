package opensky.statevector;

import java.util.List;

import components.CheckBox;
import components.CheckBoxNode;

public class StateVectorCheckBoxList 
{
	public static CheckBox[] getList()
	{
		return new CheckBox[]
		{
			new CheckBox("time"         , StateVector.TIME),
			new CheckBox("icao24"      , StateVector.ICAO24),
			new CheckBox("lat"         , StateVector.LAT),
			new CheckBox("lon"         , StateVector.LON),
			new CheckBox("velocity"    , StateVector.VELOCITY),
			new CheckBox("heading"     , StateVector.HEADING),
			new CheckBox("vertrate"    , StateVector.VERTRATE),
			new CheckBox("callsign"    , StateVector.CALLSIGN),
			new CheckBox("onground"    , StateVector.ONGROUND),
			new CheckBox("alert"        , StateVector.ALERT),
			new CheckBox("spi"          , StateVector.SPI),
			new CheckBox("squawk"       , StateVector.SQUAWK),
			new CheckBox("baroaltitude", StateVector.BAROALTITUDE),
			new CheckBox("geoaltitude" , StateVector.GEOALTITUDE),
			new CheckBox("lastposupdate", StateVector.LASTPOSUPDATE),
			new CheckBox("lastcontact"  , StateVector.LASTCONTACT),
			new CheckBox("hour"         , StateVector.HOUR)
		};
	}
}
