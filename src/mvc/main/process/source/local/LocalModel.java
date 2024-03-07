package mvc.main.process.source.local;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import components.Label;
import components.custom.RootPanel;
import components.custom.ToggleBar;
import components.text.TextArea;
import components.text.TextField;
import sql.LocalSQL;

public class LocalModel 
{

	public final JPanel rootPanel = new RootPanel().get();
	
	protected final List<LocalTable> localTables = LocalTable.getLocalTables();
	
	
	protected final JPanel tablesPanel = new RootPanel().get();
	
	protected final JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel tablesLabel = new Label("Tables").initFontSize(18).get();
	
	protected final JPanel togglePanel = new JPanel(new BorderLayout());
	protected final ToggleBar<LocalTable> tablesBar = new ToggleBar<>(10, 0, 1, localTables);
	
	
	
	protected final JPanel decriptionPanel = new RootPanel().get();
	
	protected final JPanel descLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	protected final JLabel descLabel = new Label("Description").initFontSize(18).get();
	
	protected final JPanel descPanel = new JPanel(new GridBagLayout());
	
	protected final JPanel descNPanel = new JPanel(new BorderLayout());
	
	protected final JLabel sourceLabel = new JLabel("Source:");
	protected final JLabel tableLabel = new JLabel("Table:");
	protected final JLabel countLabel = new JLabel("Count:");
	
	protected final JTextField sourceField = new TextField().initColumns(15).initEditable(false).get();
	protected final JTextField tableField = new TextField().initColumns(15).initEditable(false).get();
	protected final JTextField countField = new TextField().initColumns(15).initEditable(false).get();
	
	
	
	
	protected final JPanel historyPanel = new RootPanel().get();
	
	protected final JPanel historyLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	protected final JLabel historyLabel = new Label("History").initFontSize(18).get();
	
	
	protected final JTextArea historyTextArea = new TextArea().initEditable(false).get();
	protected final JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
	
	
	
	
	
	
	
	
	
	
	
	
	
	public final LocalView view = new LocalView(this);
	public final LocalController control = new LocalController(this);
}
