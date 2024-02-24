package components;

import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox
{
	public final int id;
	
	public CheckBox(final String text)
	{
		super(text);
		id = -1;
	}
	
	public CheckBox(final String text, final int id)
	{
		super(text);
		this.id = id;
	}
}
