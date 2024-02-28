package mvc.main;


import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import components.custom.RootPanel;
import mvc.main.collect.CollectModel;
import mvc.main.process.ProcessModel;



public class MainModel
{
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JTabbedPane menuPanel = new JTabbedPane(JTabbedPane.TOP);

	
	protected final CollectModel collectModel = new CollectModel();
	protected final ProcessModel processModel = new ProcessModel();
	
	
	protected final MainView       view    = new MainView(this);
	protected final MainController control = new MainController(this);
}