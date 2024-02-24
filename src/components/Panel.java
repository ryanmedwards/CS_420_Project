package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import application.Application;
import guiutil.Function;
import guiutil.ReturnFunction;


public class Panel extends JPanel
{
	public static final int BORDER  = 0;
	public static final int FLOW    = 1;
	public static final int GRIDBAG = 2;
	public static final int GRID    = 3;
	
	public Panel(){}

	public Panel(final BorderLayout layout)
	{
		this.setLayout(layout);
	}

	public Panel(final FlowLayout layout)
	{
		this.setLayout(layout);
	}
	
	public Panel(final GridBagLayout layout)
	{
		this.setLayout(layout);
	}
	
	public Panel(final GridLayout layout)
	{
		this.setLayout(layout);
	}
	
	public Panel(final BoxLayout layout)
	{
		this.setLayout(layout);
	}

	
	public Panel initLayout(final int layout, final Integer... args)
	{
		switch(layout)
		{
		
		case BORDER:

			if(args.length == 2) setLayout(new BorderLayout(args[0], args[1]));
			else                 setLayout(new BorderLayout());	
			break;
			
		case FLOW:
			
			if     (args.length == 3) setLayout(new FlowLayout(args[0], args[1], args[2]));
			else if(args.length == 1) setLayout(new FlowLayout(args[0]));
			else                      setLayout(new FlowLayout());
			break;	
			
		case GRIDBAG:
			
			setLayout(new GridBagLayout());
			break;
			
		case GRID:
			
			if     (args.length == 4) setLayout(new GridLayout(args[0], args[1], args[2], args[3]));
			else if(args.length == 2) setLayout(new GridLayout(args[0], args[1]));					
			else                      setLayout(new GridLayout());
			break;
			
		default:
			
			setLayout(new FlowLayout());
			
		}
		return this;
	}
	
	public Panel initBorder(final Border border)
	{
		this.setBorder(border);
		return this;
	}
	
	public Panel initBackground(final Color color)
	{
		this.setBackground(color);
		return this;
	}
	
	public Panel initBackground(final ReturnFunction function)
	{
		try
		{
			final Color color = (Color)function.run();
			this.setBackground(color);
		}
		catch(Exception e)
		{
			
		}
		
		return this;
	}
	
	public Panel initBoxLayout(final int dir)
	{
		this.setLayout(new BoxLayout(this, dir));
		return this;
	}
	
	public Panel initPreferredSize(final int x, final int y)
	{
		this.setPreferredSize(new Dimension(x, y));
		return this;
	}
	
	public Panel initPreferredWidth(final int w)
	{
		this.setPreferredSize(new Dimension(w, getHeight()));
		return this;
	}
	
	public Panel initName(final String name)
	{
		this.setName(name);
		return this;
	}
			
}
