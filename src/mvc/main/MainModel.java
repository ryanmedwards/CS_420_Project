package mvc.main;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;

import application.Application;
import components.Panel;
import components.TabPanel;
import mvc.Model;
import mvc.main.collect.CollectModel;
import mvc.main.process.ProcessModel;



public class MainModel
{


	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	public final TabPanel menuPanel = new TabPanel(TabPanel.TOP);
	
	
	
	

	protected final MainView view;
	
	protected final MainController control;

	public final CollectModel collectModel = new CollectModel();
	
	public final ProcessModel processModel = new ProcessModel();
	
	public MainModel()
	{
		this.view    = new MainView(this);
		this.control = new MainController(this);
	}
}