package components;

import javax.swing.JTabbedPane;

public class TabbedPane extends JInitializer<JTabbedPane>
{
	public TabbedPane(final int placement)
	{
		get().setTabPlacement(placement);
	}
	
	public TabbedPane initTabPlacement(final int placement)
	{
		get().setTabPlacement(placement);
		return this;
	}

	@Override
	public JTabbedPane create() 
	{
		return new JTabbedPane();
	}
	
}
