package mvc.filters;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import components.ComboBox;
import components.Label;
import components.Spinner;
import components.custom.DataField;
import components.custom.LatitudeField;
import components.custom.LongitudeField;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import components.text.TextField;
import data.CenterPoint;
import data.Unit;
import guiutil.Grid;
import sql.LocalSQL;

public class BoundaryPointModel 
{
	// Maybe Create a Unit Conversion Class

	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel eastPanel = new RootPanel().get();
	

	protected final JPanel tablePanel = new RootPanel().get();
	
	protected final JPanel tableNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	
	protected final JLabel tableLabel = new Label("Center Points").initFontSize(18).get();
	
	protected final CenterPointTable pointsTable = new CenterPointTable();
	protected final JScrollPane pointsScrollPanel = new JScrollPane(pointsTable);

	
	
	
	
	protected final JPanel infoPanel = new JPanel(new BorderLayout());
	
	protected final JPanel infoNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	protected final JPanel infoCenterPanel = new JPanel(new GridBagLayout());
	
	protected final JLabel infoLabel = new Label("Information").initFontSize(18).get();
	
	
	protected final JLabel nameLabel = new JLabel("Full Name:");
	protected final JLabel latLabel = new JLabel("Latitude:");
	protected final JLabel lonLabel = new JLabel("Longitude:");
	protected final JLabel timezoneLabel = new JLabel("Timezone:");
	protected final JLabel timeDifLabel = new JLabel("Time Difference:");
	
	protected int selectedPointID = 0;
	protected final JTextField nameTextField = new TextField().initEditable(false).initColumns(20).initHorizontalAlignment(JTextField.RIGHT).get();
	
	protected final DataField<Double> latTextField = new DataField<Double>().initColumns(20);
	protected final DataField<Double> lonTextField = new DataField<Double>().initColumns(20);
	
	protected final JTextField timezoneTextField = new TextField().initEditable(false).initColumns(20).initHorizontalAlignment(JTextField.RIGHT).get();
	protected final JTextField timeDifTextField = new TextField().initEditable(false).initColumns(20).initHorizontalAlignment(JTextField.RIGHT).get();
	
	
	
	protected final JPanel controlsPanel = new JPanel(new BorderLayout());
	
	protected final JPanel controlsNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
	protected final JPanel controlsCenterPanel = new JPanel(new GridBagLayout());
	
	protected final JLabel controlsLabel = new Label("Boundary").initFontSize(18).get();
	
	protected final JLabel distanceLabel = new JLabel("Distance");
	protected final JLabel unitsLabel = new JLabel("Unit");
	protected final JLabel shapeLabel = new JLabel("Shape");
	
	protected final JSpinner distanceSpinner = new Spinner().initModel(10, 0, 100, 1).get();
	

	protected final JComboBox<Unit>  unitsBox = new JComboBox<>(Unit.values());	
	protected final JComboBox<BoundaryPointFilter.Shape> shapeBox = new JComboBox<>(BoundaryPointFilter.Shape.values());	
	
	
	
	protected BoundaryPointModel()
	{
		this.draw();
		this.assign();
	}
	
