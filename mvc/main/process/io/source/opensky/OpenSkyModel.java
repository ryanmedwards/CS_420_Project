package mvc.main.process.io.source.opensky;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import components.Label;
import components.Table;
import components.custom.RootPanel;
import components.custom.ToggleBar;

public class OpenSkyModel 
{
	
	
	public final JPanel rootPanel = new RootPanel().initBorder(BorderFactory.createEmptyBorder()).get();
	
	

	
	protected final JTable table = new Table().initColumns("Name", "Data Type").get();
	
	protected final JScrollPane scrollPane = new JScrollPane(table);
	
	protected final JPanel tablesPanel = new RootPanel().get();
	
	protected final JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel tablesLabel = new Label("Table").initFontSize(18).get();
	
	
	
	protected final JPanel descriptionPanel = new RootPanel().get();
	protected final JLabel descriptionLabel = new Label("Description").initFontSize(18).get();
	
	protected final JPanel descriptionNorthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	
	
	protected final JPanel togglePanel = new JPanel(new BorderLayout());
	protected final ToggleBar<String> tables = new ToggleBar<>(10, 0, 1, "state_vectors_data4");
	protected final JScrollPane toggleScrollPane = new JScrollPane(togglePanel);
	
	
	
	public final OpenSkyView view = new OpenSkyView(this);
	public final OpenSkyController control = new OpenSkyController(this);
}
