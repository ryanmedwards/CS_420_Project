package mvc.main.process.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import components.custom.RootPanel;
import components.custom.dialog.Dialog;
import mvc.filters.Filter;
import mvc.filters.FilterModel;
import mvc.filters.boundary.BoundaryFilter;
import mvc.filters.datetime.DateTimeFilter;
import mvc.filters.duplicates.DuplicatesFilter;
import mvc.filters.expired.ExpiredFilter;
import mvc.filters.nulls.NullsFilter;


public class BuilderModel 
{
	
	protected static Color background;
	
	static
	{
		final Color color =  UIManager.getColor("TextArea.background");
		
		if(color != null) 
		{
			background = color;
		}
		else
		{
			background = new JPanel().getBackground();
		}
	}
	
	protected final FilterModel filterModel = new FilterModel();
	
	
	
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final List<BuilderPanel> filterPanels = new ArrayList<>();
	

	
	protected final JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 120, 10));
	
	protected final JPanel bufferPanel = new Panel(new BorderLayout())
											 .initBackground(background)
											 .initBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25))
											 .get();
	
	protected final JPanel filtersPanel = new Panel(new GridLayout(0, 1, 15, 15)).initBackground(background).get();
	
	
	protected final JLabel processLabel = new Label("Process").initFontSize(18).get();


	protected final JButton addButton = new Button("+").initFontStyle(Font.BOLD).initToolTip("Add Filters to Process.").get();
	
	protected final JPanel addPanel = new Panel().initBoxLayout(BoxLayout.X_AXIS).initBackground(background).get();
	
	
	
	public final BuilderView       view    = new BuilderView(this);
	public final BuilderController control = new BuilderController(this);
	

}


class BuilderPanel extends JPanel
{
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
		this.setBackground(BuilderModel.background);
		//this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(editButton);
		this.add(Box.createRigidArea(new Dimension(15, 0)));
		this.add(new JLabel(filter.name));
		this.add(Box.createHorizontalGlue());
		this.add(removeButton);
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
