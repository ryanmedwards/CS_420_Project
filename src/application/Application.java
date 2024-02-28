package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import components.Panel;
import mvc.main.MainModel;




/**
 * 
 */
public class Application extends JFrame
{
	private MainModel mainModel;
	
	private static Application app;
	
	public static JFrame getApp()
	{
		return app;
	}
	
	private Application()
	{
		this.setTitle("ADS-B Data Collector");
		this.setMinimumSize(new Dimension(1600, 900));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
	}

	
	private void draw()
	{
		mainModel = new MainModel();
		
		this.add(mainModel.rootPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		try
		{
//			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.setLookAndFeel(new FlatDarkLaf());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() ->
		{
			app = new Application();
			
			app.draw();
		});
	}
	
}
