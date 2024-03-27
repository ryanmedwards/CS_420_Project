package mvc.main.process.io.source.file;

import java.io.File;

import javax.swing.JFileChooser;

import application.Application;

public class FileController 
{
	private final FileModel model;
	
	protected FileController(final FileModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.selectButton.addActionListener(e ->
		{
			final int result = model.fileChooser.showOpenDialog(null);

	        if (result == JFileChooser.APPROVE_OPTION) 
	        {
	            model.filepath = model.fileChooser.getSelectedFile().getAbsolutePath();
	            model.fileField.setText(model.filepath);
	        } 
		});	
	}
	
	
	public boolean hasFile()
	{
		return model.filepath.equals("");
	}
	
	public String getFilePath()
	{
		return model.filepath;
	}
	
}
