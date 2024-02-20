package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

import guiutil.Function;

public class Spinner extends JSpinner
{
	public Spinner()
	{
		
	}
	
	public Spinner(final String[] values)
	{
		super(new SpinnerListModel(values));
	}
	
	public Spinner(int val, int min, int max, int incr)
	{
		super(new SpinnerNumberModel(val, min, max, incr));
	}
	
	public Spinner(double val, double min, double max, double incr)
	{
		super(new SpinnerNumberModel(val, min, max, incr));
	}


	public Spinner initNumberFormat(final String format)
	{
		if(this.getModel() instanceof SpinnerNumberModel)
		{
			this.setEditor(new Spinner.NumberEditor(this, format));
		}
		
		return this;
	}
	
	public void clear()
	{
		this.setValue("");
	}
	
	public void setHideArrows(final boolean hide)
	{
		if(hide)
		{
			this.setUI(new BasicSpinnerUI() 
			{
	            protected Component createNextButton() 
	            {
	                return null;
	            }

	            protected Component createPreviousButton() 
	            {
	                return null;
	            }
	        });
		}
	}
	
	public Spinner initHideArrows(final boolean hide)
	{
		setHideArrows(hide);
		return this;
	}
	
	public Spinner initChangeListener(final ChangeListener listener)
	{
		this.addChangeListener(listener);
		return this;
	}
	
	public Spinner initFocusListener()	
	{
		this.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent e) 
			{
				System.out.println("ASDASDASDASDAD");
				clear();
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				
			}
		});
		
		return this;
	}
}
