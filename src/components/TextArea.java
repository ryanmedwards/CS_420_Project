package components;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.border.Border;

public class TextArea extends JTextArea
{
	public TextArea initEditable(final boolean editable)
	{
		final Color bg = this.getBackground();

		this.setEditable(editable);

		this.setBackground(bg);
		return this;
	}
	
	public TextArea initBorder(final Border border)
	{
		this.setBorder(border);
		return this;
	}
}
