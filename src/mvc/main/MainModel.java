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
	
	protected final TabPanel menuPanel = new TabPanel(TabPanel.TOP);

	protected final CollectModel collectModel = new CollectModel();
	protected final ProcessModel processModel = new ProcessModel();
	
	protected final MainView       view    = new MainView(this);
	protected final MainController control = new MainController(this);
}