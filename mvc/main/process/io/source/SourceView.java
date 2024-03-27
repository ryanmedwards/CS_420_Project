package mvc.main.process.io.source;

import java.awt.BorderLayout;

public class SourceView 
{
	private SourceModel model;
	
	protected SourceView(final SourceModel model) 
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		model.tabPane.addTab("OpenSky-Network", model.openSkyModel.rootPanel);
		model.tabPane.addTab("Local Database" , model.localModel.rootPanel);
		model.tabPane.addTab("Import File", model.fileModel.rootPanel);
		
		model.rootPanel.add(model.tabPane, BorderLayout.CENTER);
		
		model.dialog.addContent(model.rootPanel);
	}
}
