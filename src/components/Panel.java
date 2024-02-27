package components;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Panel extends JInitializer<JPanel>
{
	public Panel() {}
	
	public Panel(final LayoutManager layout)
	{
		get().setLayout(layout);
	}

	@Override
	public JPanel create() 
	{
		return new JPanel();
	}
}
