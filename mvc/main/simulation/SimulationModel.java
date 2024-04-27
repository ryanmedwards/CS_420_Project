package mvc.main.simulation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import components.Label;
import components.Spinner;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;

public class SimulationModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel centerPanel = new RootPanel().get();
	protected final JPanel westPanel = new RootPanel().get();
	
	
	
	protected final JPanel eastPanel = new RootPanel().get();
	
	
	
	
	protected final VisualModel visualModel = new VisualModel();

	
	protected final SimulationSourceModel sourceModel = new SimulationSourceModel(this);
	protected final SimulationDestinationModel destinationModel = new SimulationDestinationModel();

	public SimulationModel()
	{
		this.draw();
		this.assign();
	}
	
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setInset(10, 10, 10, 10);
		grid.setAnchor(Grid.WEST);
		
		int x = 0;
		int y = 0;
		

		
		// Draw West Panel
		
		westPanel.add(visualModel.rootPanel, BorderLayout.CENTER);
		
		westPanel.add(destinationModel.rootPanel, BorderLayout.NORTH);
		
		centerPanel.add(sourceModel.rootPanel, BorderLayout.CENTER);
	
		
		// Draw Root Panel
		
		rootPanel.add(westPanel, BorderLayout.WEST);
		rootPanel.add(centerPanel, BorderLayout.CENTER);
		rootPanel.add(eastPanel, BorderLayout.EAST);
		

	}
	
	
	private void assign()
	{

	}
}
