package mvc.filters.datetime;

import java.awt.GridBagLayout;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import components.Spinner;
import components.custom.RootPanel;
import components.custom.datetime.DateSelector;
import components.custom.datetime.TimeSelector;
import components.text.TextField;

public class DateTimeModel 
{
	
	public final JPanel rootPanel = new RootPanel().get();
	
	
	protected final JPanel controlPanel = new JPanel(new GridBagLayout());

	protected final JLabel dateLabel     = new JLabel("Date:");
	protected final JLabel timeLabel     = new JLabel("Time:");
	protected final JLabel durationLabel = new JLabel("Duration:");
	
	
	protected final JTextField dateField = new TextField()
											   .initColumns(10)
											   .initEditable(false)
											   .initHorizontalAlignment(JTextField.RIGHT)
											   .get();
	
	protected final JTextField timeField = new TextField()
											   .initColumns(10)
											   .initEditable(false)
											   .initHorizontalAlignment(JTextField.RIGHT)
											   .get();
	
	protected final JTextField durationField = new TextField()
												   .initColumns(10)
											       .initEditable(false)
												   .initHorizontalAlignment(JTextField.RIGHT)
												   .get();
											
	
	protected final DateSelector dateSelector    = new DateSelector(2010, LocalDateTime.now().getYear());
	protected final TimeSelector timeSelector    = new TimeSelector();
	protected final JSpinner     durationSpinner = new Spinner().initModel(1, 1, 24, 1).get();
	
	
	public final DateTimeView       view    = new DateTimeView(this);
	public final DateTimeController control = new DateTimeController(this);
}
