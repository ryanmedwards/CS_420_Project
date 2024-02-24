package mvc.filters.nulls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import components.CheckBox;
import components.Label;
import components.Panel;
import mvc.filters.Filter;
import mvc.main.process.builder.BuilderModel;
import opensky.statevector.StateVectorCheckBoxList;

public class NullsFilter extends Filter
{

	private final CheckBox selectAllCheckBox = new CheckBox("All");
	private final CheckBox[] checkBoxList = StateVectorCheckBoxList.getList();

	public NullsFilter(final BuilderModel parent)
	{
		super(parent, "Remove Nulls");
		
		assign();
		
		super.drawDialog();
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
		panel.setLayout(new BorderLayout());
		
		final Panel tempPanel = new Panel(new GridLayout(0, 1, 5, 5))
				.initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		tempPanel.add(selectAllCheckBox);
		
		tempPanel.add(new Label(""));
		
		for(final CheckBox box: checkBoxList)
		{
			tempPanel.add(box);
		}
		
		panel.add(tempPanel, BorderLayout.WEST);
	}

	
	@Override
	public void execute()
	{
		
	}

	final boolean[] saves = new boolean[checkBoxList.length];
	
	@Override
	public void copy()
	{
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			saves[i] = checkBoxList[i].isSelected();
		}
	}
	
	@Override
	public void reset()
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
	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}


}
