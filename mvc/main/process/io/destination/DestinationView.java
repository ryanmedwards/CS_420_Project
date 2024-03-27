package mvc.main.process.io.destination;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class DestinationView 
{
	private DestinationModel model;
	
	protected DestinationView(final DestinationModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{
		model.dialog.setPreferredSize(new Dimension(700, 500));
		
		model.tabPane.addTab("Local Database", model.localModel.rootPanel);
		model.tabPane.addTab("Export", model.exportModel.rootPanel);
		
		
		model.rootPanel.add(model.tabPane, BorderLayout.CENTER);
		
		
		model.dialog.addContent(model.rootPanel);
	}
}
