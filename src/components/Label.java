package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel
{

	public static final int FONT       = 0;
	public static final int FONT_NAME  = 1;
	public static final int FONT_STYLE = 2;
	public static final int FONT_SIZE  = 3;
	
	public static final int ALIGN   = 4;
	public static final int ALIGN_X = 5;
	public static final int ALIGN_Y = 6;
	
	
	
	public Label() {}
	
	public Label(final String text)
	{
		super(text);
	}
	
	public Label(final String text, final Font font)
	{
		super(text);
		setFont(font);
	}
	
	public Label(final String text, final int alignment)
	{
		super(text);
		this.setHorizontalAlignment(alignment);
	}
	
	public Label(final String text, final Object... args)
	{
		super(text);
		
		try
		{
			for(int i = 0; i < args.length; ++i)
			{
				if(args[i] instanceof Integer)
				{
					switch((int)args[i])
					{
					//								   Font Name,           Font Style,           Font Size
					case FONT:       setFont(        args[i + 1],          args[i + 2],         args[i + 3]); i += 3; break;
					case FONT_NAME:  setFont(        args[i + 1], getFont().getStyle(), getFont().getSize()); i += 1; break;
					case FONT_STYLE: setFont(getFont().getName(),          args[i + 1], getFont().getSize()); i += 1; break;
					case FONT_SIZE:  setFont(getFont().getName(), getFont().getStyle(),         args[i + 1]); i += 1; break;
					
					case ALIGN:   align(             args[i + 1],            args[i + 2]); i += 2; break;
					case ALIGN_X: align(             args[i + 1], getVerticalAlignment()); i += 1; break;
					case ALIGN_Y: align(getHorizontalAlignment(),            args[i + 1]); i += 1; break;
					
					
					default: throw new Exception("Misaligned Arguments");
					}
				}
				else
				{
					throw new Exception("Misaligned Arguments");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void setFont(final Object o1, final Object o2, final Object o3)
	{
		final String name   = (o1 instanceof String)  ? o1.toString() : getFont().getName();
		final Integer style = (o2 instanceof Integer) ?       (int)o2 : getFont().getStyle();
		final Integer size  = (o3 instanceof Integer) ?       (int)o3 : getFont().getSize();
		
		super.setFont(new Font(name, style, size));
	}
	
	
	public Label initSize(final int size)
	{
		super.setFont(new Font(getFont().getName(), getFont().getStyle(), size));
		return this;
	}
	
	private void align(final Object o1, final Object o2)
	{
		final Integer x = (o1 instanceof Integer) ? (int) o1 : getHorizontalAlignment();
		final Integer y = (o2 instanceof Integer) ? (int) o2 : getVerticalAlignment();
		
		this.setHorizontalAlignment(x);
		this.setVerticalAlignment(y);
	}
	
	public Label initHorizontalAlignment(final int alignment)
	{
		this.setHorizontalAlignment(alignment);
		return this;
	}
}

















