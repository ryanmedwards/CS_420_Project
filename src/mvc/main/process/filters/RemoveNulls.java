package mvc.main.process.filters;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import components.CheckBox;
import components.Label;
import components.Panel;
import opensky.statevector.StateVectorCheckBoxList;

public class RemoveNulls extends Filter<RemoveNulls>
{

	private final CheckBox selectAllCheckBox = new CheckBox("All");
	private final CheckBox[] checkBoxList = StateVectorCheckBoxList.getList();

	public RemoveNulls(final FilterModel parent)
	{
		super(parent, "Remove Nulls");
		
		assign();
	}
	
	private void assign()
	{
		selectAllCheckBox.addActionListener(e ->
		{
			final boolean b = selectAllCheckBox.isSelected() ? true : false;

			for(final CheckBox box: checkBoxList)
			{
				box.setSelected(b);
			}
		});
	}


	@Override
	public void drawPanel(final Panel panel) 
	{
		panel.setLayout(new GridLayout(0, 1, 5, 5));
		
		panel.add(selectAllCheckBox);
		
		panel.add(new Label(""));
		
		for(final CheckBox box: checkBoxList)
		{
			panel.add(box);
		}
	}
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(400, 600);
	}
	
	@Override
	public void execute()
	{
		
	}

	final boolean[] saves = new boolean[checkBoxList.length];
	
	@Override
	protected void copy()
	{
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			saves[i] = checkBoxList[i].isSelected();
		}
	}
	
	@Override
	protected void reset()
	{
		this.selectAllCheckBox.setSelected(false);
		
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			checkBoxList[i].setSelected(false);
		}
	}
	
	@Override 
	public void revert()
	{
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			this.checkBoxList[i].setSelected(saves[i]);
		}
	}



	@Override
	protected String getDescription() 
	{
		return "<html>Entries with Null values in the selected columns will be removed.<html>";
	}
	
	@Override
	protected String getName() {
		return "Reduce Boundary";
	}


}
