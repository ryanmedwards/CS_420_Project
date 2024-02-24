package mvc.main.collect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;

import components.Button;
import components.DateSelector;
import components.FieldPopup;
import components.Label;
import components.Panel;
import components.ScrollPanel;
import components.TabPanel;
import components.TextArea;
import components.TextField;
import components.TimeSelector;
import mvc.filters.boundary.BoundaryModel;


public class CollectModel
{
	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	protected final Panel buttonPanel   = new Panel(new FlowLayout(FlowLayout.RIGHT, 25, 25));
	
	protected final TabPanel tabPanel = new TabPanel(TabPanel.BOTTOM);
	
	protected final Button explainButton = new Button("Explain");
	protected final Button countButton   = new Button("Count");
	protected final Button executeButton = new Button("Execute");
	
	protected final TextArea queryTextArea  = new TextArea().initEditable(false);
	protected final TextArea outputTextArea = new TextArea().initEditable(false);
	protected final TextArea logTextArea    = new TextArea().initEditable(false);
	
	
	protected final Panel filtersPanel = new Panel(new GridBagLayout());
	protected final Panel westPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	protected final ScrollPanel filtersScrollPanel = new ScrollPanel(filtersPanel, ScrollPanel.VERTICAL_SCROLLBAR_ALWAYS, ScrollPanel.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	protected final Label dateTimeLabel = new Label("Date and Time").initSize(18);
	protected final Label boundaryLabel = new Label("Boundary"     ).initSize(18);
	protected final Label startLabel    = new Label("Start:"       );
	protected final Label stopLabel     = new Label("Stop:"        );
	protected final Label boundsLabel   = new Label("Boundary:"    );
	
	protected final DateSelector startDate = new DateSelector(2010, 2024);
	protected final DateSelector stopDate  = new DateSelector(2010, 2024);
	
	protected final TimeSelector startTime = new TimeSelector();
	protected final TimeSelector stopTime  = new TimeSelector();

	
	protected final BoundaryModel boundaryModel = new BoundaryModel();
	
	protected final TextField boundaryTextField = new TextField(22).initEditable(false);
	
	
	
	protected final FieldPopup popup = new FieldPopup(boundaryTextField, boundaryModel.rootPanel);
	
	


	public final CollectView view = new CollectView(this);
	public final CollectController control = new CollectController(this);
}