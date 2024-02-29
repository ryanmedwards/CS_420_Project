package components;

import javax.swing.JLabel;

public class Label extends JInitializer<JLabel>
{
	public JLabel create()
	{
		return new JLabel();
	}

	public Label() {}
	
	public Label(final String text)
	{
		get().setText(text);
	}
	
	public Label initText(final String text)
	{
		get().setText(text);
		return this;
	}

	public Label initHorizontalAlignment(final int alignment) 
	{
		get().setHorizontalAlignment(alignment);
		return this;
	}
	
	public Label initVerticalAlignment(final int alignment) 
	{
		get().setVerticalAlignment(alignment);
		return this;
	}
}
