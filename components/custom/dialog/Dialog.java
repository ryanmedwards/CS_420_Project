package components.custom.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import application.Application;
import components.custom.RootPanel;


public class Dialog extends JDialog
{


	private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15)); 
	
	private final JButton confirmButton = new JButton("Confirm");
	private final JButton cancelButton = new JButton("Cancel");
	
	private final JButton closeButton = new JButton("Close");
	
	private boolean result = false;
	
	private DialogMode currentMode;
	
	public void setMode(final DialogMode mode)
	{
		if(mode == null)
		{
			return;
		}
		
		if(mode == currentMode)
		{
			return;
		}
		
		currentMode = mode;
		
		switch(mode)
		{
		
		case CANCEL_CONFIRM:
			
			buttonPanel.removeAll();
			
			buttonPanel.add(cancelButton);
			buttonPanel.add(confirmButton);
			
			this.revalidate();
			this.repaint();
			
			break;
			
			
		case CLOSE:
			
			buttonPanel.removeAll();
			
			buttonPanel.add(closeButton);
			
			this.revalidate();
			this.repaint();
			
			break;
		}
	}
	
	
	
	
	public boolean getResult()
	{
		return result;
	}

	public Dialog(final JFrame parent, final String title, final boolean setModal) 
	{
		super(parent, title, setModal);
		
		currentMode = DialogMode.CLOSE;
		
		draw();
		assign();
	}
	
	private void draw()
	{
		buttonPanel.add(closeButton);
	}
	
	private void assign()
	{
		confirmButton.addActionListener(this.getConfirmButtonActionListener());
		
		cancelButton.addActionListener(e ->
		{
			result = false;
			this.dispose();
		});
		
		closeButton.addActionListener(e ->
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
	
	public void addContent(final JPanel panel)
	{
		try
		{
			final JPanel rootPanel = new JPanel(new BorderLayout());
			
			rootPanel.add(panel      , BorderLayout.CENTER);
			rootPanel.add(buttonPanel, BorderLayout.SOUTH);
			
			this.getContentPane().add(rootPanel);
			this.pack();
			
			this.setLocationRelativeTo(Application.getApp());
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public Dialog initContent(final Container container)
	{
		try
		{
			final JPanel rootPanel = new RootPanel().get();
			
			rootPanel.add(container  , BorderLayout.CENTER);
			rootPanel.add(buttonPanel, BorderLayout.SOUTH);
			
			this.getContentPane().add(rootPanel);
			this.pack();
			
			this.setLocationRelativeTo(Application.getApp());
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public ActionListener getConfirmButtonActionListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				result = true;
				dispose();
			}
		};
	}
	
	public Dialog initMode(final DialogMode mode)
	{
		setMode(mode);
		return this;
	}
	
	public Dialog initPreferredSize(final int x, final int y)
	{
		this.setMinimumSize(new Dimension(x, y));
		return this;
	}
	
	public boolean launch()
	{
		this.setVisible(true);
		
		return result;
	}
	
	
	
	
}
