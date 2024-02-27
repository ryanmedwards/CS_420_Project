package components.text;

import java.awt.Color;

import javax.swing.JTextArea;

public class TextArea extends JTextInitializer<JTextArea> 
{
	
	@Override
	public TextArea initEditable(final boolean editable)
	{
		final Color color = get().getBackground();

		get().setEditable(editable);

		get().setBackground(color);
		
		return this;
	}

	@Override
	public JTextArea create() 
	{
		return new JTextArea();
	}
}
