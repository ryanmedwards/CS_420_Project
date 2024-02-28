package mvc.main.process.builder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import application.Application;
import components.Label;
import components.Panel;
import components.buttons.Button;
import components.custom.Dialog;
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
	
	protected final FilterModel filterModel = new FilterModel();
	
	
	
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final List<BuilderPanel> filterPanels = new ArrayList<>();
	

	
	protected final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
	
	protected final JPanel bufferPanel = new JPanel(new BorderLayout());
	
	protected final JPanel filtersPanel = new JPanel(new GridLayout(0, 1, 15, 15));
	
	
	protected final JLabel processLabel = new Label("Process").initFontSize(18).get();


	protected final JButton addButton = new Button("+").initToolTip("Add Filters to Process.").get();
	
	protected final JPanel addPanel = new Panel().initBoxLayout(BoxLayout.X_AXIS).get();
	
	
	
	public final BuilderView       view    = new BuilderView(this);
	public final BuilderController control = new BuilderController(this);
	

}


class BuilderPanel extends JPanel
{
	protected static final Dimension LEFT_BOX_MARGIN = new Dimension(5, 5);
	
	protected final Filter filter;

	private final JButton removeButton = new Button("\u2716").initToolTip("Remove").get();
	private final JButton editButton   = new Button("\u2699").initToolTip("Edit"  ).get();

	protected BuilderPanel(final BuilderModel model, final Filter filter) 
	{
		this.filter = filter;
		
		draw();
		
		assign(model);
	}

	private void draw() 
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(Box.createRigidArea(LEFT_BOX_MARGIN));
		this.add(editButton);
		this.add(Box.createRigidArea(new Dimension(15, 5)));
		this.add(new JLabel(filter.name));
		this.add(Box.createHorizontalGlue());
		this.add(removeButton);
		this.add(Box.createRigidArea(new Dimension(15, 5)));
	}
	
	private void assign(final BuilderModel model)
	{
		editButton.addActionListener(e -> filter.edit());	
		
		removeButton.addActionListener(e -> 
		{
			filter.remove();
			
			model.control.removeFilter(filter);
		});
	}
}
