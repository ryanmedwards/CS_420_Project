package mvc.main.process.filters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import components.Button;
import components.Label;
import components.Panel;
import components.ScrollPanel;
import components.Table;
import guiutil.Grid;
import mvc.Model;
import mvc.main.process.ProcessModel;

public class FilterModel
{
	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	protected final Panel optionPanel = new Panel(new BorderLayout());

	protected final Panel westPanel   = new Panel(new BorderLayout());
	protected final Panel centerPanel = new Panel(new BorderLayout());
	
	protected final Panel excludedHeaderPanel = new Panel(new FlowLayout());
	protected final Panel includedHeaderPanel = new Panel(new FlowLayout());
	
	protected final Panel includedPanel = new Panel(new BorderLayout())
			.initBorder(BorderFactory.createLineBorder(rootPanel.getBackground(), 10))
			.initBackground(() -> UIManager.getColor("TextArea.background"))
			.initPreferredWidth(350);
	
	protected final Panel excludedPanel = new Panel(new BorderLayout())
			.initBorder(BorderFactory.createLineBorder(rootPanel.getBackground(), 10))
			.initBackground(() -> UIManager.getColor("TextArea.background"))
			.initPreferredWidth(250);

	
	protected final Label optionsLabel = new Label("Filters").initSize(18);
	protected final Label processLabel = new Label("Process").initSize(18);
	
	
	protected final Panel includedFiltersPanel = new Panel(new GridBagLayout())
															.initBackground(UIManager.getColor("TextArea.background"))
															.initBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	
	protected final Panel excludedFiltersPanel = new Panel(new GridBagLayout())
															.initBackground(UIManager.getColor("TextArea.background"))
															.initBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	
	
	
	public final FilterView view = new FilterView(this);
	public final FilterController control = new FilterController(this);
	
	
	protected final Filter<RemoveDuplicates> removeDuplicatesFilter = new RemoveDuplicates(this);
	protected final Filter<RemoveNulls> removeNullsFilter           = new RemoveNulls(this);
	protected final Filter<RemoveExpiredMessages> removeExpiredMessagesFilter = new RemoveExpiredMessages(this);
	protected final Filter<BoundaryFilter> reduceBoundary = new BoundaryFilter(this);
	
//	protected final Filter reduceBoundaryFilter   = new Filter(this, "Reduce Boundary");
//	protected final Filter removeOldFilter        = new Filter(this, "Remove Old Messages");
	
	
	//public final FilterView view;
	
	
	
	public FilterModel()
	{

	}

	
}















