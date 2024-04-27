package application;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import mvc.main.MainModel;




/**
 * Starts the GUI Application
 */
public class Application extends JFrame
{
	private final MainModel mainModel;
	
	private static Application app = null;
	
	public static final boolean DEBUG = true;
	
	public static JFrame getApp()
	{
		return app;
	}
	
	private Application()
	{
		this.setTitle("ADS-B Data Collector");
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		mainModel = new MainModel();

		this.add(mainModel.rootPanel, BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(new FlatLightLaf());
//			UIManager.setLookAndFeel(new FlatDarkLaf());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		SwingUtilities.invokeLater(() ->
		{
			app = new Application();
		});
	}
	
}
