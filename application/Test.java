package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import components.Table;
import components.custom.RootPanel;
import components.custom.datetime.DateSelector;
import components.custom.datetime.TimeSelector;
import guiutil.MyTableModel;
import mvc.main.MainModel;
import mvc.source.Source;
import mvc.source.SourceSelector;

public class Test extends JFrame 
{

	private Test()
	{
		this.setTitle("Test");
		//this.setPreferredSize(new Dimension(1920, 1080));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setLayout(new BorderLayout());
		this.draw();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	final JTextField field = new JTextField("hello");
	final DateSelector ds = new DateSelector(this::update);
	final TimeSelector ts = new TimeSelector(this::update);
	
	private void update()
	{
		field.setText(ds.getDate() + "  " + (ds.getUnixTime() + ts.getSeconds()));
		field.revalidate();
		field.repaint();
	}
	
	private void draw()
	{
		JPanel rootPanel = new RootPanel().get();
		
		
		rootPanel.add(field, BorderLayout.NORTH);
		rootPanel.add(ds, BorderLayout.CENTER);
		rootPanel.add(ts, BorderLayout.EAST);
	
		this.add(rootPanel, BorderLayout.CENTER);
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
			new Test();
		});
	}
}
