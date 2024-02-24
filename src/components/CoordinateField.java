package components;

import javax.swing.SpinnerNumberModel;



public class CoordinateField extends Spinner
{
	public static final int LATITUDE = 0;
	public static final int LONGITUDE = 1;
	
	public CoordinateField(final int type)
	{		
		switch(type)
		{
		case LATITUDE:
			
			this.setModel(new SpinnerNumberModel(0, -90, 90, 0.0001));
			
			break;
			
		default: // LATITUDE
			
			this.setModel(new SpinnerNumberModel(0, -180, 179.9999, 0.0001));
			
		}
		
		this.setEditor(new Spinner.NumberEditor(this, "0.0000"));
		
		this.setHideArrows(true);
	}
	
	public double get()
	{
		return (double) this.getValue();
	}
}
