package components.custom;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import components.JInitializer;
import guiutil.Function;

public class ToggleBar<T> extends JPanel
{
	
	
	private final List<ToggleBarButton> buttons = new ArrayList<>();
	
	private int selectedIndex = 0;
	
	private final List<Function> functions = new ArrayList<>();
	

	
	public void addUpdateFunctions(final Function...functions)
	{
		for(final Function f: functions)
		{
			this.functions.add(f);
		}
	}

	public ToggleBar(final int borderSize, final T... objects)
	{
		this(borderSize, 1, 0, objects);
	}
	
	public ToggleBar(final int borderSize, final int rows, final int cols, final T...objects)
	{
		this.setLayout(new GridLayout(rows, cols));
		
		for(final T t: objects)
		{
			buttons.add(new ToggleBarButton(borderSize, t));
		}
		
		for(final ToggleBarButton b: buttons)
		{
			this.add(b);
		}
		
		buttons.get(selectedIndex).setSelected(true);
	}
	
	public T getSelectedItem()
	{
		final T t = buttons.get(selectedIndex).object;
		
		if(t == null)
		{
			throw new NullPointerException();
		}
		
		return t;
	}
	
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
	
	public void setSelectedIndex(final int index)
	{
		if(index < 0 || index >= buttons.size())
		{
			System.err.println("ToggleBar Index out of Bounds.");
			return;
		}
		
		buttons.get(selectedIndex).setSelected(false);
		
		selectedIndex = index;
		
		buttons.get(selectedIndex).setSelected(true);
	}
	
	public ToggleBar<T> initUpdateFunctions(final Function...functions)
	{
		for(final Function f: functions)
		{
			this.functions.add(f);
		}
		return this;
	}
	
	private int next = 0;
	
	private class ToggleBarButton extends JToggleButton
	{

		
		private final int id = next++;

		private final T object;
		
		private ToggleBarButton(final int size, final T object)
		{
			this.object = object;

			this.setText(object.toString());
			this.setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
	
			
			this.addActionListener(e ->
			{	
				buttons.get(selectedIndex).setSelected(false);
				
				selectedIndex = id;
				
				buttons.get(selectedIndex).setSelected(true);
				
				this.revalidate();
				this.repaint();
				
				for(final Function f: functions)
				{
					f.run();
				}
			});
		}
		

	}
	
}
