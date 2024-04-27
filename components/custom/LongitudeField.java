package components.custom;

import javax.swing.JSpinner;

import components.JInitializer;
import components.Spinner;

public class LongitudeField extends JInitializer<JSpinner>
{
	@Override
	public JSpinner create() 
	{
		return new Spinner()
							.initModel(0, -180.0000, 179.9999, 1.0)
							.initNumberFormat("0.0000")
							.get();
	}
}
