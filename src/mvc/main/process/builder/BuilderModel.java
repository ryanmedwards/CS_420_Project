package mvc.main.process.builder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

import components.DragAndDrop;
import components.Label;
import components.Panel;
import mvc.filters.Filter;
import mvc.filters.boundary.BoundaryFilter;
import mvc.filters.datetime.DateTimeFilter;
import mvc.filters.duplicates.DuplicatesFilter;
import mvc.filters.expired.ExpiredFilter;
import mvc.filters.nulls.NullsFilter;


public class BuilderModel 
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

	
	protected final Panel excludedFiltersPanel = new Panel(new GridBagLayout())
															.initBackground(() -> UIManager.getColor("TextArea.background"))
															.initBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
	
	public final DragAndDrop<Filter> includedFiltersPanel = new DragAndDrop<>();
	

	
	
	public final BuilderView       view    = new BuilderView(this);
	public final BuilderController control = new BuilderController(this);
	
	
	
	// Filters must be defined after View and Controller
	
	protected final BoundaryFilter   boundaryFilter   = new BoundaryFilter(this);
	protected final DuplicatesFilter duplicatesFilter = new DuplicatesFilter(this);
	protected final NullsFilter      nullsFilter      = new NullsFilter(this);
	protected final ExpiredFilter    expiredFilter    = new ExpiredFilter(this);
	protected final DateTimeFilter   dateTimeFilter   = new DateTimeFilter(this);

}
