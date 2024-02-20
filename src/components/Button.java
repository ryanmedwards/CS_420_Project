package components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton
{
	public Button(final String text)
	{
		super(text);
		//this.setFocusable(false);
	}
	
	public Button initActionListener(final ActionListener listener)
	{
		this.addActionListener(listener);
		return this;
	}
}
