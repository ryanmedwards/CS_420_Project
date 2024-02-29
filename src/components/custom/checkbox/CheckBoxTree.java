package components.custom.checkbox;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import guiutil.Grid;

public class CheckBoxTree extends JPanel
{

	public final CheckBoxNode root;
	
	private final JPanel sub;
	
	private static final CheckBoxNode[] convert(final String[] nodes)
	{
		final CheckBoxNode[] children = new CheckBoxNode[nodes.length];
		
		for(int i = 0; i < children.length; ++i)
		{
			children[i] = new CheckBoxNode(nodes[i], 1);
		}
		
		return children;
	}
	
	// Use index 0 as root
	public CheckBoxTree(final String root, final String[] nodes)
	{	
		this(new CheckBoxNode(root, 0, convert(nodes)));
	}
	
	public CheckBoxTree(final CheckBoxNode root)
	{
		this.root = root;
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		sub = new JPanel(new GridBagLayout());
		
		Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(1, 0, 1, 0);
		placeNode(root, 10, grid);
		this.add(sub);
	}

	public int depth()
	{
		return depth(root) + 1;
	}
	
	private int depth(final CheckBoxNode node)
	{
		if(node.children.length == 0)
		{
			return 0;
		}
		else
		{
			final Integer[] depths = new Integer[node.children.length];
			
			for(int i = 0; i < depths.length; ++i)
			{
				depths[i] = depth(node.children[i]);
			}
			
			return Collections.max(Arrays.asList(depths)) + 1;
		}
	}
	
	private int y = 0;
	private void placeNode(final CheckBoxNode node, final int x, final Grid grid)
	{	
		sub.add(node, grid.set(0, y++));
			
		if(node.children.length == 0)
		{
			return;
		}
		else
		{
			for(final CheckBoxNode child: node.children)
			{
				grid.setInset(1, x, 1, 0);
				placeNode(child, x + 25, grid);
				child.setVisible(false);
			}
		}
	}
	
	public void setAvailable(final int id, final boolean available)
	{
		CheckBoxNode node = getNode(root, id);
		
		if(node != null)
		{
			System.out.println(node.getText());
			node.isAvailable = available;
		}
	}
	
	public List<String> getSelectedItems()
	{
		if(!root.isSelected()) return new ArrayList<>();
		
		final List<String> result = new ArrayList<>();
		
		searchSelectedItems(root, result);
		
		return result;
	}
	
	private void searchSelectedItems(final CheckBoxNode node, final List<String> items)
	{
		if(!node.isSelected())
		{
			return;
		}
		
		if(node.children.length == 0)
		{
			items.add(node.getText());
			return;
		}
		else
		{	
			for(final CheckBoxNode child: node.children)
			{
				searchSelectedItems(child, items);
			}
		}
		
	}
	
 	public CheckBoxNode getNode(final CheckBoxNode node, final int id)
	{
		if(node.id == id)
		{
			return node;
		}
		else
		{
			if(node.children.length == 0)
			{
				return null;
			}
			else
			{
				int index = 0;
				
				CheckBoxNode found = getNode(node.children[index], id);
				
				while(found == null && index < node.children.length - 1)
				{
					++index;
					found = getNode(node.children[index], id);
				}
				return found;
			}
		}
	}
}
