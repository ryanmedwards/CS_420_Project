package mvc.filters.duplicates;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.Panel;
import components.custom.checkbox.CheckBoxID;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.main.process.builder.BuilderModel;
import opensky.statevector.StateVectorCheckBoxList;
import sql.FilterTable;
import sql.LocalSQL;

public class DuplicatesFilter extends Filter
{	
	private final JCheckBox selectAllCheckBox = new JCheckBox("All");
	private final CheckBoxID[] checkBoxList = StateVectorCheckBoxList.getList();


	
	public DuplicatesFilter(final FilterModel model, final String name)
	{
		super(model, name);
	
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
		
//		final JPanel tempPanel = new Panel(new GridLayout(0, 1, 5, 5))
//									.initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
//
		final JPanel tempPanel = new Panel(new FlowLayout(FlowLayout.LEADING, 5, 5)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
		
		tempPanel.add(selectAllCheckBox);
		
		tempPanel.add(new JLabel(""));
		
		for(final CheckBoxID box: checkBoxList)
		{
			tempPanel.add(box);
		}
		
		panel.add(tempPanel, BorderLayout.WEST);
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
		return "<html>This filter will remove duplicate entries based on the selected columns.<br>" +
			   "For example, if the columns 'lat' and 'lon' are selected, then entries with the same 'lat' and 'lon' value will be removed.<br>"+
			   "If only the 'lat' values are the same, then the entry will be kept.<html>";
	}

	
	@Override
	public boolean filter(final String table, final JTextArea log)
	{
		log.append("Removing duplicate entries...");
		
		final StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("delete from %s where rowid not in (select min(rowid) from %s ", table, table));
		
		boolean hasSelected = false;
		
		for(int i = 0; i < checkBoxList.length; ++i)
		{
			if(checkBoxList[i].isSelected())
			{
				hasSelected = true;
				break;
			}
		}
		
		if(hasSelected)
		{
			sb.append("group by ");
			
			for(int i = 0; i < checkBoxList.length; ++i)
			{
				if(checkBoxList[i].isSelected())
				{
					sb.append(String.format("%s, ", checkBoxList[i].getName()));
				}
			}
			
			sb.replace(sb.length() - 2, sb.length(), "");	
		}
		
		sb.append(";");
		
		return LocalSQL.update(sb.toString());
	}


	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSQLiteTable()
	{
		return FilterTable.DUPLICATE;
	}
	
	
	@Override
	public boolean logFilter(final int processID, final int filterID)
	{
		int selected = 0;
		
		for(int i = 0; i <= checkBoxList.length; ++i)
		{
			if(checkBoxList[i].isSelected())
			{
				selected = selected & 0b1;
			}
			
			selected <<= 1;
		}

		System.out.println(Integer.toString(selected, 2));
		
		return LocalSQL.update(
				String.format("insert into %s values(%d, %d, %d);",
						processID,
						filterID,
						selected));
	}
	

}




















