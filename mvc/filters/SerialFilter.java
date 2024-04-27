package mvc.filters;

import javax.swing.JPanel;

import opensky.statevector.StateVectorList;

public class SerialFilter extends Filter
{

	public SerialFilter(final int id) 
	{
		super(Filter.Type.SERIALS, id);
		
		super.drawRootPanel();
	}

	@Override
	public boolean filter(StateVectorList data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(JPanel panel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void saveState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revertState() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getLog(final int id, final int order, final Filter.Applied applied) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHistory() {
		// TODO Auto-generated method stub
		return null;
	}




}
