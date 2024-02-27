package components;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class Spinner extends JInitializer<JSpinner>
{
	public Spinner initModel(final String...strings)
	{
		try
		{
			get().setModel(new SpinnerListModel(strings));
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Spinner initModel(final int value, final int min, final int max, final int increment)
	{
		try
		{
			get().setModel(new SpinnerNumberModel(value, min, max, increment));
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Spinner initModel(final double value, final double min, final double max, final double increment)
	{
		try
		{
			get().setModel(new SpinnerNumberModel(value, min, max, increment));
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Spinner initNumberFormat(final String format)
	{
		try
		{
			get().setEditor(new JSpinner.NumberEditor(get(), format));
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Spinner initArrows(final boolean hide)
	{
		if(hide)
		{
			get().setUI(new BasicSpinnerUI() 
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
		
		return this;
	}

	@Override
	public JSpinner create() 
	{
		return new JSpinner();
	}
}
