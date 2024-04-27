package mvc.main.simulation;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import components.custom.RootPanel;

public class VisualModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final JTabbedPane tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
	
	protected final GeoplotModel geoplotModel = new GeoplotModel();
	protected final AnimationModel animationModel = new AnimationModel();
	
	
	protected VisualModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		tabPane.addTab("Geoplot", geoplotModel.rootPanel);
		tabPane.addTab("Animate", animationModel.rootPanel);
		
		rootPanel.add(tabPane, BorderLayout.CENTER);
	}
	
	private void assign()
	{
		
	}
}
