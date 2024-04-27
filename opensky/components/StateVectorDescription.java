package opensky.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import guiutil.Displayable;
import opensky.statevector.StateVector;

/**
 * Write better descriptions
 * 
 */
public class StateVectorDescription implements Displayable<StateVectorDescription>
{
	public static final List<StateVectorDescription> list = new ArrayList<>();
	
	public static final String[] COLUMNS = {"Features", "Unit", "Description"};
	
	static
	{
		final String[] features = StateVector.FEATURES;
		
		list.add(new StateVectorDescription(features[StateVector.TIME]			, "Unix Timestamp"	, "The last timestamp for which state vector was valid."));
		list.add(new StateVectorDescription(features[StateVector.ICAO24]		, "n/a"				, "The unique 24-bit Address code for an aircraft. ICAO stands for International Civil Aviation Organization."));
		list.add(new StateVectorDescription(features[StateVector.LAT]			, "WGS84"			, "The latitude coordinate of the aircraft"));
		list.add(new StateVectorDescription(features[StateVector.LON]			, "WGS84"			, "The longitude coordinate of the aircraft"));
		list.add(new StateVectorDescription(features[StateVector.VELOCITY]		, "m/s"				, "The speed of the aircraft"));
		list.add(new StateVectorDescription(features[StateVector.HEADING]		, "degrees"			, "The angle from true north that the aircraft's nose points"));
		list.add(new StateVectorDescription(features[StateVector.VERTRATE]		, "m/s"				, "The vertical speed of the aircraft."));
		list.add(new StateVectorDescription(features[StateVector.CALLSIGN]		, "n/a"				, "The identifier indicates which flight the aircraft is on."));
		list.add(new StateVectorDescription(features[StateVector.ONGROUND]		, "n/a"				, "Indicates true if the aircraft is grounded, false if airborne."));
		list.add(new StateVectorDescription(features[StateVector.ALERT]			, "n/a"				, "a special indicator used in ATC (Air Traffic Control)"));
		list.add(new StateVectorDescription(features[StateVector.SPI]			, "n/a"				, "a special indicator used in ATC (Air Traffic Control)"));
		list.add(new StateVectorDescription(features[StateVector.SQUAWK]		, "n/a"				, "A four-digit octal number that is used for indication of emergencies."));
		list.add(new StateVectorDescription(features[StateVector.BAROALTITUDE]	, "m"				, "Measures the altitude by the barometer."));
		list.add(new StateVectorDescription(features[StateVector.GEOALTITUDE]	, "m"				, "Measure the altitude using GNSS (GPS) sensor."));
		list.add(new StateVectorDescription(features[StateVector.LASTPOSUPDATE]	, "Unix Timestamp"	, "Indicates the age of the position."));
		list.add(new StateVectorDescription(features[StateVector.LASTCONTACT]	, "Unix Timestamp"	, "The time when OpenSky received the last signal from the aircraft"));
		list.add(new StateVectorDescription(features[StateVector.HOUR]			, "Unix Timestamp"	, "The time stamp for each message received by an aircraft"));
		list.add(new StateVectorDescription(features[StateVector.CLASSIFICATION], "n/a"				, "An integer indicating the type of message. Used for machine learning classification."));
	}
	
	public final String feature;
	public final String unit;
	public final String description;

	private StateVectorDescription(final String feature, final String unit, final String description)
	{
		this.feature = feature;
		this.unit = unit;
		this.description = description;
	}
	
	
	
	@Override
	public Object[] toArray() 
	{
		return new Object[] {this.feature, this.unit, this.description};
	}



	@Override
	public StateVectorDescription toObject(final JTable table, final int row) 
	{
		return null;
	}

}
