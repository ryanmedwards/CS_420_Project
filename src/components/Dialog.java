package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import application.Application;

public class Dialog extends JDialog
{

	private final Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); 
	
	private final Button confirmButton = new Button("Confirm");
	private final Button cancelButton = new Button("Cancel");
	
	private boolean result;
	
	public boolean getResult()
	{
		return result;
	}

	public Dialog(Frame frame, String string, boolean b) 
	{
		super(frame, string, b);
		
		draw();
		assign();
	}
	
	private void draw()
	{
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
	}
	
	private void assign()
	{
		confirmButton.addActionListener(e ->
		{
			result = true;
			this.dispose();
		});
		
		cancelButton.addActionListener(e ->
		{
			result = false;
			this.dispose();
		});
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) // Invoked when Window Exit Button Pressed
			{
				result = false;
			}
		});
	}
	
	public void addContent(final Panel panel)
	{
		try
		{
			final Panel rootPanel = new Panel(new BorderLayout());
			
			rootPanel.add(panel, BorderLayout.CENTER);
			rootPanel.add(buttonPanel, BorderLayout.SOUTH);
			
			this.getContentPane().add(rootPanel);
			this.pack();
			
			this.setLocationRelativeTo(Application.getFrame());
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public boolean launch()
	{
		this.setVisible(true);
		
		return result;
	}
}
