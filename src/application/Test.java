package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;


public class Test 
{

	private static void launch()
	{
		final JFrame frame = new JFrame();
		
		frame.setTitle("Testing");
		frame.setMinimumSize(new Dimension(1400, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		
		
		
		final List<Integer> list = new ArrayList<>();
		
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		System.out.println(list.toArray().toString());
		
		
		
		
		
		
		
		
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
//		SwingUtilities.invokeLater(() -> launch());
	}
	
	final String[] colors = new String[]
	{
			"Menu.acceleratorForeground",
			"Menu.acceleratorSelectionForeground",
			"Menu.background",
			"Menu.disabledForeground",
			"Menu.foreground",
			"Menu.selectionBackground",
			"Menu.selectionForeground",
			"MenuBar.background",
			"MenuBar.borderColor",
			"MenuBar.foreground",
			"MenuBar.highlight",
			"MenuBar.shadow",
			"MenuItem.acceleratorForeground",
			"MenuItem.acceleratorSelectionForeground",
			"MenuItem.background",
			"MenuItem.disabledForeground",
			"MenuItem.foreground",
			"MenuItem.selectionBackground",
			"MenuItem.selectionForeground"
	};
	
	
	class ColorsDemo extends JPanel
	{
		
		public ColorsDemo()
		{
			this.setLayout(new GridLayout(0, 1));
			
			for(final String color: colors)
			{
				final JPanel panel = new JPanel(new GridLayout(1, 0));
				
				final JPanel lp = new JPanel();
				
				final JPanel cp = new JPanel();
				
				final Color c = UIManager.getColor(color);
				//lp.setBackground(Color.white);
				cp.setBackground(c);
				
				lp.add(new JLabel(color));
				
				panel.add(lp);
				panel.add(cp);
				
				this.add(panel);
			}
		}
	}
}
