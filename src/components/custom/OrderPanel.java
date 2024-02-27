package components.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;

import components.Label;
import components.Panel;

public class OrderPanel<T> extends JPanel
{	
	private final List<DragPanel> panels = new ArrayList<>();
	
	private final JPanel actionPanel = new JPanel(new GridLayout(0, 1));
	
	private Color background;
	
	public OrderPanel()
	{
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		this.add(actionPanel, BorderLayout.NORTH);
		
		
		try 
		{ 
			this.background = (Color) UIManager.getColor("TextArea.background");
			this.setBackground(background); 
		}
		catch(NullPointerException e) 
		{
			this.background = this.getBackground();
			e.printStackTrace(); 
		}
		
		actionPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				for(final DragPanel p: panels)
				{
					//p.held = false;
				}
			}
		});
	}

	public List<T> getOrderedList()
	{
		final List<T> list = new ArrayList<>();
		
		for(final DragPanel panel: panels)
		{
			list.add(panel.object);
		}
		
		return list;
	}
	
	public void add(final DragPanel panel)
	{		
		panels.add(panel);
		
		actionPanel.add(panel);
		
		this.revalidate();
		this.repaint();
	}
	
	public void remove(final DragPanel panel)
	{
		panels.remove(panel);
			
		redraw();
	}
	
	private void redraw()
	{
		actionPanel.removeAll();
		
		for(final JPanel p: panels)
		{
			actionPanel.add(p);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	public void moveUp(final DragPanel p)
	{
		int index = -1;
		
		for(int i = 0; i < panels.size(); ++i)
		{
			if(panels.get(i).equals(p))
			{
				index = i;
				break;
			}
		}
		
		if(index > 0)
		{
			panels.remove(index);
			panels.add(index - 1, p);
			
			redraw();
		}
	}
	
	public void moveDown(final DragPanel p)
	{
		int index = -1;
		
		for(int i = 0; i < panels.size(); ++i)
		{
			if(panels.get(i).equals(p))
			{
				index = i;
				break;
			}
		}
		
		if(index != -1 && index < panels.size() - 1)
		{
			panels.remove(index);
			panels.add(index + 1, p);
			
			redraw();
		}
	}
	
	
	
	public abstract class DragPanel extends JPanel
	{
		private final T object;

		
		private boolean held = false;
	
		private final JPanel movePanel    = new Panel().initBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)).get();
		private final JPanel contentPanel = new JPanel();
		
		private final Color pressedColor = movePanel.getBackground();
		
		public abstract void addContent(final JPanel panel);
		
		private static int next = 0;
		
		public DragPanel(final T object, final boolean addToPanels)
		{
			this.object = object;

			this.movePanel.setToolTipText("Click and Drag to Reorder.");
			
			if(addToPanels)
			{
				panels.add(this);
			}
			
			assign();
		}

		
		
		public void draw()
		{
			addContent(contentPanel);
			
			movePanel.setBackground(background);
			contentPanel.setBackground(background);
			
			this.setLayout(new BorderLayout());
			
			movePanel.add(new Label("\u2261").initFontSize(22).get());
			
			this.add(movePanel, BorderLayout.WEST);
			
			this.add(contentPanel, BorderLayout.CENTER);
			
			redraw();
		}

		private void assign()
		{
			final DragPanel p = this;
			
			movePanel.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{	
					movePanel.setBackground(pressedColor);
					contentPanel.setBackground(pressedColor);
					held = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) 
				{
					movePanel.setBackground(background);
					contentPanel.setBackground(background);
					held = false;
					revalidate();
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					final int y = e.getY();
					
					final int h = getHeight();
					
					if(held)
					{
						if(y <= 3)
						{
							moveUp(p);
						}
						else if(y >= h - 3)
						{
							moveDown(p);
						}
					}
					
				}
			});
		}
	}
}
