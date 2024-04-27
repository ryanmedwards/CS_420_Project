package mvc.main.simulation;

public class GeoplotSettings 
{
	public final String title;
	public final String subtitle;
	
	public final String auStyle;
	public final String pmStyle;
	public final String giStyle;
	public final String vdStyle;
	
	public final String auMarker;
	public final String pmMarker;
	public final String giMarker;
	public final String vdMarker;
	
	public final String auColor;
	public final String pmColor;
	public final String giColor;
	public final String vdColor;
	
	public GeoplotSettings(
			final String title,
			final String subtitle,
			final String[] styles, 
			final String[] markers, 
			final String[] colors)
	{
		this.title = (title.isBlank()) ? "Flights" : title;
		
		this.subtitle = (subtitle.isBlank()) ? "" : subtitle;		
		
		this.auStyle = styles[0];
		this.pmStyle = styles[1];
		this.giStyle = styles[2];
		this.vdStyle = styles[3];
		
		this.auMarker = markers[0];
		this.pmMarker = markers[1];
		this.giMarker = markers[2];
		this.vdMarker = markers[3];
		
		this.auColor = colors[0];
		this.pmColor = colors[1];
		this.giColor = colors[2];
		this.vdColor = colors[3];
	}
}
