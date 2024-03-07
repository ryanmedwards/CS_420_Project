package components.buttons;

import javax.swing.JToggleButton;

public class ToggleButton extends JButtonInitializer<JToggleButton>
{
	@Override
	public JToggleButton create() 
	{
		return new JToggleButton();
	}
}
