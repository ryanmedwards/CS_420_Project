package components.text;

import javax.swing.JFormattedTextField;

public class FormatField extends JTextFieldInitializer<JFormattedTextField> 
{

	@Override
	public JFormattedTextField create() 
	{
		return new JFormattedTextField();
	}

}
