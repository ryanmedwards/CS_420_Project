package mvc;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import components.Panel;



public abstract class Model
<
	M extends Model<M, V, C, P>, 
	V extends View<M>,
	C extends Controller<M>,
	P extends Model<P, ?, ?, ?>
> 
{
	private Model<?, ?, ?, ?>[] children;
	
	private final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	public final Panel getRootPanel()
	{
		return rootPanel;
	}
	
	public P getParent()
	{
		return parent;
	}
	
	private final P parent;
	private final V view;
	private final C control;

	public abstract M getModel();
	
	public V getView() { return view; }
	
	public C getController() { return control; }
	
	public Model(final P parent, final V view, final C control)
	{
		this.parent = parent;
		this.view = view;
		this.control = control;
	}
	
	public abstract Model<?, ?, ?, ?>[] create();
	
	public void createAll()
	{
		children = create();
		
		for(final Model<?, ?, ?, ?> model: children)
		{
			model.createAll();
		}
	}
	
	public void drawAll()
	{
		for(final Model<?, ?, ?, ?> model: children)
		{
			model.drawAll();
		}
		
		view.draw(getModel());
	}
	
	public void assignAll()
	{
		for(final Model<?, ?, ?, ?> model: children)
		{
			model.assignAll();
		}
		
		control.assign(getModel());
	}

}
