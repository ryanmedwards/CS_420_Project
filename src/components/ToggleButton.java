package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

public class ToggleButton extends JToggleButton
{
	public final int id;
	private final String text;
	
	public ToggleButton(final String text, final int id)
	{
		super(text);
		this.id = id;
		this.text = text;
	}

	public String getSelectedString()
	{
		return text;
	}
}
