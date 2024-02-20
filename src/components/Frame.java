package components;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;

import application.Application;

public class Frame extends JFrame
{
	
	public Frame() {}
	

	
	
	public Frame initLayout(final BorderLayout layout)
	{
		this.setLayout(layout);
		return this;
	}
	
	public Frame initLayout(final FlowLayout layout)
	{
		this.setLayout(layout);
		return this;
	}
	
	public Frame initLayout(final GridBagLayout layout)
	{
		this.setLayout(layout);
		return this;
	}
	
	public Frame initLayout(final GridLayout layout)
	{
		this.setLayout(layout);
		return this;
	}
	
	public Frame initContent(final Panel panel)
	{
		this.add(panel);
		return this;
	}
	
	public Frame initTitle(final String title)
	{
		this.setTitle(title);
		return this;
	}
	
	public Frame initSize(final int x, final int y)
	{
		this.setSize(new Dimension(x, y));
		return this;
	}
	
	public Frame initPreferredSize(final int x, final int y)
	{
		this.setPreferredSize(new Dimension(x, y));
		return this;
	}
	
	public Frame initMinimumSize(final int x, final int y)
	{
		this.setMinimumSize(new Dimension(x, y));
		return this;
	}
	
	public Frame initResizable(final boolean resizable)
	{
		this.setResizable(resizable);
		return this;
	}
	
	public Frame initVisible(final boolean visible)
	{
		this.setVisible(visible);
		return this;
	}
	
	public Frame initDefaultCloseOperation(final int operation)
	{
		this.setDefaultCloseOperation(operation);
		return this;
	}
	
	public Frame initLocationRelativeTo(final Frame component)
	{
		this.setLocationRelativeTo(component);
		return this;
	}
	
	
}
