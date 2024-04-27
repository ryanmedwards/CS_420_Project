package mvc.main.simulation;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import components.ComboBox;
import components.Label;
import components.Spinner;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;
import matlab.Animate;

public class AnimationModel 
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel settingsPanel = new JPanel(new GridBagLayout());
	
	protected final JLabel settingsLabel = new Label("Settings").initFontSize(18).get();

	
	
	
	
	
	protected final JLabel titleLabel = new JLabel("Title:");
	protected final JLabel baseMapZoomLabel = new JLabel("Basemap Zoom:");
	protected final JLabel rasterWidthLabel = new JLabel("Raster Width:");
	protected final JLabel rasterHeightLabel = new JLabel("Raster Height:");
	protected final JLabel planeScaleLabel = new JLabel("Plane Scale:");
	
	protected final JLabel figureZoomLabel = new JLabel("Figure Zoom:");
	protected final JLabel dpiLabel = new JLabel("Resolution (dpi):");
	protected final JLabel latitudeOffsetLabel = new JLabel("Latitude Offset:");
	protected final JLabel longitudeOffsetLabel = new JLabel("Longitude Offset:");
	protected final JLabel timeIntervalLabel = new JLabel("Time Interval:");

	
	
	
	protected final JTextField titleField = new TextField().initColumns(15).get();

	private int x = 80; // Spinner Width
	private int y = 23;  // Spinner Height
	
	protected final JSpinner baseMapZoomSpinner = new Spinner().initModel(10, 0, 25, 1).initPreferredSize(x, y).get();
	protected final JSpinner rasterWidthSpinner = new Spinner().initModel(2048, 64, 2048, 8).initPreferredSize(x, y).get();
	protected final JSpinner rasterHeightSpinner = new Spinner().initModel(2048, 64, 2048, 8).initPreferredSize(x, y).get();
	protected final JSpinner planeScaleSpinner = new Spinner().initModel(0.1, 0.01, 5, 0.1).initNumberFormat("0.00").initPreferredSize(x, y).get();
	
	protected final JSpinner figureZoomSpinner = new Spinner().initModel(1, 0.1, 3, 0.05).initNumberFormat("0.00").initPreferredSize(x, y).get();
	
	protected final JComboBox<Integer> dpiComboBox = new ComboBox<Integer>().initItems(150, 300, 600).get();
	
	protected final JSpinner latitudeOffsetSpinner = new Spinner().initModel(0.0, -1.0, 1.0, 0.05).initNumberFormat("0.00").initPreferredSize(x, y).get();
	protected final JSpinner longitudeOffsetSpinner = new Spinner().initModel(0.0, -1.0, 1.0, 0.05).initNumberFormat("0.00").initPreferredSize(x, y).get();
	protected final JSpinner timeIntervalSpinner = new Spinner().initModel(20, 1, 100, 1).initPreferredSize(x, y).get();
	
	protected final JButton openFigureButton = new JButton("Open Figure");
	protected final JButton saveFigureButton = new JButton("Save Figure");
	
	protected final JButton animateButton = new JButton("Animate");
	

	protected AnimationModel()
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
		
	
		// Draw Settings Panel
		
		x = 0;  y = 0;
		grid.setAnchor(Grid.CENTER);
		
		settingsPanel.add(settingsLabel, grid.set(x, y, 2, 1));
		
		x = 0;  y = 1;
		grid.setAnchor(Grid.WEST);
		
		settingsPanel.add(titleLabel       , grid.set(x,   y));
		settingsPanel.add(baseMapZoomLabel        , grid.set(x, ++y));
		settingsPanel.add(rasterWidthLabel , grid.set(x, ++y));
		settingsPanel.add(rasterHeightLabel, grid.set(x, ++y));
		settingsPanel.add(planeScaleLabel       , grid.set(x, ++y));
		settingsPanel.add(figureZoomLabel       , grid.set(x, ++y));
		settingsPanel.add(dpiLabel       , grid.set(x, ++y));
		settingsPanel.add(latitudeOffsetLabel       , grid.set(x, ++y));
		settingsPanel.add(longitudeOffsetLabel       , grid.set(x, ++y));
		settingsPanel.add(timeIntervalLabel, grid.set(x, ++y));
		
		x = 1;  y = 1;
		grid.setAnchor(Grid.EAST);
		
		settingsPanel.add(titleField         , grid.set(x,   y));
		settingsPanel.add(baseMapZoomSpinner        , grid.set(x, ++y));
		settingsPanel.add(rasterWidthSpinner , grid.set(x, ++y));
		settingsPanel.add(rasterHeightSpinner, grid.set(x, ++y));
		settingsPanel.add(planeScaleSpinner       , grid.set(x, ++y));
		settingsPanel.add(figureZoomSpinner       , grid.set(x, ++y));
		settingsPanel.add(dpiComboBox       , grid.set(x, ++y));
		settingsPanel.add(latitudeOffsetSpinner       , grid.set(x, ++y));
		settingsPanel.add(longitudeOffsetSpinner       , grid.set(x, ++y));
		settingsPanel.add(timeIntervalSpinner, grid.set(x, ++y));
		
		settingsPanel.add(openFigureButton, grid.set(x, ++y));
		settingsPanel.add(saveFigureButton, grid.set(x, ++y));
		settingsPanel.add(animateButton, grid.set(x, ++y));
		
		final JScrollPane scrollPane = new JScrollPane(settingsPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		rootPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	
	
	public AnimationSettings getSettings()
	{
		return new AnimationSettings
		(
			titleField.getText(),
			(int)baseMapZoomSpinner.getValue(),
			(int)rasterWidthSpinner.getValue(),
			(int)rasterHeightSpinner.getValue(),
			(double)planeScaleSpinner.getValue(),
			(double)figureZoomSpinner.getValue(),
			(int)dpiComboBox.getSelectedItem(),
			(double)latitudeOffsetSpinner.getValue(),
			(double)longitudeOffsetSpinner.getValue(),
			(int)timeIntervalSpinner.getValue()
		);
	}
	
	private void assign()
	{

		animateButton.addActionListener(e ->
		{
			Animate.animate(getSettings());
		});
		
		openFigureButton.addActionListener(e ->
		{
			if( ! Animate.testSettings(getSettings()) )
			{
				//OptionPane.showError("Failed to open figure.");
			}
		});
		
		saveFigureButton.addActionListener(e ->
		{
			if( ! Animate.saveFigure(getSettings()) )
			{
				//OptionPane.showError("Failed to open figure.");
			}
		});

	}
	
	
	
	
	
	
	
	
	
}


















