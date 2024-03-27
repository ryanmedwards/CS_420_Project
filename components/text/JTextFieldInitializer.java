package components.text;

import java.awt.Color;

import javax.swing.JTextField;

public abstract class JTextFieldInitializer<T extends JTextField> extends JTextInitializer<T>
{

	public JTextFieldInitializer<T> initText(final String text)
	{
		get().setText(text);
		return this;
	}
	
	public JTextFieldInitializer<T> initColumns(final int columns)
	{
		get().setColumns(columns);
		return this;
	}
	

	public JTextFieldInitializer<T> initEditable(final boolean editable)
	{
		final Color color = get().getBackground();
		
		get().setEditable(editable);
		
		get().setBackground(color);
		
		return this;
	}
	
	
	public JTextFieldInitializer<T> initHorizontalAlignment(final int alignment)
	{
		try
		{
			get().setHorizontalAlignment(alignment);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
}
