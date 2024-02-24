package components;

import java.awt.GridLayout;

import guiutil.Grid;

public class LabelField extends Panel
{
	public final Label label;
	public final TextField textField;
	
	public LabelField(final String label, final int col)
	{
		this.label     = new Label(label);
		this.textField = new TextField(col, true);
		
		
		this.add(this.label);
		this.add(this.textField);
	}
}
