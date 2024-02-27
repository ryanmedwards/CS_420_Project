package components.custom;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import components.JInitializer;

public class RootPanel extends JInitializer<JPanel>
{
	public RootPanel()
	{
		this(new JPanel());
	}
	
	public RootPanel(final Container container)
	{
		final int t = 15; // Top
		final int l = 15; // Left
		final int b = 15; // Bottom
		final int r = 15; // Right
		
		get().setBorder(BorderFactory.createEmptyBorder(t, l, b, r));
		
		get().setLayout(new BorderLayout());
		
		get().add(container, BorderLayout.CENTER);
	}
	
	@Override
	public JPanel create() 
	{
		return new JPanel();
	}
}
