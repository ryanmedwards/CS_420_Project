package components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import application.Application;

public abstract class JInitializer<T extends JComponent>
{
	private final T jcomponent = create();
	
	public abstract T create();
	
	/**
	 * 
	 * 
	 * @return JComponent associated with JInitializer object
	 */
	public T get()
	{
		return jcomponent;
	}
	
	public void add(final JInitializer<?> jInitializer) 
	{
		get().add(jInitializer.get());
	}
	
	public void add(final JInitializer<?> jInitializer, final Object constraint)
	{
		try
		{
			get().add(jInitializer.get(), constraint);
		}
		catch(Exception e)
		{
			if(Application.DEBUG) e.printStackTrace();
		}
	}
	
	public JScrollPane toScrollPane()
	{
		final JScrollPane pane  = new JScrollPane(this.jcomponent);
		
		pane.setBorder(BorderFactory.createEmptyBorder());
		
		return pane;
	}
	
	public JPanel toFlowPanel(final int align, final int hgap, final int vgap)
	{
		final JPanel panel = new JPanel(new FlowLayout(align, hgap, vgap));
		
		panel.add(get());
		
		return panel;
	}
	
	public JInitializer<T> initName(final String name)
	{
		jcomponent.setName(name);
		return this;
	}
	
	public JInitializer<T> initLayout(final LayoutManager layout)
	{
		jcomponent.setLayout(layout);
		return this;
	}
	
	public JInitializer<T> initBackground(final Color color)
	{
		jcomponent.setBackground(color);
		return this;
	}

	
	public JInitializer<T> initForeground(final Color color)
	{
		jcomponent.setForeground(color);
		return this;
	}
	
	public JInitializer<T> initPreferredSize(final int x, final int y)
	{
		jcomponent.setPreferredSize(new Dimension(x, y));
		return this;
	}
	
	public JInitializer<T> initMinimumSize(final int x, final int y)
	{
		jcomponent.setMinimumSize(new Dimension(x, y));
		return this;
	}
	
	public JInitializer<T> initBorder(final Border border) 
	{
		jcomponent.setBorder(border);
		return this;
	}
	
	public JInitializer<T> initFont(final Font font)
	{
		jcomponent.setFont(font);
		return this;
	}
	
	public JInitializer<T> initFontSize(final int size)
	{
		final Font oldFont = jcomponent.getFont();
		
		jcomponent.setFont(new Font(oldFont.getName(), oldFont.getStyle(), size));
		
		return this;
	}
	
	public JInitializer<T> initFontStyle(final int style)
	{
		final Font oldFont = jcomponent.getFont();
		
		jcomponent.setFont(new Font(oldFont.getName(), style, oldFont.getSize()));
		
		return this;
	}
	
	public JInitializer<T> initToolTip(final String toolTip)
	{
		jcomponent.setToolTipText(toolTip);
		return this;
	}
	
	public JInitializer<T> initFocusable(final boolean focusable)
	{
		jcomponent.setFocusable(focusable);
		return this;
	}
	

}
