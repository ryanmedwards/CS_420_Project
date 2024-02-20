package components;

import java.util.List;

import javax.swing.JComboBox;

public class ComboBox<T> extends JComboBox<T> 
{
	public ComboBox()
	{
		
	}
	
	public ComboBox(final T[] items)
	{
		for(final T item: items)
		{
			this.addItem(item);
		}
	}
	
	public ComboBox(final List<T> items)
	{
		for(final T item: items)
		{
			this.addItem(item);
		}
	}
}
