package mvc.filters.datetime;

public class DateTimeController 
{
	private final DateTimeModel model;
	
	protected DateTimeController(final DateTimeModel model)
	{
		this.model = model;
		
		this.assign();
	}
	
	private void assign()
	{
		model.dateSelector.addFunction(this::updateDateField);
		
		model.timeSelector.addFunction(this::updateTimeField);
		
		model.durationSpinner.addChangeListener(e -> updateDurationField());
		
		updateDateField();
		updateTimeField();
		updateDurationField();
	}
	
	private void updateDateField()
	{
		model.dateField.setText(model.dateSelector.getDate());
	}
	
	private void updateTimeField()
	{
		model.timeField.setText(model.timeSelector.getTime());
	}
	
	private void updateDurationField()
	{
		final int duration = (int) model.durationSpinner.getValue();
		
		if(duration == 1)
		{
			model.durationField.setText(duration + " hour");
		}
		else
		{
			model.durationField.setText(duration + " hours");
		}
	}
	
	
}
