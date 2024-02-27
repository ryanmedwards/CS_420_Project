package mvc.main.process.builder;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.buttons.Button;
import components.custom.OrderPanel;
import mvc.filters.Filter;

public class FilterPanel extends OrderPanel<Filter>.DragPanel
{

	
	private final JButton removeButton = new Button("\u2716").initToolTip("Remove").get();
	private final JButton editButton   = new Button("\u2699").initToolTip("Edit"  ).get();

	
	final Filter filter;
	
	
	public FilterPanel(OrderPanel<Filter> orderPanel, Filter filter, boolean addToPanels) 
	{
		orderPanel.super(filter, addToPanels);

		this.filter = filter;
		
		removeButton.addActionListener(e -> 
		{
			orderPanel.remove(this);
			filter.enable();
		});
		
		
		editButton.addActionListener(e -> filter.edit());
		
		super.draw();
	}

	@Override
	public void addContent(final JPanel panel) 
	{
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panel.add(Box.createRigidArea(new Dimension(5, 5)));
		panel.add(editButton);
		panel.add(Box.createRigidArea(new Dimension(15, 5)));
		panel.add(new JLabel(filter.name));
		panel.add(Box.createHorizontalGlue());
		panel.add(removeButton);
		panel.add(Box.createRigidArea(new Dimension(15, 5)));
	}

	
}
