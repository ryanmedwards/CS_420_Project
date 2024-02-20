package components;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelector extends JFileChooser 
{
	public FileSelector(final String path, final String description, final String... extensions)
	{
		super(path);
		this.setFileFilter(new FileNameExtensionFilter(description, extensions));
	}
}
