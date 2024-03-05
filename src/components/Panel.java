package components;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Panel extends JInitializer<JPanel>
{
	public Panel() {}
	
	public Panel(final LayoutManager layout)
	{
		get().setLayout(layout);
	}

	public Panel initBoxLayout(final int axis)
	{
		get().setLayout(new BoxLayout(get(), axis));
		return this;
	}
	
	
	@Override
	public JPanel create() 
	{
		return new JPanel();
	}
}
