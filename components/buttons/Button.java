package components.buttons;

import javax.swing.JButton;

public class Button extends JButtonInitializer<JButton>
{
	public Button() {}
	
	public Button(final String text)
	{
		get().setText(text);
	}

	@Override
	public JButton create() 
	{
		return new JButton();
	}
}
