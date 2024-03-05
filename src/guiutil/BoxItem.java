package guiutil;

public class BoxItem 
{
	public final String name;
	public final int id;
	
	public BoxItem(final String name, final int id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String toString()
	{
		return name;
	}
}
