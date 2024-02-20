package mvc.main.process;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import components.Panel;
import mvc.Controller;
import mvc.Model;
import mvc.View;
import mvc.main.MainModel;
import mvc.main.process.filters.FilterModel;

public class ProcessModel
{
	
	
	public final Panel rootPanel = new Panel(new BorderLayout()).initBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	public final FilterModel filterModel = new FilterModel();

	public final ProcessView       view;
	public final ProcessController control;
	
	public ProcessModel() 
	{
		this.view    = new ProcessView(this);
		this.control = new ProcessController(this);
	}

}
