package components;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import opensky.statevector.StateVectorTable;

public class ScrollPanel extends JScrollPane
{

	public ScrollPanel(StateVectorTable table, int v, int h) {
		super(table, v, h);
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	public ScrollPanel(Table table, int v, int h) {
		super(table, v, h);
		setBorder(BorderFactory.createEmptyBorder());
	}
	

	public ScrollPanel(Panel panel, int v, int h) {
		super(panel, v, h);
		setBorder(BorderFactory.createEmptyBorder());
		getVerticalScrollBar().setUnitIncrement(12);
	}

	public ScrollPanel(TextField textField, int v, int h) {
		super(textField, v, h);
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	public ScrollPanel(TextArea textArea, int v, int h) {
		super(textArea, v, h);
		setBorder(BorderFactory.createEmptyBorder());
	}

}
