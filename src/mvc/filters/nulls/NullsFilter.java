package mvc.filters.nulls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.Label;
import components.Panel;
import components.custom.checkbox.CheckBoxID;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;
import opensky.statevector.StateVectorCheckBoxList;

public class NullsFilter extends Filter
{

	private final JCheckBox selectAllCheckBox = new JCheckBox("All");
	private final CheckBoxID[] checkBoxList = StateVectorCheckBoxList.getList();

	public NullsFilter(final FilterModel model)
	{
		super(model, "Remove Nulls");
		
		assign();
		
		super.drawDialog();
	}
	
	private void assign()
	{
		selectAllCheckBox.addActionListener(e ->
		{
			final boolean b = selectAllCheckBox.isSelected() ? true : false;

			for(final CheckBoxID box: checkBoxList)
			{
				box.setSelected(b);
			}
		});
	}


	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new BorderLayout());
		
		final JPanel tempPanel = new Panel(new GridLayout(0, 1, 5, 5))
				.initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
		
		tempPanel.add(selectAllCheckBox);
		
		tempPanel.add(new JLabel(""));
		
		for(final CheckBoxID box: checkBoxList)
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
