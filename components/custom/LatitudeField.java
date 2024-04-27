package components.custom;

import javax.swing.JSpinner;

import components.JInitializer;
import components.Spinner;

public class LatitudeField extends JInitializer<JSpinner>
{

	@Override
	public JSpinner create() 
	{
		return new Spinner()
							.initModel(0, -90.0000, 90.0000, 1.0)
							.initNumberFormat("0.0000")
							.get();
	}
	
}
