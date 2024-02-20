package mvc.main.process.filters;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import components.Button;
import components.CheckBox;
import components.Label;
import components.Panel;
import opensky.statevector.StateVectorCheckBoxList;
import opensky.statevector.StateVectorCheckBoxTree;

public class RemoveDuplicates extends Filter<RemoveDuplicates>
{

	private final CheckBox selectAllCheckBox = new CheckBox("All");
	private final CheckBox[] checkBoxList = StateVectorCheckBoxList.getList();

		
	public RemoveDuplicates(final FilterModel parent)
	{
		super(parent, "Remove Duplicates");
		

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
		return "<html>This filter will remove duplicate entries based on the selected columns.<br>" +
			   "For example, if the columns 'lat' and 'lon' are selected, then entries with the same 'lat' and 'lon' value will be removed.<br>"+
			   "If only the 'lat' values are the same, then the entry will be kept.<html>";
	}


	@Override
	protected String getName() 
	{
		return "Remove Duplicates";
	}

}
