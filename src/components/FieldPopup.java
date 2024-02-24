package components;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import application.Application;

public class FieldPopup extends JPopupMenu
{
	private boolean allow = false;

	private boolean hasSearchedForComboBoxes = false;
	
	final JTextComponent field;
	final Panel panel;
	
	final List<ComboBox<?>> comboBoxList = new ArrayList<>(); 

	final MouseListener fieldListener = new MouseAdapter()
	{		
		@Override
		public void mouseEntered(MouseEvent e) { open(field, 0, field.getHeight()); }
		
		@Override
		public void mouseExited(MouseEvent e) {	if(e.getY() < field.getHeight()) close(); }
	};
	
	final MouseListener panelListener = new MouseAdapter()
	{
		@Override
		public void mouseExited(MouseEvent e)
		{
			final int fieldWidth = field.getWidth();
			
			final int mouseX = e.getX();
			final int mouseY = e.getY();
			
			final int panelX = panel.getX();
			final int panelY = panel.getY();
			
			final int panelWidth  = panel.getWidth();
			final int panelHeight = panel.getHeight();
			
			if( mouseX < panelX ||
				mouseX > panelX + panelWidth - 2 )
			{
				close();
			}
			else if( mouseY > panelY + panelHeight - 5 || 
					( mouseY < panelY + 5 && 
					  mouseX > fieldWidth - 2 ) )
			{
				close();
			}
		}
	};

	public FieldPopup(final JTextComponent field, final Panel panel)
	{	
		this.field = field;
		this.panel = panel;
		
		this.add(panel);
		
		enable();
	}
	
	public void disable()
	{
		field.removeMouseListener(fieldListener);
		
		panel.removeMouseListener(fieldListener);
	}
	
	public void enable()
	{
		field.addMouseListener(fieldListener);
		
		panel.addMouseListener(panelListener);
	}

	private void findComboBoxes(final Container container)
	{
		for(final Component component: container.getComponents())
		{
			if(component instanceof ComboBox)
			{
				comboBoxList.add((ComboBox<?>)component);
			}
			else if(component instanceof Container)
			{
				findComboBoxes((Container)component);
			}
		}
	}


	
	public void open(Component invoker, final int x, final int y)
	{
		allow = true;
		this.show(invoker, x, y);
		allow = false;
		
		this.revalidate();
		this.repaint();
	}
	
	private void closeComboBox()
	{
		if( ! hasSearchedForComboBoxes )
		{
			findComboBoxes(panel);
			hasSearchedForComboBoxes = true;
		}
		
		for(final ComboBox<?> comboBox: comboBoxList)
		{
			comboBox.hidePopup();
		}
		
		panel.revalidate();
		panel.repaint();
	}
	
	public void close()
	{
		closeComboBox();
		
		allow = true;
		this.setVisible(false);
		allow = false;
	}
	
	
	@Override
	public void setVisible(final boolean visible)
	{
		if(allow)
		{
			super.setVisible(visible);
		}
	}
}
