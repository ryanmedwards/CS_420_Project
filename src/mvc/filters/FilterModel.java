package mvc.filters;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import application.Application;
import components.custom.Dialog;
import components.custom.RootPanel;
import mvc.filters.boundary.BoundaryFilter;
import mvc.filters.datetime.DateTimeFilter;
import mvc.filters.duplicates.DuplicatesFilter;
import mvc.filters.expired.ExpiredFilter;
import mvc.filters.nulls.NullsFilter;

public class FilterModel 
{

	public final JPanel rootPanel = new JPanel(new BorderLayout());
	
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
	
	protected final JPanel aboutPanel = new JPanel();
	
	
	
	protected final Dialog dialog = new Dialog(Application.getApp(), "Filter Editor", true)
											 .initPreferredSize(1000, 800);
	
	
	protected int selectedFilter = 0;

	
//	protected final BoundaryFilter   boundaryFilter   = new BoundaryFilter();
//	protected final DuplicatesFilter duplicatesFilter = new DuplicatesFilter();
//	protected final NullsFilter      nullsFilter      = new NullsFilter();
//	protected final ExpiredFilter    expiredFilter    = new ExpiredFilter();
//	protected final DateTimeFilter   dateTimeFilter   = new DateTimeFilter();
//	
	
	protected final Filter[] filters = new Filter[] 
	{
		new DateTimeFilter(this),
		new BoundaryFilter(this),
		new NullsFilter(this),
		new DuplicatesFilter(this),
		new ExpiredFilter(this)
	};
	
	public final FilterView       view    = new FilterView(this);
	public final FilterController control = new FilterController(this);
}

class AboutPanel extends JPanel
{
	
	private final String description =
			  "<html>"
			+ ""
			+ "<html>";
	
	private AboutPanel()
	{
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, getForeground()), "About"));
		
		
	}
}
