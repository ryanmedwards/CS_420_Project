package mvc.main.simulation;

public class AnimationSettings 
{

	public static final String DEFAULT_TITLE = "Flight Simulation";
	public static final int DEFAULT_BASEMAP_ZOOM = 8;
	public static final int RASTER_WIDTH = 512;
	public static final int RASTER_HEIGHT = 512;
	public static final double PLANE_SCALE = 1.0;
	public static final double FIGURE_ZOOM = 1.0;
	public static final int DPI = 150;
	public static final double LATITUDE_OFFSET = 0.0;
	public static final double LONGITUDE_OFFSET = 0.0;
	public static final int TIME_INTERVAL = 20;
	
	
	
	public final String title;
	public final int basemapZoom;
	public final int rasterWidth;
	public final int rasterHeight;
	public final double planeScale;
	public final double figureZoom;
	public final int dpi;
	public final double latitudeOffset;
	public final double longitudeOffset;
	public final int timeInterval;
	
	
	public AnimationSettings(final String title,
						 	  final int basemapZoom,
							  final int rasterWidth,
							  final int rasterHeight,
							  final double planeScale,
							  final double figureZoom,
							  final int dpi,
							  final double latitudeOffset,
							  final double longitudeOffset,
							  final int timeInterval)
	{
		this.title = (title.isBlank()) ? DEFAULT_TITLE : title;
		this.basemapZoom = basemapZoom;
		this.rasterWidth = rasterWidth;
		this.rasterHeight = rasterHeight;
		this.planeScale = planeScale;
		this.figureZoom = figureZoom;
		this.dpi = dpi;
		this.latitudeOffset = latitudeOffset;
		this.longitudeOffset = longitudeOffset;
		this.timeInterval = timeInterval;
	}
}
