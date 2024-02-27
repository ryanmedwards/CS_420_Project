package mvc.main.collect;



public class CollectController
{
	private final CollectModel model;
	
	protected CollectController(final CollectModel model)
	{
		this.model = model;
		
		model.view.draw();
		
		this.assign();
	}
	
	private void assign()
	{
		model.executeButton.addActionListener(e -> System.out.println(model.startDate.getWidth()));
		
		model.boundaryModel.menuPanel.addChangeListener(e -> setBoundaryField());
		
		model.boundaryModel.airportModel.airportBox.addActionListener(e -> setBoundaryField());
		
		model.boundaryModel.airportModel.unitsBox.addActionListener(e -> setBoundaryField());
		
		model.boundaryModel.airportModel.distanceSpinner.addChangeListener(e -> setBoundaryField());
		
		model.boundaryModel.boxModel.northField.addChangeListener(e -> setBoundaryField());
		model.boundaryModel.boxModel.southField.addChangeListener(e -> setBoundaryField());
		model.boundaryModel.boxModel. westField.addChangeListener(e -> setBoundaryField());
		model.boundaryModel.boxModel. eastField.addChangeListener(e -> setBoundaryField());
		
		setBoundaryField();
	}

	private void setBoundaryField()
	{
		model.boundaryTextField.setText(model.boundaryModel.control.getDescription());	
	}
	
	
}
