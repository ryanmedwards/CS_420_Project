package mvc.filters;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import application.Application;
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import components.Panel;
import guiutil.Displayable;
import guiutil.OptionPane;
import opensky.statevector.StateVectorList;

public abstract class Filter
{
	public enum Applied
	{
		JAVA, QUERY
	}
	
	public enum QueryType 
	{
		WHERE, GROUP_BY
	}

	public enum DBTable
	{
		DATE_TIME             ("filter_date_time"),
		BOUNDARY_BOX          ("filter_boundary_box"),
		BOUNDARY_POINT 		  ("filter_boundary_point"),
		NULLS                 ("filter_nulls"),
		DUPLICATES            ("filter_duplicates"),
		EXPIRED               ("filter_expired"),
		AIRLINE				  ("filter_airlines"),
		SERIAL				  ("filter_serials");
		
		final String table;
		
		private DBTable(final String table)
		{
			this.table = table;
		}
		
		public static DBTable getEnum(final String table)
		{
			for(DBTable e: values())
			{
				if(e.table.equalsIgnoreCase(table))
				{
					return e;
				}
			}	
			throw new IllegalArgumentException();
		}
		
		@Override
		public String toString()
		{
			return table;
		}
	}
	
	public enum Type
	{
		DATE_TIME		("Date and Time"				, QueryType.WHERE	 , DBTable.DATE_TIME),
		BOUNDARY		("Reduce Boundary"				, QueryType.WHERE    , DBTable.BOUNDARY_BOX),
		BOUNDARY_BOX	("Reduce Boundary Box"			, QueryType.WHERE	 , DBTable.BOUNDARY_BOX),
		BOUNDARY_POINT	("Reduce Boundary Center Point"	, QueryType.WHERE	 , DBTable.BOUNDARY_POINT),
		NULLS			("Remove Nulls"					, QueryType.WHERE	 , DBTable.NULLS),
		DUPLICATES		("Remove Duplicates"			, QueryType.GROUP_BY , DBTable.DUPLICATES),
		EXPIRED			("Remove Expired Entires"		, QueryType.WHERE	 , DBTable.EXPIRED),
		AIRLINES		("Select Airlines"				, QueryType.WHERE	 , DBTable.AIRLINE),
		SERIALS			("Select Recievers"				, QueryType.WHERE	 , DBTable.SERIAL);
		
		public final String name;
		public final QueryType queryType;
		protected final DBTable table;
		
		private Type(final String name, final QueryType queryType, final DBTable table)
		{
			this.name = name;
			this.queryType = queryType;
			this.table = table;
		}
		
		public static Filter.Type getEnum(final String name)
		{
			for(Filter.Type t: values())
			{
				if(t.name.equalsIgnoreCase(name))
				{
					return t;
				}
			}
			
			throw new IllegalArgumentException();
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}


	

	public final JPanel rootPanel = new RootPanel().get();
	
	private final JPanel descriptionPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 25))
												.initBorder(BorderFactory.createTitledBorder(
																BorderFactory.createMatteBorder(1, 0, 0, 0, rootPanel.getForeground()), 
																"Description")).get();
	
	public final Type type;
	public Applied applied;
	
	
	public final int id;

	public Filter(final Filter.Type type, final int id)
	{ 
		this.type = type;
		this.id = id;
	}
	
	
	public DBTable getDBTable()
	{
		return this.type.table;
	}
	


	
	/**
	 * 
	 * 
	 */
	protected void drawRootPanel()
	{
		final JPanel panel = new Panel().initBorder(BorderFactory.createTitledBorder(
														BorderFactory.createMatteBorder(
																1, 0, 0, 0, rootPanel.getForeground()), "Controls")).get();
		
		draw(panel);
		
		descriptionPanel.add(new JLabel(getDescription()));
		
		rootPanel.add(descriptionPanel, BorderLayout.NORTH);
		rootPanel.add(panel, BorderLayout.CENTER);
	}



	
	
	private static List<Filter> getFiltersByType(final List<Filter> filters, final QueryType queryType)
	{
		final List<Filter> result = new ArrayList<>();
		
		for(final Filter filter: filters)
		{
			if(filter.type.queryType.equals(queryType))
			{
				result.add(filter);
			}
		}
		
		return result;
	}
	
	
	public static String buildQuery(final List<Filter> filters, final String table)
	{
		if(filters.size() == 0)
		{
			OptionPane.showError("No filters given.");
			return null;
		}
		
		final StringBuilder sb = new StringBuilder();
		
		final List<Filter> whereFilters = Filter.getFiltersByType(filters, QueryType.WHERE);
		
		final List<Filter> groupByFilters = Filter.getFiltersByType(filters, QueryType.GROUP_BY);
		
		if(groupByFilters.size() > 1)
		{
			OptionPane.showError("Cannot create query for multiple GROUP BY filters.");
			return null;
		}
		
		sb.append("select "
				+ "time,icao24,lat,lon,velocity,"
				+ "heading,vertrate,callsign,onground,"
				+ "alert,spi,squawk,baroaltitude,geoaltitude,"
				+ "lastposupdate,lastcontact,hour "
				+ "from " + table + " \r\n");
		
		if(whereFilters.size() > 0)
		{
			sb.append("where ");
			
			for(int i = 0; i < whereFilters.size(); ++i)
			{
				sb.append(whereFilters.get(i).getQueryCondition());
				
				if(i < whereFilters.size() - 1)
				{
					sb.append(" and \r\n");
				}	
			}
		}
		
		
		if(groupByFilters.size() == 1) 
		{
			sb.append(groupByFilters.get(0).getQueryCondition());
		}
		
		sb.append("\r\n");
		
		return sb.toString();
	}
	
	
	/**
	 * Applies the filter to the data set.
	 * <br>
	 * This method should alter the data set given rather than producing a copy.
	 * 
	 * @param data
	 * @return true if operation is successful, false if unsuccessful
	 */
	public abstract boolean filter(StateVectorList data);
	

	
	/**
	 * This method returns a string that will be displayed to the user.
	 * <br>
	 * The string should describe what the filter will do to the data set.
	 * 
	 * @return string description of the filter
	 */
	protected abstract String getDescription();

	/**
	 * This method should place the filters GUI controls on the given panel.
	 * 
	 * @param panel 
	 */
	public abstract void draw(final JPanel panel);

	
	/**
	 * Save the values from Filter's GUI controls.
	 */
	public abstract void saveState();
	
	/**
	 * Set the Filter's GUI controls to their last saved vaules.
	 */
	public abstract void revertState();

	
	
	/**
	 * Creates a SQL INSERT statement that should log the filters information
	 * to it DBTable.
	 * 
	 * @param id
	 * @param order
	 * @param applied
	 * @return
	 */
	public abstract String getLog(final int id, final int order, final Filter.Applied applied); 
	
	public abstract String getQueryCondition();
	
	public abstract String getHistory();
	
	@Override
	public String toString()
	{
		return this.type.name;
	}
	
}












