package components.custom;

import javax.swing.JSpinner;

import components.JInitializer;
import components.Spinner;

public class LatitudeField extends JInitializer<JSpinner>
{

	@Override
	public JSpinner create() 
	{
		return new Spinner().initArrows(false)
							.initModel(0, -90.0000, 90.0000, 0.0001)
							.initNumberFormat("0.0000")
							.get();
	}
	
}
