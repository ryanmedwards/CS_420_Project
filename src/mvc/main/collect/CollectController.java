package mvc.main.collect;

import mvc.Controller;



public class CollectController
{
	public CollectController(final CollectModel model)
	{
		model.boundaryMVC.menuPanel.addChangeListener(e -> setBoundaryField(model));
		
		model.boundaryMVC.airportBox.addActionListener(e -> setBoundaryField(model));
		
		model.boundaryMVC.unitsBox.addActionListener(e -> setBoundaryField(model));
		
		model.boundaryMVC.distanceSpinner.addChangeListener(e -> setBoundaryField(model));
		
		model.boundaryMVC.northField.addChangeListener(e -> setBoundaryField(model));
		model.boundaryMVC.southField.addChangeListener(e -> setBoundaryField(model));
		model.boundaryMVC.westField.addChangeListener(e -> setBoundaryField(model));
		model.boundaryMVC.eastField.addChangeListener(e -> setBoundaryField(model));
		
		setBoundaryField(model);
	}
	
	private void setBoundaryField(final CollectModel model)
	{
		model.boundaryTextField.setText(model.boundaryMVC.getBoundaryString());	
	}
}
