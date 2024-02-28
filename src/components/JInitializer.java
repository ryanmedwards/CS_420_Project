package components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
