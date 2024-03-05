package mvc.filters.boundary;

import java.awt.BorderLayout;

public class BoundaryView 
{
	private final BoundaryModel model;
	
	public BoundaryView(final BoundaryModel model)
	{
		this.model = model;
	}
	
	protected void draw()
	{
		model.menuPanel.addTab("Boundary Box", model.boxModel.rootPanel);
		model.menuPanel.addTab("Airport"     , model.airportModel.rootPanel);
		
		model.rootPanel.add(model.menuPanel, BorderLayout.CENTER);
	}
}
