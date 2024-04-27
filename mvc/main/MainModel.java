package mvc.main;


import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import components.custom.RootPanel;
import mvc.main.process.ProcessModel;
import mvc.main.simulation.SimulationModel;



public class MainModel
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JTabbedPane menuPanel = new JTabbedPane(JTabbedPane.TOP);


	protected final ProcessModel processModel = new ProcessModel();
	
	protected final SimulationModel simulateModel = new SimulationModel();
	
	
	public MainModel()
	{
		this.draw();
		this.assign();
	}
	
	protected void draw()
	{		
		menuPanel.addTab("Process", processModel.rootPanel);
		menuPanel.addTab("Simulate", simulateModel.rootPanel);

		//rootPanel.add(userModel.rootPanel, BorderLayout.NORTH);
		rootPanel.add(menuPanel, BorderLayout.CENTER);
	}
	
	private void assign()
	{
	
	}
}