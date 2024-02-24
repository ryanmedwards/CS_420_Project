package components;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class LabelCombine<T> extends Panel
{
	public final JLabel label;
	public final T      component;
	
	public LabelCombine(final String label, final T component)
	{
		this(new Label(label), component);
	}
	
	public LabelCombine(final Label label, final T component)
	{
		this.label = label;
		this.component = component;
		
		this.setLayout(new GridLayout(1, 2));
		
		this.add(this.label);
		
		if(component instanceof JComponent)
		{
			this.add((JComponent) this.component);
		}
		else
		{
			this.add(new Label("Error!"));
		}
	}
}
