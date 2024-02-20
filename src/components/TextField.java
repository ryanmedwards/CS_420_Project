package components;

import java.awt.Color;

import javax.swing.JTextField;

public class TextField extends JTextField 
{
	public TextField()
	{
		
	}
	
	public TextField(final int col, final boolean editable)
	{
		this("", col, editable);
	}
	
	public TextField(final String text, final int col, final boolean editable)
	{
		setText(text);
		setColumns(col);
		setEditable(editable);
		setHorizontalAlignment(TextField.RIGHT);
	}
	
	public TextField(final int col)
	{
		this.setColumns(col);
	}
	
	public TextField initEditable(final boolean editable)
	{
		final Color bg = this.getBackground();
		
		this.setEditable(editable);

		this.setBackground(bg);
		return this;
	}
}
