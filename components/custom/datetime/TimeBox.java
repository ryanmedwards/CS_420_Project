package components.custom.datetime;

import javax.swing.JComboBox;

import components.JInitializer;
import guiutil.BoxItem;

public class TimeBox extends JInitializer<JComboBox<BoxItem>>
{

	public TimeBox()
	{
		int t = 0;
		
		get().addItem(new BoxItem("12 a.m.", t));
		
		t += 3600;
		
		for(int i = 1; i < 12; ++i)
		{	
			get().addItem(new BoxItem(i + " a.m.", t));
			
			t += 3600;
		}
		
		
		get().addItem(new BoxItem("12 p.m.", t));
		
		t += 3600;
		
		for(int i = 1; i < 12; ++i)
		{	
			get().addItem(new BoxItem(i + " p.m.", t));
			
			t += 3600;
		}
	}

	@Override
	public JComboBox<BoxItem> create() 
	{
		return new JComboBox<>();
	}
}
