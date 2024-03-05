package components;

import java.awt.Color;

import javax.swing.JTextField;

public class TextField extends JInitializer<JTextField> 
{
	
	
	public TextField initColumns(final int columns)
	{
		get().setColumns(columns);
		return this;
	}
	

	public TextField initEditable(final boolean editable)
	{
		final Color color = get().getBackground();
		
		get().setEditable(editable);
		
		get().setBackground(color);
		
		return this;
	}
	
	
	public TextField initHorizontalAlignment(final int alignment)
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
	
	
	@Override
	public JTextField create() 
	{
		return new JTextField();
	}
}
