package mvc.main.process.builder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import application.Application;
import components.Label;
import components.Panel;
import components.custom.Dialog;
import components.custom.OrderPanel;
import components.custom.RootPanel;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.filters.boundary.BoundaryFilter;
import mvc.filters.datetime.DateTimeFilter;
import mvc.filters.duplicates.DuplicatesFilter;
import mvc.filters.expired.ExpiredFilter;
import mvc.filters.nulls.NullsFilter;


public class BuilderModel 
{
	public final JPanel rootPanel = new RootPanel().get();
	
	

	
	protected final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 10));
	

	public final OrderPanel<Filter> filtersPanel = new OrderPanel<>();


	protected final JLabel processLabel = new Label("Process").initFontSize(18).get();

	protected final JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
	protected final JButton addButton = new JButton("Add");

	
	
	
	
	
	
	protected final FilterModel filterModel = new FilterModel();
	
	
	
	public final BuilderView       view    = new BuilderView(this);
	public final BuilderController control = new BuilderController(this);
	


}
