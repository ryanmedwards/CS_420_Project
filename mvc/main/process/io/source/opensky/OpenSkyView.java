package mvc.main.process.io.source.opensky;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import components.Table;

public class OpenSkyView 
{
	private final OpenSkyModel model;
	
	protected OpenSkyView(final OpenSkyModel model)
	{
		this.model = model;
		
		this.draw();
	}
	
	private void draw()
	{

		
		model.descriptionNorthPanel.add(model.descriptionLabel);
		
		model.descriptionPanel.add(model.descriptionNorthPanel, BorderLayout.NORTH);
		model.descriptionPanel.add(model.scrollPane, BorderLayout.CENTER);
		
		
		
		model.tablesPanel.add(model.labelPanel, BorderLayout.NORTH);
		model.labelPanel.add(model.tablesLabel);
		
		model.togglePanel.add(model.tables, BorderLayout.NORTH);
		model.tablesPanel.add(model.toggleScrollPane, BorderLayout.CENTER);
		
		model.toggleScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		model.rootPanel.add(model.tablesPanel, BorderLayout.WEST);
		model.rootPanel.add(model.descriptionPanel, BorderLayout.CENTER);
		
	}
}
