package components.custom.checkbox;

import javax.swing.JCheckBox;

public class CheckBoxID extends JCheckBox
{
	private final int id;
	
	public CheckBoxID(final String text, final int id)
	{
		super(text);
		
		this.id = id;
	}
}
