package components;

import javax.swing.JTabbedPane;
import javax.swing.border.Border;

public class TabPanel extends JTabbedPane 
{
	public TabPanel()
	{
		
	}

	public TabPanel(final int tabPlacement) 
	{
		super(tabPlacement);
	}
	
	public TabPanel(final Panel... panels)
	{
		for(final Panel panel: panels)
		{
			if(panel.getName() != null)
			{
				this.addTab(panel.getName(), panel);
			}
			else
			{
				this.addTab("Unnamed Panel!", panel);
			}
		}
	}
	
	public TabPanel initTabPlacement(final int tabPlacement)
	{
		this.setTabPlacement(tabPlacement);
		return this;
	}
	
	public TabPanel initBorder(final Border border)
	{
		this.setBorder(border);
		return this;
	}

}
