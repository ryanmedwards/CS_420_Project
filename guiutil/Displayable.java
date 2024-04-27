package guiutil;

import javax.swing.JTable;

public interface Displayable<T> 
{
	public Object[] toArray();
	
	public T toObject(final JTable table, final int row);
}
