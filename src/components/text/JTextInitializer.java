package components.text;

import java.awt.Insets;

import javax.swing.text.JTextComponent;

import components.JInitializer;

public abstract class JTextInitializer<T extends JTextComponent> extends JInitializer<T>
{
	
	public JTextInitializer<T> initMargin(final int margin)
	{
		get().setMargin(new Insets(margin, margin, margin, margin));
		return this;
	}
	
	public JTextInitializer<T> initMargin(final int top, final int left, final int bottom, final int right)
	{
		get().setMargin(new Insets(top, left, bottom, right));
		return this;
	}
	
	public JTextInitializer<T> initEditable(final boolean editable)
	{
		get().setEditable(editable);
		return this;
	}
}
