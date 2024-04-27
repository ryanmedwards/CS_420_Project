package mvc.main.simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import application.Application;
import components.ComboBox;
import components.Label;
import components.Panel;
import components.custom.RootPanel;
import components.text.TextField;
import guiutil.Grid;
import guiutil.OptionPane;
import matlab.GeoPlot;
import opensky.statevector.MessageClassification;

public class GeoplotModel 
{
	// au = authentic
	// pm = path modification
	// gi = ghost injection
	// vd = velocity drift
	
	private final Map<MessageClassification, Boolean> classMap = new HashMap<>();
	

	
	public final JPanel rootPanel = new RootPanel().get();

	protected final JPanel contentPanel = new JPanel(new GridBagLayout());
	protected final JScrollPane contentScrollPane = new JScrollPane(contentPanel);
	
	
	protected final JPanel titlePanel = new RootPanel().get();
	protected final JPanel titleLabelPanel = new Label("Title").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 10, 10);
	protected final JPanel titleContentPanel = new JPanel(new GridBagLayout());
	
	protected final JTextField titleField = new TextField().initColumns(30).get();
	protected final JTextField subtitleField = new TextField().initColumns(30).get();
	
	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// *** Appearance	          				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	protected final JPanel appearancePanel = new RootPanel().get();
	protected final JPanel appearanceLabelPanel = new Label("Appearance").initFontSize(18).toFlowPanel(FlowLayout.CENTER, 10, 10);	
	protected final JTabbedPane appearanceTabPane = new JTabbedPane(JTabbedPane.LEFT);	
	
	private static final String[] STYLES = {"-", "--", ":", "-.", "none"};
	private static final String[] MARKERS = {"none", "o", "+", "*", ".", "x", "-", "_", "|", "square", "diamond", "^", "v", ">", "<", "pentagram", "hexagram"};
	private static final String[] COLORS = {"red", "green", "blue", "cyan", "magenta", "yellow", "black", "white"};
	
	protected final JPanel auPanel = new Panel(new GridLayout(0, 2, 10, 5)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
	protected final JPanel pmPanel = new Panel(new GridLayout(0, 2, 10, 5)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
	protected final JPanel giPanel = new Panel(new GridLayout(0, 2, 10, 5)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
	protected final JPanel vdPanel = new Panel(new GridLayout(0, 2, 10, 5)).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();

	protected final JComboBox<String> auMarkerBox = new ComboBox<String>().initItems(MARKERS).get();
	protected final JComboBox<String> pmMarkerBox = new ComboBox<String>().initItems(MARKERS).get();
	protected final JComboBox<String> giMarkerBox = new ComboBox<String>().initItems(MARKERS).get();
	protected final JComboBox<String> vdMarkerBox = new ComboBox<String>().initItems(MARKERS).get();
	
	protected final JComboBox<String> auStyleBox = new ComboBox<String>().initItems(STYLES).get();
	protected final JComboBox<String> pmStyleBox = new ComboBox<String>().initItems(STYLES).get();
	protected final JComboBox<String> giStyleBox = new ComboBox<String>().initItems(STYLES).get();
	protected final JComboBox<String> vdStyleBox = new ComboBox<String>().initItems(STYLES).get();
	
	protected final JComboBox<String> auColorBox = new ComboBox<String>().initItems(COLORS).get();
	protected final JComboBox<String> pmColorBox = new ComboBox<String>().initItems(COLORS).get();
	protected final JComboBox<String> giColorBox = new ComboBox<String>().initItems(COLORS).get();
	protected final JComboBox<String> vdColorBox = new ComboBox<String>().initItems(COLORS).get();


	
	// ********************************************************
	// ********************************************************
	// ***									             	***
	// *** Plot Buttons	           				 			***
	// ***											        ***	
	// ********************************************************
	// ********************************************************
	
	protected final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	protected final JButton geoplotButton = new JButton("Plot");
	
	
	
	
	 
	public GeoplotModel()
	{
		this.draw();
		this.assign();
	}
	
	private void draw()
	{
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(5, 5, 5, 5);
		
		int x = 0;
		int y = 0;
		
		this.titleContentPanel.add(new JLabel("Title: "), grid.set(x, y)); 
		this.titleContentPanel.add(new JLabel("Subtitle: "), grid.set(x, ++y)); 
		
		x = 1; y = 0;
		grid.setAnchor(Grid.EAST);
		this.titleContentPanel.add(this.titleField, grid.set(x, y));
		this.titleContentPanel.add(this.subtitleField, grid.set(x, ++y));
		
		this.titlePanel.add(this.titleLabelPanel, BorderLayout.NORTH);
		this.titlePanel.add(this.titleContentPanel, BorderLayout.CENTER);

		this.auPanel.add(new JLabel("Line Style: ")); this.auPanel.add(this.auStyleBox );
		this.auPanel.add(new JLabel("Marker: "    )); this.auPanel.add(this.auMarkerBox);
		this.auPanel.add(new JLabel("Color: "     )); this.auPanel.add(this.auColorBox );

		this.pmPanel.add(new JLabel("Line Style: ")); this.pmPanel.add(this.pmStyleBox );
		this.pmPanel.add(new JLabel("Marker: "    )); this.pmPanel.add(this.pmMarkerBox);
		this.pmPanel.add(new JLabel("Color: "     )); this.pmPanel.add(this.pmColorBox );
		
		this.giPanel.add(new JLabel("Line Style: ")); this.giPanel.add(this.giStyleBox );
		this.giPanel.add(new JLabel("Marker: "    )); this.giPanel.add(this.giMarkerBox);
		this.giPanel.add(new JLabel("Color: "     )); this.giPanel.add(this.giColorBox );
		
		this.vdPanel.add(new JLabel("Line Style: ")); this.vdPanel.add(this.vdStyleBox );
		this.vdPanel.add(new JLabel("Marker: "    )); this.vdPanel.add(this.vdMarkerBox);
		this.vdPanel.add(new JLabel("Color: "     )); this.vdPanel.add(this.vdColorBox );
		
		this.appearanceTabPane.setPreferredSize(new Dimension(350, 135));
		
		this.drawAuthentic();

		buttonPanel.add(geoplotButton);

		this.contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
		

		grid.setAnchor(Grid.CENTER);
		grid.setInset(15, 15, 15, 15);
		x = 0; y = 0;
		
		this.contentPanel.add(this.titlePanel, grid.set(x,   y));
		this.contentPanel.add(this.appearancePanel, grid.set(x, ++y));
		this.contentPanel.add(this.buttonPanel, grid.set(x, ++y));
		
		
		rootPanel.add(contentScrollPane, BorderLayout.CENTER);
	}
	
	protected void drawAuthentic()
	{
		this.appearanceTabPane.removeAll();
		
		this.appearancePanel.removeAll();
		
		this.appearancePanel.add(appearanceLabelPanel, BorderLayout.NORTH);
		this.appearancePanel.add(this.auPanel, BorderLayout.CENTER);
		
		this.appearancePanel.revalidate();
		this.appearancePanel.repaint();
	}
	
	protected void addClasses(final Set<MessageClassification> set)
	{
		for(final MessageClassification mc: set)
		{
			this.classMap.put(mc, true);
		}
	
		this.redrawTabPane();
	}
	
	protected void removeAttack(final MessageClassification mc)
	{
		if(mc.equals(MessageClassification.AUTHENTIC)) return;
		
		this.classMap.put(mc, false);
	
		this.redrawTabPane();
	}
	
	private void redrawTabPane()
	{
		this.appearancePanel.removeAll();
		this.appearanceTabPane.removeAll();
		
		for(final MessageClassification mc: this.classMap.keySet())
		{
			if(this.classMap.get(mc))
			{
				switch(mc) 
				{
				case AUTHENTIC:			 this.appearanceTabPane.addTab("Authentic"        , this.auPanel); break;
				case GHOST_INJECTION:    this.appearanceTabPane.addTab("Ghost Injection"  , this.giPanel); break;
				case PATH_MODIFICATION:  this.appearanceTabPane.addTab("Path Modification", this.pmPanel); break;
				case VELOCITY_DRIFT:     this.appearanceTabPane.addTab("Velocity Drift"   , this.vdPanel); break;
				}
			}
		}
		
		this.appearancePanel.add(appearanceLabelPanel, BorderLayout.NORTH);
		this.appearancePanel.add(this.appearanceTabPane, BorderLayout.CENTER);
		
		this.appearancePanel.revalidate();
		this.appearancePanel.repaint();
	}
	
	
	
	
	private void assign()
	{
		classMap.put(MessageClassification.AUTHENTIC, true);
		classMap.put(MessageClassification.PATH_MODIFICATION, false);
		classMap.put(MessageClassification.GHOST_INJECTION, false);
		classMap.put(MessageClassification.VELOCITY_DRIFT, false);

		geoplotButton.addActionListener(e -> geoplot());
	}
	
	
	
	
	private void geoplot()
	{
		final String[] styles = 
		{
			auStyleBox.getSelectedItem().toString(),
			pmStyleBox.getSelectedItem().toString(),
			giStyleBox.getSelectedItem().toString(),
			vdStyleBox.getSelectedItem().toString()
		};
		
		final String[] markers = 
		{
			auMarkerBox.getSelectedItem().toString(),
			pmMarkerBox.getSelectedItem().toString(),
			giMarkerBox.getSelectedItem().toString(),
			vdMarkerBox.getSelectedItem().toString()
		};
		
		final String[] colors = 
		{
			auColorBox.getSelectedItem().toString(),
			pmColorBox.getSelectedItem().toString(),
			giColorBox.getSelectedItem().toString(),
			vdColorBox.getSelectedItem().toString()
		};
		
		final String title = this.titleField.getText();
		final String subtitle = this.subtitleField.getText();
		
		if( ! GeoPlot.geoplot(new GeoplotSettings(title, subtitle, styles, markers, colors), true) )
		{
			
		}
	}
	

	
}
