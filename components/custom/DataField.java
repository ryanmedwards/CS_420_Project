package components.custom;

import java.awt.Color;

import javax.swing.JTextField;


public class DataField<T> extends JTextField
{
	private T value = null;
	
	public void setValue(T value)
	{
		if(value == null)
		{
			return;
		}
		
		this.value = value;
		
		this.setText(value.toString());
	}
	
	public T getValue()
	{
		return value;
	}
	
	public DataField()
	{
		setup();
	}
	
	public DataField(final T value)
	{
		setup();
		
		setValue(value);
	}
	
	public DataField<T> initColumns(final int col)
	{
		this.setColumns(col);
		return this;
	}
	
	private void setup()
	{
		this.setHorizontalAlignment(JTextField.RIGHT);
		
		final Color bg = this.getBackground();
		
		this.setEditable(false);
		
		this.setBackground(bg);
	}
}
