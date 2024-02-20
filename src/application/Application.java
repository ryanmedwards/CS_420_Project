package application;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import components.Frame;
import mvc.Model;
import mvc.main.MainModel;




/**
 * 
 */
public class Application
{

	protected static final Frame frame = new Frame()
								 .initTitle("ADS-B Data Collector")
								 .initMinimumSize(1600, 900)
								 .initDefaultCloseOperation(Frame.EXIT_ON_CLOSE)
								 .initLocationRelativeTo(null)
								 .initLayout(new BorderLayout());

	private final MainModel mainModel = new MainModel();

	public static Frame getFrame()
	{
		return frame;
	}

	public static void refresh()
	{
		frame.revalidate();
		frame.repaint();
	}
	
	private Application()
	{

		frame.add(mainModel.rootPanel, BorderLayout.CENTER);

		frame.setVisible(true);

	}

	// Main Function
	
	public static void main(String[] args)
	{
		try
		{
			//UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.setLookAndFeel(new FlatDarkLaf());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() -> new Application());
	}
	
}
