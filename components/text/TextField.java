package components.text;

import java.awt.Color;

import javax.swing.JTextField;

import components.JInitializer;

public class TextField extends JTextFieldInitializer<JTextField> 
{
	
	@Override
	public JTextField create() 
	{
		return new JTextField();
	}
}
