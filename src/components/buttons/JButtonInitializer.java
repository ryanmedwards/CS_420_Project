package components.buttons;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import components.JInitializer;

public abstract class JButtonInitializer<T extends AbstractButton> extends JInitializer<T>
{	
	
	public JButtonInitializer<T> initText(final String text)
	{
		get().setText(text);
		return this;
	}
	
	public JButtonInitializer<T> initActionListener(final ActionListener listener)
	{
		get().addActionListener(listener);
		return this;
	}
	
}