	protected void draw()
	{	
		final Grid grid = new Grid();
		grid.setAnchor(Grid.WEST);
		grid.setInset(10, 10, 10, 10);
		
		int x = 0;
		int y = 0;
		
		infoNorthPanel.add(infoLabel);
		
		grid.setInset(5, 5, 5, 5);
		x = 0; y = 0;
		infoCenterPanel.add(nameLabel    , grid.set(x,   y));
		infoCenterPanel.add(latLabel     , grid.set(x, ++y));
		infoCenterPanel.add(lonLabel     , grid.set(x, ++y));
		infoCenterPanel.add(timezoneLabel, grid.set(x, ++y));
		infoCenterPanel.add(timeDifLabel , grid.set(x, ++y));
		
		x = 1; y = 0;
		infoCenterPanel.add(nameTextField    , grid.set(x,   y));
		infoCenterPanel.add(latTextField     , grid.set(x, ++y));
		infoCenterPanel.add(lonTextField     , grid.set(x, ++y));
		infoCenterPanel.add(timezoneTextField, grid.set(x, ++y));
		infoCenterPanel.add(timeDifTextField , grid.set(x, ++y));
		
		infoPanel.add(infoNorthPanel, BorderLayout.NORTH);
		infoPanel.add(infoCenterPanel, BorderLayout.CENTER);
		
		
		
		
		controlsNorthPanel.add(controlsLabel);
		
		x = 0; y = 0;
		controlsCenterPanel.add(distanceLabel, grid.set(x,   y));
		controlsCenterPanel.add(unitsLabel, grid.set(x, ++y));
		controlsCenterPanel.add(shapeLabel, grid.set(x, ++y));
		
		x = 1; y = 0;
		controlsCenterPanel.add(distanceSpinner, grid.set(x, y));
		controlsCenterPanel.add(unitsBox, grid.set(x, ++y));
		controlsCenterPanel.add(shapeBox, grid.set(x, ++y));
		
		controlsPanel.add(controlsNorthPanel, BorderLayout.NORTH);
		controlsPanel.add(controlsCenterPanel, BorderLayout.CENTER);
		
		
		tableNorthPanel.add(tableLabel);
		tablePanel.add(tableNorthPanel, BorderLayout.NORTH);
		tablePanel.add(pointsScrollPanel, BorderLayout.CENTER);
		
		
		eastPanel.add(infoPanel, BorderLayout.NORTH);
		eastPanel.add(controlsPanel, BorderLayout.CENTER);
		
		
		rootPanel.add(tablePanel, BorderLayout.CENTER);
		rootPanel.add(eastPanel, BorderLayout.EAST);
		
		
	} 

	
	private void assign()
	{

		pointsTable.getSelectionModel().addListSelectionListener(e ->
		{
			this.updateFields();
		});
		
		
		pointsTable.addCenterPoint(LocalSQL.getCenterPoints());
		pointsTable.setRowSelectionInterval(0, 0);
	}

	private void updateFields()
	{
		final CenterPoint p = pointsTable.getSelectedItem();
		
		if(p != null)
		{
			selectedPointID = p.pointID;
			nameTextField.setText(p.name);
			latTextField.setValue(p.lat);
			lonTextField.setValue(p.lon);
			timezoneTextField.setText(p.timezone);
			timeDifTextField.setText("UTC" + (p.utcTimeDif/3600) + "");
			
			infoCenterPanel.revalidate();
			infoCenterPanel.repaint();
		}
	}


	
	protected void setSelectedRow(final int pointID)
	{
		for(int i = 0; i < pointsTable.pointsList.size(); ++i)
		{
			if(pointsTable.pointsList.get(i).pointID == pointID)
			{
				pointsTable.setRowSelectionInterval(i, i);
				return;
			}
		}
	}
	
	public double[] getBoundary()
	{	
		final double clat = latTextField.getValue();
		final double clon = lonTextField.getValue();
		
		final Unit unit = (Unit)this.unitsBox.getSelectedItem();
		
		final double distance = unit.toKilometers((int)distanceSpinner.getValue() * 1.0);
		
		return BoundaryPointFilter.toBounds(clat, clon, distance);
	}
	
}


class CenterPointTable extends JTable
{
	private final String[] columns = 
	{
		"Name", "Location", "Country", "Type"
	};
	
	protected final List<CenterPoint> pointsList = new ArrayList<>();
	
	public CenterPointTable()
	{
		this.getTableHeader().setReorderingAllowed(false);
		
		this.showHorizontalLines = true;
		this.showVerticalLines = true;

		this.setModel(new DefaultTableModel()
		{
			@Override
		    public int getColumnCount() 
			{
		         return columns.length;
		    }
			
			@Override
			public String getColumnName(int index)
			{
				return columns[index];
			}
			
		});

		this.setRowSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.getSelectionModel().addListSelectionListener(e ->
		{
			
		});
	}
	
	public void addCenterPoint(final List<CenterPoint> points)
	{
		pointsList.addAll(points);
		
		final DefaultTableModel tableModel = (DefaultTableModel) this.getModel();

		for(final CenterPoint p: points)
		{
			tableModel.addRow(new Object[]
			{
				p.shortName, (p.city + ", " + p.state), p.country, p.type
			});
		}
		
		
		
		tableModel.fireTableDataChanged();
		
		this.revalidate();
	}
	
	protected CenterPoint getSelectedItem()
	{
		final int index = this.getSelectedRow();
		
		if(index == -1)
		{
			return null;
		}
		
		return this.pointsList.get(index);
	}
	
	
}

















