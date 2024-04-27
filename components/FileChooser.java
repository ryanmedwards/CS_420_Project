package components;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JInitializer<JFileChooser> 
{
	public FileChooser initDirectory(final String directory)
	{
		try
		{
			get().setCurrentDirectory(new File(directory));
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public FileChooser initFileFilter(final String description, final String...extensions)
	{
		try
		{
			get().setFileFilter(new FileNameExtensionFilter(description, extensions));
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public FileChooser initFileSelectionMode(final int mode)
	{
		try
		{
			get().setFileSelectionMode(mode);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public FileChooser initAcceptAllFileFilterUsed(final boolean acceptAll)
	{
		get().setAcceptAllFileFilterUsed(acceptAll);
		return this;
	}
	

	@Override
	public JFileChooser create() 
	{
		return new JFileChooser();
	}
}
