package components.buttons;

import javax.swing.JCheckBox;

import components.JInitializer;

public class CheckBox extends JInitializer<JCheckBox>
{

	@Override
	public JCheckBox create() 
	{
		return new JCheckBox();
	}
	
}
