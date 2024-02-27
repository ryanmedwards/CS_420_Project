package components;

import java.util.List;

import javax.swing.JComboBox;

public class ComboBox<T> extends JInitializer<JComboBox<T>>
{
	public ComboBox(final List<T> items)
	{
		for(final T item: items)
		{
			get().addItem(item);
		}
	}
	
	@Override
	public JComboBox<T> create() 
	{
		return new JComboBox<>();
	}
}
