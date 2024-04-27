package components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import mvc.filters.Filter;

public class IList<T> extends JInitializer<JList<T>>
{
	public int size()
	{
		return get().getModel().getSize();
	}
	
	public void setSelectedIndex(final int index)
	{
		get().setSelectedIndex(index);
	}
	
	public T getSelectedItem()
	{
		return get().getSelectedValue();
	}
	
	public void addItem(final T item)
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		lm.addElement(item);
		
		get().revalidate();
	}
	
	public void addItems(final List<T> items)
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		for(final T item: items)
		{
			lm.addElement(item);
		}
		
		get().revalidate();
	}
	
	public List<T> getAllItems()
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		final List<T> result = new ArrayList<>(lm.size());
		
		for(int i = 0; i < lm.size(); ++i)
		{
			result.add(lm.getElementAt(i));
		}
		
		return result;
	}
	
	public void removeAllItems()
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		lm.removeAllElements();
		
		get().revalidate();
	}
	
	public boolean removeItem(final T item)
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		final boolean removed = lm.removeElement(item);
		
		get().revalidate();
		
		return removed;
	}
	
	public boolean removeSelectedItem() 
	{
		return removeItem(get().getSelectedValue());
	}
	

	public IList<T> initModel(final ListModel<T> model)
	{
		get().setModel(model);
		return this;
	}
	
	/**
	 * Should use initModel before this
	 * 
	 * @param items
	 * @return
	 */
	public IList<T> initItems(final T... items)
	{
		final DefaultListModel<T> lm = (DefaultListModel<T>) get().getModel();
		
		for(final T item: items)
		{
			lm.addElement(item);
		}
		
		return this;
	}

	@Override
	public JScrollPane toScrollPane()
	{
		return new JScrollPane(get());
	}
	
	@Override
	public JList<T> create() 
	{
		return new JList<T>();
	}

}
