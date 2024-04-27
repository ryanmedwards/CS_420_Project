package mvc.source;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.JFrame;

import components.custom.dialog.Dialog;

public class SourceSelector extends Dialog
{
	
	private final SourceModel model;

	
	public SourceSelector(final JFrame parent, final Set<Source.Type> types) 
	{
		super(parent, "Source Selector", true);
		
		this.model = new SourceModel(types);
		
		this.setMode(Dialog.Mode.CANCEL_CONFIRM);
		
		this.addContent(model.rootPanel);
		
		this.setPreferredSize(new Dimension(1500, 1200));
	}

	
	public boolean hasValidSource()
	{
		return model.hasValidSource();
	}
	
	public Source getSource()
	{
		return model.getSource();
	}
	
}
