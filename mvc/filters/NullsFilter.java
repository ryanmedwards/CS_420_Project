package mvc.filters;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.Application;
import components.Panel;
import components.custom.checkbox.CheckBoxID;
import opensky.components.StateVectorCheckBoxList;
import opensky.statevector.StateVector;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class NullsFilter extends Filter
{
	private final JCheckBox selectAllCheckBox = new JCheckBox("All");
	private final CheckBoxID[] checkBoxList = StateVectorCheckBoxList.getList();

	final boolean[] saves = new boolean[checkBoxList.length];

	public NullsFilter(final int id)
	{
		super(Filter.Type.NULLS, id);
		
		assign();
		
		super.drawRootPanel();
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
	
	private int[] getSelectedColumns()
	{
		int total = 0;
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			if(checkBoxList[i].isSelected())
			{
				++total;
			}
		}
		
		int[] columns = new int[total];
		int next = 0;
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			if(checkBoxList[i].isSelected())
			{
				columns[next++] = i;
			}
		}
		
		return columns;
	}


	@Override
	public void draw(final JPanel panel) 
	{
		panel.setLayout(new BorderLayout());
		
		final JPanel tempPanel = new Panel().initBoxLayout(BoxLayout.Y_AXIS).get();
		
		tempPanel.add(selectAllCheckBox);
		
		tempPanel.add(Box.createRigidArea(new Dimension(15,15)));
		
		for(final CheckBoxID box: checkBoxList)
		{
			tempPanel.add(box);
		}
		
		final JScrollPane scrollPane = new JScrollPane(tempPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		panel.add(scrollPane, BorderLayout.CENTER);
	}
	
	@Override
	public void saveState()
	{
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			saves[i] = checkBoxList[i].isSelected();
		}
	}
	
	@Override 
	public void revertState()
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
	public boolean filter(StateVectorList data)
	{
		final int[] columns = this.getSelectedColumns();
		
		return StateVectorOP.filterNulls(data, columns);
	}
	

	
	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		return String.format(
				"insert into %s values( %d, %d, \'%s\', %d );", 
				this.getDBTable(),
				id,
				order,
				applied.toString(),
				encode());
	}
	
	@Override
	public String getQueryCondition()
	{
		this.saveState();
		
		return FilterQuery.getNullsQuery(encode());
	}
	
	@Override
	public String getHistory()
	{
		this.saveState();
		
		return FilterHistory.getNullsHistory(encode());
	}
	
	private int encode()
	{
		final int[] selected = new int[checkBoxList.length];
		
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			selected[i] = (checkBoxList[i].isSelected()) ? 1 : 0;
		}
		
		int result = 0;
		
		for(int i = selected.length - 1; i > 0; --i)
		{
			result = (result | selected[i]) << 1;
		}
		
		result = (result | selected[0]);
		
		return result;
	}
}















