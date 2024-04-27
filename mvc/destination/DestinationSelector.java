package mvc.destination;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import application.Application;
import components.custom.dialog.Dialog;
import mvc.source.Source;

public class DestinationSelector  extends Dialog
{
	
	/**
	 * Returns a Destination Selector for StateVectorList
	 * 
	 * Gives the options for csv file and local database
	 * 
	 * @param frame
	 * @return
	 */
	public static DestinationSelector stateVectors(final JFrame frame)
	{
		return new DestinationSelector(
				frame, 
				Arrays.asList(
						Destination.Type.FILE,
						Destination.Type.LOCAL_DATABASE),
				Arrays.asList(".csv"));
	}
	
	/**
	 * Returns a DestinationSelector for image files.
	 * 
	 * Gives the option for png files. (for now)
	 * 
	 * @param frame
	 * @return
	 */
	public static DestinationSelector image(final JFrame frame)
	{
		return new DestinationSelector(
				frame, 
				Arrays.asList(Destination.Type.FILE),
				Arrays.asList(".png"));
	}
	
	
	
	
	
	
	private final DestinationModel model;
	
	private DestinationSelector(
			final JFrame parent, 
			final List<Destination.Type> types,
			final List<String> fileFormats)
	{
		super(parent, "Destination Selector", true);
		
		this.model = new DestinationModel(types, fileFormats);
		
		this.setMode(Mode.CANCEL_CONFIRM);
		
		this.addContent(model.rootPanel);
		
		this.setPreferredSize(new Dimension(800, 500));
	}
	
	public boolean hasValidDestination()
	{
		return model.hasValidDestination();
	}
	
	public Destination getDestination()
	{
		return model.getDestination();
	}
}
