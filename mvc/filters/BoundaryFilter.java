package mvc.filters;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import application.Application;
import opensky.statevector.StateVectorList;

public class BoundaryFilter extends Filter
{

	private final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	private final BoundaryBoxFilter boxFilter;
	private final BoundaryPointFilter pointFilter;
	
	private int tab = 0;

	
	public BoundaryFilter(final int id) 
	{
		super(Filter.Type.BOUNDARY, id);
		
		this.boxFilter = new BoundaryBoxFilter(id);
		this.pointFilter = new BoundaryPointFilter(id);
		
		super.drawRootPanel();
	}

	@Override
	public DBTable getDBTable()
	{
		final int index = this.tabPane.getSelectedIndex();
		
		switch(index)
		{
		case 0: return DBTable.BOUNDARY_BOX;
		
		case 1: return DBTable.BOUNDARY_POINT;
		}
		
		return null;
	}
	


	
	@Override
	protected String getDescription() 
	{
		return "<html>"
			     + "<p><p>"
			     + "Reduce the boundary of the data set."
			     + "<p><p>"
			     + "<html>";
	}

	@Override
	public void draw(JPanel panel) 
	{
		panel.setLayout(new BorderLayout());
		
		this.tabPane.addTab("Box", boxFilter.getRawRootPanel());
		this.tabPane.addTab("Point", pointFilter.getRawRootPanel());

		final JScrollPane pane = new JScrollPane(tabPane);
		
		pane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		panel.add(pane, BorderLayout.CENTER);
		
	}

	@Override
	public void revertState() 
	{
		this.boxFilter.revertState();
		this.pointFilter.revertState();
	}


	@Override
	public void saveState() 
	{
		this.tab = this.tabPane.getSelectedIndex();
	}

	@Override
	public boolean filter(StateVectorList data) 
	{
		this.saveState();
		
		if(tab == 0)
		{
			return this.boxFilter.filter(data);
		}
		else if(tab == 1)
		{
			return this.pointFilter.filter(data);
		}
		else
		{
			return false;
		}
	}



	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		if(tab == 0)
		{
			return this.boxFilter.getLog(id, order, applied);
		}
		else if(tab == 1)
		{
			return this.pointFilter.getLog(id, order, applied);
		}
		else
		{
			return "";
		}
	}

	@Override
	public String getQueryCondition()
	{
		this.saveState();

		if(tab == 0)
		{
			return this.boxFilter.getQueryCondition();
		}
		else if(tab == 1)
		{
			return this.pointFilter.getQueryCondition();
		}
		else
		{
			return "";
		}
	}

	@Override
	public String getHistory()
	{
		this.saveState();
		
		if(tab == 0)
		{
			return this.boxFilter.getHistory();
		}
		else if(tab == 1)
		{
			return this.pointFilter.getHistory();
		}
		else
		{
			return "";
		}
	}

	
}
