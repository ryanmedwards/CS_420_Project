package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import components.Frame;
import components.ScrollPanel;
import components.Table;


public class Debug 
{

	
	private final Frame frame = new Frame()
			 .initTitle("Debug")
			 .initMinimumSize(1600, 900)
			 .initDefaultCloseOperation(Frame.EXIT_ON_CLOSE)
			 .initLocationRelativeTo(null)
			 .initLayout(new BorderLayout());
	
	
	
	private final Table table = new Table(new String[]{"Label" , "Button"})
			.initButtonColumn(1);
	
	private final ScrollPanel scrollPanel = new ScrollPanel(table, ScrollPanel.VERTICAL_SCROLLBAR_ALWAYS, ScrollPanel.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	private Debug()
	{

		table.setRowHeight(40);

		table.setEnabled(false);
		table.setFocusable(false);
		
		table.addData(new Object[][] { {"Test"} });
		
		frame.add(scrollPanel, BorderLayout.CENTER);
		
		
		frame.setVisible(true);
	}
	
	
	
	
	
	
	public static void main(String[] args)
	{
		
//		try
//		{
////			UIManager.setLookAndFeel(new FlatLightLaf());
//			UIManager.setLookAndFeel(new FlatDarkLaf());
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		List<String> colors = new ArrayList<String>();
//		for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
//		    if (entry.getValue() instanceof Color) {
//		        colors.add((String) entry.getKey()); // all the keys are strings
//		    }
//		}
//		Collections.sort(colors);
//		for (String name : colors)
//		    System.out.println(name);
//		
//		SwingUtilities.invokeLater(() -> new Debug());

		

		
		

	}

}
