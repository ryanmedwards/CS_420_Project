package components;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class PopupMenu extends JPopupMenu
{	
	public PopupMenu()
	{
		
	}
	
	public PopupMenu(final JComponent comp)
	{
		this.add(comp);
	}
}
