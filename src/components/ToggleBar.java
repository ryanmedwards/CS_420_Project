package components;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import guiutil.Function;

public class ToggleBar extends Panel 
{
	public final ToggleButton[] buttons;
	
	private int selectedIndex;
	
	private final List<Function> functionList = new ArrayList<>();
	
	/**
	 * 
	 * @param size
	 * @param function
	 * @param options
	 */
	public ToggleBar(final int size, final String... options)
	{
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		buttons = new ToggleButton[options.length];

		for(int i = 0; i < buttons.length; ++i)
		{
			buttons[i] = new ToggleButton(options[i], i);
		}
		
		for(final ToggleButton button: buttons)
		{
			button.setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
			
			button.addActionListener(e ->
			{
				buttons[selectedIndex].setSelected(false);
				
				selectedIndex = button.id;
				
				buttons[selectedIndex].setSelected(true);
				
				button.revalidate();
				
				for(final Function function: functionList)
				{
					function.run();
				}
			});
			
			this.add(button);
		}
		
		selectedIndex = 0;
		buttons[selectedIndex].setSelected(true);
	}

	public ToggleBar initBorder(final Border border)
	{
		this.setBorder(border);
		return this;
	}
	
	public ToggleBar initUpdateFunctions(final Function... functions)
	{
		for(final Function function: functions)
		{
			functionList.add(function);
		}
		
		return this;
	}
	
	public String getSelectedItem()
	{
		return buttons[selectedIndex].getSelectedString();
	}
}
