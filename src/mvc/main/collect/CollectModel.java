package mvc.main.collect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import components.Label;
import components.Panel;
import components.Spinner;
import components.TabbedPane;
import components.TextField;
import components.buttons.Button;
import components.custom.RootPanel;
import components.custom.datetime.DateSelector;
import components.custom.datetime.TimeBox;
import components.custom.datetime.TimeSelector;
import components.text.TextArea;
import guiutil.BoxItem;
import mvc.filters.boundary.BoundaryModel;


public class CollectModel
{

	
	public final JPanel rootPanel = new RootPanel().get();
	
	protected final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 25));
	
	protected final JTabbedPane tabPanel = new TabbedPane(JTabbedPane.TOP).get();
	
	protected final JButton explainButton = new JButton("Explain");
	protected final JButton countButton   = new JButton("Count"  );
	protected final JButton executeButton = new JButton("Execute");
	
	protected final JTextArea queryTextArea  = new TextArea().initEditable(false).get();
	protected final JTextArea outputTextArea = new TextArea().initEditable(false).get();
	protected final JTextArea logTextArea    = new TextArea().initEditable(false).get();
	
	
	protected final JPanel filtersPanel = new JPanel(new GridBagLayout());
	protected final JPanel westPanel    = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)).get();
	protected final JScrollPane filtersScrollPanel = new JScrollPane(filtersPanel, 
																	 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
																	 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	protected final JLabel dateTimeLabel = new  Label("Date and Time").initFontSize(18).get();
	protected final JLabel boundaryLabel = new  Label("Boundary"     ).initFontSize(18).get();
	protected final JLabel startLabel    = new JLabel("Start:"       );
	protected final JLabel stopLabel     = new JLabel("Stop:"        );
	protected final JLabel boundsLabel   = new JLabel("Boundary:"    );
	
	private static int FIELD_HEIGHT = 22;
	
	protected final DateSelector startDate = new DateSelector(2010, 2024);
	protected final TimeSelector startTime = new TimeSelector();

	
	protected final JComboBox<BoxItem> timeBox = new TimeBox().initPreferredSize(94, FIELD_HEIGHT).get();
	
	protected final JLabel timeLabel     = new JLabel("Time:");
	protected final JLabel durationLabel = new JLabel("Duration (hrs):");
	
	protected final JSpinner durationSpinner = new Spinner()
												   .initModel(1, 1, 24, 1)
												   .initPreferredSize(94, FIELD_HEIGHT)
												   .get();
	
	
	
	protected final BoundaryModel boundaryModel = new BoundaryModel();
	
	protected final JTextField boundaryTextField = new TextField()
													   .initColumns(22)
													   .initEditable(false)
													   .get();
	
	
	
	//protected final FieldPopup popup = new FieldPopup(boundaryTextField, boundaryModel.rootPanel);
	
	


	public final CollectView view = new CollectView(this);
	public final CollectController control = new CollectController(this);
}