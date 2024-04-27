package guiutil;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Grid extends GridBagConstraints
{
	
	public static final int POSITION = 0;
	public static final int SPAN     = 1;
	public static final int ANCHOR   = 2;
	public static final int INSET    = 3;
	
	public Grid()
	{
		this.insets = new Insets(0,0,0,0);
	}
	
	public Grid initAnchor(final int anchor)
	{
		this.setAnchor(anchor);
		return this;
	}
	
	public Grid initInset(final int margin)
	{
		this.setInset(margin, margin, margin, margin);
		return this;
	}
	
	public Grid initInset(final int top, final int left, final int bottom, final int right)
	{
		this.setInset(top, left, bottom, right);
		return this;
	}
	
	
	public Grid set(final int x, final int y)
	{
		this.setGrid(x, y);
		return this;
	}
	
	public Grid set(final int x, final int y, final int w, final int h)
	{
		this.setGrid(x, y, w, h);
		return this;
	}
	
	
	public void setGrid(final int x, final int y)
	{
		setGrid(x, y, 1, 1);
	}
	
	public void setGrid(final int x, final int y, final int w, final int h)
	{
		this.gridx = x;
		this.gridy = y;
		this.gridwidth = w;
		this.gridheight = h;
	}
	
	public void setSpan(final int w, final int h)	
	{
		this.gridwidth  = w;
		this.gridheight = h;
	}
	
	public void setAnchor(final int a)
	{
		this.anchor = a;
	}
	
	public void setInset(final int top, final int left, final int bottom, final int right)
	{
		this.insets.top = top;
		this.insets.left = left;
		this.insets.bottom = bottom;
		this.insets.right = right;
	}
	
	public static Grid getGrid(final int x, final int y, final int w, final int h)
	{
		Grid grid = new Grid();
		
		grid.setAnchor(Grid.EAST);
		grid.setInset(10, 10, 10, 10);
		grid.setGrid(x, y, w, h);
		
		return grid;
	}
			
}