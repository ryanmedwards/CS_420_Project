package mvc.main.process.io.source;

public enum Source 
{
	OPENSKY_NETWORK("The OpenSky-Network"),
	LOCAL_DATABASE("Local Database"),
	CSV_FILE("A .csv File");
	
	public final String name;
	
	private Source(final String name)
	{
		this.name = name;
	}
}
