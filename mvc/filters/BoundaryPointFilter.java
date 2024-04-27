 package mvc.filters;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import application.Application;
import data.Unit;
import mvc.filters.Filter.DBTable;
import opensky.statevector.StateVectorList;
import opensky.statevector.StateVectorOP;

public class BoundaryPointFilter extends Filter
{
	private final BoundaryPointModel model = new BoundaryPointModel();
	
	
	private int pointID = 0;
	private int distance = 0;
	private Unit unit = Unit.MILES;
	private Shape shape = Shape.BOX;


	public BoundaryPointFilter(final int id) 
	{
		super(Filter.Type.BOUNDARY_POINT, id);
		
		super.drawRootPanel();
	}
	
	public JPanel getRawRootPanel()
	{
		return model.rootPanel;
	}


	@Override
	protected String getDescription() 
	{
		return "<html>"
		     + "<p><p>"
		     + "Reduce the boundary of the data set by using a center point and a distance."
		     + "<br>"
		     + "The boundary will extend the distance given in each direction of the center point."
		     + "<p><p>"
		     + "<html>";
	}

	@Override
	public void draw(JPanel panel)
	{
		panel.setLayout(new BorderLayout());
		
		panel.add(model.rootPanel, BorderLayout.CENTER);
	}

	@Override
	public void revertState() 
	{
		model.distanceSpinner.setValue(distance);
		model.unitsBox.setSelectedItem(unit);
		model.shapeBox.setSelectedItem(shape);
	}


	@Override
	public void saveState() 
	{
		pointID = model.selectedPointID;
		distance = (int)model.distanceSpinner.getValue();
		unit = (Unit)model.unitsBox.getSelectedItem();
		shape = (Shape)model.shapeBox.getSelectedItem();
	}

	@Override
	public boolean filter(StateVectorList data) 
	{

		final int shape = model.shapeBox.getSelectedIndex();
		
		if(shape == 0) // box
		{
			final double[] box = model.getBoundary();
			
			return StateVectorOP.filterBoundaryBox(data, box);
		}
		else // Circle
		{
			final double clat = model.latTextField.getValue();
			final double clon = model.lonTextField.getValue();
			
			final double distance = model.unitsBox.getSelectedIndex() == 0 ?
					((int)model.distanceSpinner.getValue()) * 1.609:
					((int)model.distanceSpinner.getValue()) * 1.0;
			
			
			return StateVectorOP.filterBoundaryRadius(data, clat, clon, distance);
		}
	}

	


	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) 
	{
		this.saveState();
		
		return String.format(
				"insert into %s values ( %d, %d, \'%s\', %d, %d, \'%s\', \'%s\');", 
				this.getDBTable(),
				id,
				order,
				applied.toString(),
				pointID,
				distance,
				model.unitsBox.getSelectedItem().toString(),
				model.shapeBox.getSelectedItem().toString());
	}
	
	@Override
	public String getQueryCondition()
	{
		this.saveState();
		
		switch(shape)
		{
		case BOX:
			final double[] box = model.getBoundary();
			
			return FilterQuery.getBoundaryBoxQuery(box[0], box[1], box[2], box[3]);
			
		case CIRCLE:
			return FilterQuery.getBoundaryCircleQuery(
					model.latTextField.getValue(), 
					model.lonTextField.getValue(), 
					distance);
		default:
			return FilterQuery.getBoundaryBoxQuery(0.0, 0.0, 0.0, 0.0);
		}
		

	}
	
	
	@Override 
	public String getHistory()
	{
		this.saveState();

		return FilterHistory.getBoundaryPointHistory(
				model.nameTextField.getText(), 
				distance, 
				(Unit)model.unitsBox.getSelectedItem(), 
				(Shape)model.shapeBox.getSelectedItem());

	}

	
	
	public static double[] toBounds(final double clat, final double clon, final double distance)
	{
		final double[] bounds = new double[4]; // NSEW
		
		final double degToRad = Math.PI / 180;
		final double radToDeg = 180 / Math.PI;
		
		final double lat1 = clat * degToRad;
		final double lon1 = clon * degToRad;
		
		final double earthPolarRadiusKM = 6371.0;
		
		
		final double angDist = distance / earthPolarRadiusKM;
		
		final double sinLat1 = Math.sin(lat1);
		final double cosLat1 = Math.cos(lat1);
		
		final double sinAngDist = Math.sin(angDist);
		final double cosAngDist = Math.cos(angDist);
		
		bounds[0] = (Math.asin( sinLat1 * cosAngDist + cosLat1 * sinAngDist *  1)) * radToDeg;
		bounds[1] = (Math.asin( sinLat1 * cosAngDist + cosLat1 * sinAngDist * -1)) * radToDeg;
		bounds[2] = (lon1 + Math.atan2( 1 * sinAngDist * cosLat1 , cosAngDist - sinLat1 * sinLat1)) * radToDeg;
		bounds[3] = (lon1 + Math.atan2(-1 * sinAngDist * cosLat1 , cosAngDist - sinLat1 * sinLat1)) * radToDeg;
		
		if(bounds[0] > 90.0) bounds[0] = 90.0;
		
		if(bounds[1] < -90.0) bounds[1] = -90.0;
		
		if(bounds[2] >= 180.0) bounds[2] = -180.0 + (bounds[2] - 180.0);
		
		if(bounds[3] < -180.0) bounds[3] = 180.0 - (-1 * bounds[3] - 180);

		return bounds;
	}
	
	public enum Shape
	{
		BOX("Box", 0), 
		CIRCLE("Circle", 1);
		
		final String name;
		final int id;
		
		private Shape(final String name, final int id)
		{
			this.name = name;
			this.id = id;
		}
		
		public static Shape getEnum(final String name)
		{
			for(Shape s: values())
			{
				if(s.name.equalsIgnoreCase(name))
				{
					return s;
				}
			}	
			throw new IllegalArgumentException();
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
















